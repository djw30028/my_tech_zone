package com.hellokoding.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.hellokoding.springboot.model.Person;
import com.hellokoding.springboot.service.UserService;


@Configuration
//@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class WebApplication implements CommandLineRunner {// extends SpringBootServletInitializer {
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(WebApplication.class);
//    }

	@Autowired
    private UserService userService;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebApplication.class, args);
    }

	@Override
	public void run(String... arg0) throws Exception {
		addSomePerson();
		
	}
	
	private void addSomePerson() {
		Person person = new Person();
		person.setFirstName("f1");
		person.setLastName("l1");
		person.setId("1");
		
		userService.addPerson(person);
	
		person = new Person();
		person.setFirstName("f2");
		person.setLastName("l2");
		person.setId("2");
		
		userService.addPerson(person);
	}
}

