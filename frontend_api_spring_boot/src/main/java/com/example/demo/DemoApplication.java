package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.ui.Model;


import org.mybatis.spring.annotation.MapperScan;




@SpringBootApplication
@MapperScan("com.example.demo.mapper")
public class DemoApplication {
    @Value("${spring.application.name}")
    String appName;

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);

	}

}
