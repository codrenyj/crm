package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.responses.Response;

@RestController
public class ErrorController {

    @RequestMapping(value = "/api/error/nopermission", method = RequestMethod.GET)
    public  Response  nopermission() 
    {
          Response response=new Response();

          response.code="NO_PERMISSION";
          response.code="用户未登录";

 
         return response;
    }
}