package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.filter.LoginFilter;

//import com.example.demo.filter.LoginFilter;

@Configuration
public class MyConfiguration {


    @Autowired
    private LoginFilter loginFilter;

	@Bean
    public FilterRegistrationBean<LoginFilter> registFilter() {


        FilterRegistrationBean<LoginFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(loginFilter); //增加过滤器，会在请求到达 controller 之前执行
        registration.addUrlPatterns("/*"); //对所有url 应用过滤器
        registration.setName("LoginFilter");  //设置过滤器名字
        registration.setOrder(1);   //设置过滤器次序，有多个过滤器的时候有用
        return registration;
    }

}