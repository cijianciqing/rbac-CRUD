package jsjzx.wlyw.rbactest.config;

import jsjzx.wlyw.rbactest.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //返回登录页面
        registry.addViewController("/").setViewName("login");
        //为什么/login不好用
        registry.addViewController("/login00").setViewName("login");
        //登录成功后返回页面
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/welcome").setViewName("welcome");

        //resources展示页面
        registry.addViewController("/permission/resources/list").setViewName("permission/resources/list");

        //role快捷页面
        registry.addViewController("/permission/role/list").setViewName("permission/role/list");
        registry.addViewController("/permission/role/edit").setViewName("permission/role/edit");
        registry.addViewController("/permission/role/add").setViewName("permission/role/add");

        //user快捷页面
        registry.addViewController("/permission/user/list").setViewName("permission/user/list");
        registry.addViewController("/permission/user/edit").setViewName("permission/user/edit");
        registry.addViewController("/permission/user/assign").setViewName("permission/user/assign");
        registry.addViewController("/permission/user/add").setViewName("permission/user/add");

        //测试zTree
        registry.addViewController("/ztreeTest").setViewName("ztree01/ztreeIntroduce");

        //测试H-ui页面内的跳转
        registry.addViewController("/t01").setViewName("t01");
        registry.addViewController("/t02").setViewName("t02");


    }

    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }
/*
//暂时禁用，其他功能测试完成后再启用
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        springboot已经做好论文静态资源映射
        registry.addInterceptor(new MyLoginInterceptor()).addPathPatterns("/*")
                .excludePathPatterns("/index.html","/","user/lgin");
    }*/
}
