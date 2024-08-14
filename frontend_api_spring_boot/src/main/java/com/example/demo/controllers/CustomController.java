package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import com.example.demo.model.Custom;
import com.example.demo.model.CustomExample;
import com.example.demo.model.User;
import com.example.demo.model.CustomExample.Criteria;
import com.example.demo.responses.Response;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.example.demo.mapper.CustomMapper;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class CustomController {

    @Autowired
    private CustomMapper customMapper;

    //@Autowired
    //private CustomExample customExample;


    @RequestMapping(value = "/api/custom/list", method = RequestMethod.GET)
    public  Response<PageInfo<Custom>>   list(
         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageCount,
         @RequestParam(value = "pageIndex", defaultValue = "1") Integer page,
         @RequestParam(value = "name", defaultValue = "") String query
  
         ) 
         {

          if (!query.equals("")) query="%"+query+"%";
          if (page < 1) page=1;
          if (pageCount < 1) pageCount =1; 

          CustomExample customExample = new CustomExample();

          PageHelper.startPage(page,pageCount);    


          User user=new User();


          Criteria criteria = customExample.createCriteria();
          if (!StringUtils.isEmptyOrWhitespace(query)) {
                  criteria.andNameLike("%" + query + "%");    
          }
          
          List<Custom> userList = customMapper.selectByExample(customExample);

          PageInfo<Custom> pageInfo = new PageInfo<Custom>(userList);

          Response<PageInfo<Custom>> response=new Response<PageInfo<Custom>>();


          response.data=pageInfo;

          return response;
    }

 
    
    @RequestMapping(value = "/api/custom/get", method = RequestMethod.GET)
    public  Response<Custom>  get(
         @RequestParam(value = "id", defaultValue = "",required = true) Integer id
         ) 
    {

         Custom custom = customMapper.selectByPrimaryKey(id);

         Response<Custom> response = new Response<Custom>();

         response.data = custom;

         return response;
    }


    @RequestMapping(value = "/api/custom/create", method = RequestMethod.POST)
    public  Response<HashMap<String,String>>  create(
         @RequestBody Custom custom
         ) 
    {

          //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
          //String date= df.format(new Date());

          custom.setCreatedAt(new Date());
          custom.setUpdatedAt(new Date());

         customMapper.insert(custom);

         Response<HashMap<String,String>> response = new Response<HashMap<String,String>>();

         response.data=new HashMap<String,String>();
         response.data.put("id",custom.getId().toString());

         return response;
    }


    @RequestMapping(value = "/api/custom/update", method = RequestMethod.POST)
    public  Response<HashMap<String,String>>  update(
         @RequestBody Custom custom
         ) 
    {
          custom.setUpdatedAt(new Date());
         customMapper.updateByPrimaryKey(custom);

         Response<HashMap<String,String>> response = new Response<HashMap<String,String>>();

         response.data=new HashMap<String,String>();
         response.data.put("id",custom.getId().toString());

         return response;
    }



    @RequestMapping(value = "/api/custom/delete", method = RequestMethod.POST)
    public  Response<HashMap<String,String>>  delete(
         @RequestParam(value = "id", defaultValue = "",required = true) Integer id
         ) 
    {
         customMapper.deleteByPrimaryKey(id);

         Response<HashMap<String,String>> response = new Response<HashMap<String,String>>();

         response.data=new HashMap<String,String>();

         return response;
    }
         
}