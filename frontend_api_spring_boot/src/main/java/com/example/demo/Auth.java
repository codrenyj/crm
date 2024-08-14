package com.example.demo;

import org.springframework.stereotype.Component;

import com.example.demo.model.User;


//这个注解不能忘记
@Component
public class Auth 
{
    
    //使用 ThreadLocal 来实现线程共享数据
    private  static final ThreadLocal<User> storage = new ThreadLocal<>();


    public static void set(User user)
    {
        storage.set(user);
    }
    public static User get()
    {
        return storage.get();
    }

    public static void clear()
    {
        storage.remove();

    }
}
