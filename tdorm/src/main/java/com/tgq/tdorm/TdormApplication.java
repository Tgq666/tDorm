package com.tgq.tdorm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@MapperScan(basePackages = "com.tgq.tdorm.mapper")
@SpringBootApplication
public class TdormApplication {
    public static void main(String[] args) {
        SpringApplication.run(TdormApplication.class, args);
    }
}
