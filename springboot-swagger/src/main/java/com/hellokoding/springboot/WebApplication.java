package com.hellokoding.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



//@Configuration
@SpringBootApplication
//@EnableAutoConfiguration
//@ComponentScan
public class WebApplication { //implements CommandLineRunner {// extends SpringBootServletInitializer {
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(WebApplication.class);
//    }

 

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebApplication.class, args);
    }
 
    
	 
}

