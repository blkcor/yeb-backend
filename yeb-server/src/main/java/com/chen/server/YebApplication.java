package com.chen.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author: blkcor
 * @DATE: 2022/5/23  12:59
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 */
@SpringBootApplication
@MapperScan("com.chen.server.mapper")
public class YebApplication {
    public static void main(String[] args) {
        SpringApplication.run(YebApplication.class, args);

    }
}

