package cn.wenzhuo4657.BigMarket.config;



import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
//
//@Component
//public class MyTomcatConfig {
//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        return new TomcatServletWebServerFactory() {
//            @Override
//            protected void prepareContext(
//                    Tomcat tomcat,
//                    Context context) {
//
//                // Tomcat 启动前的最后准备阶段
//                System.out.println("Tomcat 即将启动");
//                super.prepareContext(tomcat, context);
//            }
//        };
//    }
//
//}
