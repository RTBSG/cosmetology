package com.cosmetology;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@MapperScan("com.cosmetology.dao")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CosmetologyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CosmetologyApplication.class, args);
	}

}
