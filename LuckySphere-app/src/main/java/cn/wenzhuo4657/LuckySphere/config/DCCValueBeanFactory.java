package cn.wenzhuo4657.LuckySphere.config;


import cn.wenzhuo4657.LuckySphere.types.annotations.DCCValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: wenzhuo4657
 * @date: 2024/11/19
 * @description:
 * BeanPostProcessor（bean创建后的处理器）：该接口是springboot提供的钩子函数，参入容器的启动，用于对bean进行统一约束。
 * 依赖于单例模式对spring容器内的bean的属性进行（运行时）动态配置
 */

@Slf4j
public class DCCValueBeanFactory implements BeanPostProcessor {
//    todo 这个路径待修改
    private static final String BASE_CONFIG_PATH = "/big-market-dcc";
    private static final String BASE_CONFIG_PATH_CONFIG = BASE_CONFIG_PATH + "/config";
    private final CuratorFramework client;
    private final Map<String, Object> dccObjGroup = new HashMap<>();

    public DCCValueBeanFactory(CuratorFramework client) throws Exception {
        this.client = client;

        if (null == client.checkExists().forPath(BASE_CONFIG_PATH_CONFIG)) {
            client.create().creatingParentsIfNeeded().forPath(BASE_CONFIG_PATH_CONFIG);
            log.info("DCC 节点监听 base node {} not absent create new done!", BASE_CONFIG_PATH_CONFIG);
        }
        CuratorCache curatorCache = CuratorCache.build(client, BASE_CONFIG_PATH_CONFIG);

        /**
         *  @author:wenzhuo4657 des:
        CuratorCache：自定义监听指定节点下的变化，
        dccObjGroup：本地缓存
        1，该监听器表示只会监听本地存在的key的节点，将其值变化进行同步到本地缓存group当中，也就是容器当中bean。
        因此，该配置的前提是spring容器的单例模式，如果是原型就不可以。
         */
        curatorCache.listenable().addListener((type, oldData, data) -> {
            log.info("DCC监听生效，{}节点发生变化，",data.getPath());
            switch (type) {
                case NODE_CHANGED:
                    String dccValuePath = data.getPath();
                    Object objBean = dccObjGroup.get(dccValuePath);
                    if (null == objBean) return;
                    try {
                        Class<?> objBeanClass=objBean.getClass();
                        if (AopUtils.isAopProxy(objBean)){
                            objBeanClass = AopUtils.getTargetClass(objBean);
                        }

                        Field field = objBeanClass.getDeclaredField(dccValuePath.substring(dccValuePath.lastIndexOf("/") + 1));
                        field.setAccessible(true);
                        field.set(objBean, new String(data.getData()));
                        field.setAccessible(false);
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                default:
                    break;
            }
        });
        curatorCache.start();
    }

    /**
     * @author:wenzhuo4657 des:
     * 1，使用钩子找到具有指定注解DCCValue的bean
     * 2，（1）缓存到group中，（2）创建对应的zookeeper节点  (3)绑定数据到注解标注的属性当中
     * ps: 对于filed属性来说，数据源有两点 1，注解属性defaultValue当中的默认值 ，2，zookeeper当中的节点值。
     * <p>
     * 其中： zookeeper >DCCValue.default ,
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 注意；增加 AOP 代理后，获得类的方式要通过 AopProxyUtils.getTargetClass(bean); 不能直接 bean.class 因为代理后类的结构发生变化，这样不能获得到自己的自定义注解了。
        Class<?> targetBeanClass = bean.getClass();
        Object targetBeanObject = bean;
        if (AopUtils.isAopProxy(bean)) {
            targetBeanClass = AopUtils.getTargetClass(bean);
            targetBeanObject = AopProxyUtils.getSingletonTarget(bean);
        }

        Field[] declaredFields = targetBeanClass.getDeclaredFields();
        for (Field field : declaredFields) {
            if (!field.isAnnotationPresent(DCCValue.class)) {
                continue;
            }
            DCCValue dccValue = field.getAnnotation(DCCValue.class);
            String value = dccValue.value();
            if (StringUtils.isBlank(value)) {
                throw new RuntimeException(field.getName() + " @DCCValue is not config value config case 「isSwitch/isSwitch:1」");
            }

            String[] splits = value.split(":");
            String key = splits[0];
            String defaultValue = splits.length == 2 ? splits[1] : null;

            try {
                String keyPath = BASE_CONFIG_PATH_CONFIG.concat("/").concat(key);
                if (null == client.checkExists().forPath(keyPath)) {
                    client.create().creatingParentsIfNeeded().forPath(keyPath,defaultValue.getBytes());
                    if (StringUtils.isNotBlank(defaultValue)) {
                        field.setAccessible(true);
                        field.set(bean, defaultValue);
                        field.setAccessible(false);
                    }
                    log.info("DCC 节点监听 创建节点 {}", keyPath);
                } else {
                    String configValue = new String(client.getData().forPath(keyPath));
                    if (StringUtils.isNotBlank(configValue)) {
                        field.setAccessible(true);
                        field.set(targetBeanObject, configValue);
                        field.setAccessible(false);
                        log.info("DCC 节点监听 设置配置 {} {} {}", keyPath, field.getName(), configValue);
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            dccObjGroup.put(BASE_CONFIG_PATH_CONFIG.concat("/").concat(key), targetBeanObject);
        }


        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
