# 项目知识点

## 零散

### redisson： redis分布式客户端

#### redisson分布式集合：RMap

参考：https://huingsn.github.io/2019/01/11/Redisson%E8%AF%A6%E8%A7%A3/

RMap 本身并没有在本地缓存数据，每次读写操作都会发送到 Redis 服务器去执行相应的命令，保证了多线程环境中保持一致性。



但是在特定的场景下，映射缓存（Map）上的高度频繁的读取操作，导致网络通信都被视为瓶颈，此时可以使用Redisson提供的带有本地缓存功能的映射。



### java集合

`List<Integer> list=new ArrayList<>(RateRange.intValue()s);`

该参数的传递，是为了减少即将向list存入大量元素所带来的扩容操作的损耗，提前指定其初识容量，而避免扩容，这一点从本质上需要理解ArrayList本质上其元素存储在数组`transient Object[] elementData;`



add方法中是这样写的。

```

    public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
    }
```

可以看到，方法内部除了添加元素，还有扩容方法。

### java访问类型

在写代码时看到一个protected修饰符，突然发现自己通常只使用私有和公有两种访问修饰类型，故此去查询了一下。

<img src="http://obimage.wenzhuo4657.cn/image-20240926120935494.png" alt="image-20240926120935494"  />

需要注意的时private字段在其子类中也不能直接访问，只能通过public方法、反射等其他迂回的方式进行间接的访问。



# 报错

## mapper返回值类型不受泛型约束

报错很多，但实际上仅仅java泛型的一个在应用中的错误。

java泛型是一个伪泛型，这个伪是指其在实际运行中对java对象并没有约束，仅仅在编译中生效。

由于反射是通过class对象操作，而class对象的生命周期是从加载阶段开始的，因此我们可以通过反射查看java泛型是否生效。

![image-20240924171005751](http://obimage.wenzhuo4657.cn/image-20240924171005751.png)

此处实际上提醒我，java泛型在编译阶段的检查范围，我的错误是这样的，

![image-20240924171105413](http://obimage.wenzhuo4657.cn/image-20240924171105413.png)

该方法的实际内容由xml定义，使用泛型约束，但是在运行中总是并没有生成对应的泛型对象。

修正过后的xml

![image-20240924171215203](http://obimage.wenzhuo4657.cn/image-20240924171215203.png)

这里需要重新明确resultMap的定义，不仅仅是属性的映射，而且还管理sql语句返回的数据将要转向哪一个java类。



## 绕过泛型检查

```
    public Map<String, ILogicFilter<?>> logicFilterMap = new ConcurrentHashMap<>();



    public <T extends RuleActionEntity.RaffleEntity>    Map<String, ILogicFilter<T>> openLogicFilter() {
        return (Map<String, ILogicFilter<T>>) (Map<?, ?>) logicFilterMap;
    }
```

注意该段代码有三个泛型类型，Map<String, ILogicFilter<?>>、<T extends RuleActionEntity.RaffleEntity>和 (Map<?, ?>)。对于 (Map<?, ?>)而言，很显然这是没有任何约束的泛型，等价于map<>,但问题是为什么要这样做？



java的泛型是一个伪泛型，在运行时被擦除，原始信息保留在字节码文件中，不产生约束，仅仅在编译时进行约束。对于该段代码而言，我们需要注意将两个泛型进行转换，由于没有运行我们不知道能否进行转换，java编译器禁止了该种行为，将其视为报错，但是我们可以通过将其原有泛型转换为没有约束的map<?,?>类型，绕过泛型检查。

需要注意的是，这样做存在风险，或者说仅仅是保证了看上去类型是这样，实际上在运行中该方法返回的集合内部元素的value不一定是所期望的ILogicFilter<T>>。

对此需要明白的是，java语言在编译阶段无法得知某些泛型类型的具体类型，因此无法直接进行转换，如果进行了间接转换，即表示接受风险。



*注意对于java泛型来说，仅仅在编译阶段起到检查做用，这意味着对对象内部的元素是不知道的，仅仅检查了存入和取出，一般情况下，控制了get和set即控制了对象内部，但此处很明显，仅仅控制了get。*



## Field strategyArmory in cn.wenzhuo4657.BigMarket.Application required a single bean, but 2 were found:

在注入bean时总是将我的接口也注入为bean，经检查发现是由于mapperscan没有指定包的原因。

@MapperScan作用：扫描指定包下所有的接口类，然后所有接口在编译之后都会生成相应的实现类

**且注意mybatits通过动态代理为接口生成一个动态代理类，并注入容器，无论该接口是否有对应xml实现都会生成。**



这也是我总是有接口类的bean的原因，

https://blog.csdn.net/belongtocode/article/details/134771662

Mapper接口与XML的关联：
Mybatis初始化时，会解析XML配置文件，将里面定义的SQL语句与Mapper接口的方法建立映射关系，并保存在配置对象中。
动态代理的创建：
当我们调用SqlSession.getMapper()方法时，Mybatis使用Java动态代理机制，为Mapper接口创建代理对象。
代理对象的创建，主要通过MapperProxyFactory类来完成。
调用代理对象的方法时的内部处理：
当调用Mapper接口中的方法时，实际上调用的是代理对象的invoke方法。
在invoke方法中，Mybatis使用之前的映射关系，找到与方法对应的SQL语句。
SQL语句的执行：
找到SQL语句后，Mybatis会调用底层的执行器（Executor）来执行SQL语句，并完成参数的绑定，查询，以及结果返回。



对此需要注意mybatis的动态代理机制，并没有将sql语句注入到java对象中，而是通过一个映射关系，在java对象执行方法进行调用sql语句的执行并接收参数，这里需要回忆其jdbc的相关知识，将sql语句视为字符串参数输入，然后传输给数据库连接池进行执行。