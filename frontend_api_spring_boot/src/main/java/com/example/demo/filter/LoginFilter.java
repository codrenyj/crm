package com.example.demo.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;
import com.example.demo.model.UserExample;
import com.example.demo.mapper.UserMapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.example.demo.Auth;

@Component
public class LoginFilter implements Filter {

    @Autowired
    HttpSession httpSession;

    @Autowired
    Auth auth;

    @Autowired
    UserMapper userMapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        // long start = System.currentTimeMillis();

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String url =  request.getRequestURI().toString();

        // 确定不需要登录的路径
        ArrayList<String> urls = new ArrayList<String>();
        urls.add("/api/login/login");
        //urls.add("/api/login/logout");
        urls.add("/api/login/register");
        //urls.add("/api/custom/list");
        //urls.add("/api/custom/get");
        //urls.add("/api/custom/create");
        //urls.add("/api/custom/update");
        //urls.add("/api/custom/delete");
        urls.add("/api/error/nopermission");

        //System.out.println("URL " + url);
        if (urls.contains(url)) 
        {
            //不需要登录的路径，安装原来的方式处理
            filterChain.doFilter(servletRequest, servletResponse);
        } 
        else 
        {
            //获取http请求头中的token 值，没有则跳转到错误接口
            String token = request.getHeader("Authorization");
            System.out.println("TOKEN " + token);
            if(token == null)
            {
                response.sendRedirect("/api/error/nopermission");
                return ;
            }



            //根据token 查询用户，没有则跳转到错误接口
            UserExample userExample=new UserExample();
            userExample.createCriteria().andTokenEqualTo(token);

            List<User> users = userMapper.selectByExample(userExample);
            User user = null;
            if (users.size() > 0)
                user = users.get(0);

            if(user == null)
            {
                response.sendRedirect("/api/error/nopermission");
                return ;
            }

            //有则保存到 Auth,方便其它地方使用
            //其它地方不能通过获取token 方式，查找数据库来获取用户, 因为token可能中途失效，所以通过token获取用户并不是百分百有效的,这会导致代码很难写
            //然而通过读取已经存入的对象，这才是百分百有效
            Auth.set(user);

            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
