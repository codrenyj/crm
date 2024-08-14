package com.example.demo.controllers;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.model.UserExample;
import com.example.demo.model.UserExample.Criteria;
import com.example.demo.mapper.UserMapper;
import com.example.demo.responses.ResponseLoginMe;
import com.example.demo.responses.Response;
import com.example.demo.responses.ResponseLogin;
import com.example.demo.responses.ResponseLogout;
import com.example.demo.Auth;

@RestController
public class LoginController  {

    @Autowired
    private UserMapper userMapper;



    @RequestMapping(value = "/api/login/login", method = RequestMethod.POST)
    public  Response<User> login(
         @RequestParam(value = "username", defaultValue = "") String username,
         @RequestParam(value = "password", defaultValue = "") String password
     ) 
    {
          Response<User> response= new Response<User>();


          System.out.println("username " + username);
          System.out.println("password " + password);

          UserExample userExample = new UserExample();
          Criteria criteria =  userExample.createCriteria();
          
          criteria.andUsernameEqualTo(username);
          criteria.andPasswordEqualTo(password);
 
          List<User> users  = userMapper.selectByExample(userExample);
          User user=null;
          if(users.size() > 0 )  user=users.get(0);

          if(user == null )
          {
               response.code="USER_NOT_EXISTS";
               response.message="用户不存在或者账号密码错误";
               response.data=null;

               return response;
          }



          String token = this.generateRandom(32);

          user.setToken(token);

          userMapper.updateByPrimaryKey(user);


          response.data = user;

         return response;
    }

    @RequestMapping(value = "/api/login/register", method = RequestMethod.POST)
    public  Response<User> register(
         @RequestParam(value = "username", defaultValue = "") String username,
         @RequestParam(value = "password", defaultValue = "") String password
     ) 
    {
          Response<User> response= new Response<User>();


          System.out.println("username " + username);
          System.out.println("password " + password);


          UserExample userExample = new UserExample();
          Criteria criteria =  userExample.createCriteria();
          
          criteria.andUsernameEqualTo(username);
 
          List<User> users  = userMapper.selectByExample(userExample);
          User user=null;
          if(users.size() > 0 )  user=users.get(0);

          if(user != null )
          {
               response.code="USER_ALREDY_EXISTS";
               response.message="用户已存在";
               response.data=null;

               return response;
          }


          user=new User();
          user.setUsername(username);
          user.setPassword(password);

          userMapper.insert(user);


          response.data = user;

         return response;
    }


    private String generateRandom(int length) {

         String aToZ = "ABCDEFGHIJKLMNOPQRSTUVWXZYabcdefghijklmnopqrstuvwxyz1234567890"; // 36 letter.

         Random rand = new Random();
         StringBuilder res = new StringBuilder();
         for (int i = 0; i < length; i++) {
              int randIndex = rand.nextInt(aToZ.length());
              res.append(aToZ.charAt(randIndex));
         }
         return res.toString();
    }
 
    
    @RequestMapping(value = "/api/login/logout", method = RequestMethod.POST)
    public  ResponseLogout  logout(
     ) 
    {
         User user = Auth.get();
        
    
         System.out.println("User is " + user);
          if(user != null)
          {
               user.setToken("");
               userMapper.updateByPrimaryKey(user);
               
          }

          ResponseLogout response = new ResponseLogout();



        return response;
    }



    @RequestMapping(value = "/api/login/me", method = RequestMethod.GET)
    public  Response<User> me(
     ) 
    {

          User user = Auth.get();
    
          Response<User> response = new Response<>();
          response.data=user;

          return response;
    }



         
}
