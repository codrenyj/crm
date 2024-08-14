package com.example.demo ;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;



@SpringBootTest(classes = DemoApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class MybatisTest {

    static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

  //  @Autowired
   // StudentMapper studentMapper;

    @Test
    void contextLoads() {
       
        //String now = studentMapper.now();

       // logger.info("NOW={}",now);
        

        //System.out.println( "Test" );

       // System.out.println(now);
        
        //logger.info("NOW={}", now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
    }
}