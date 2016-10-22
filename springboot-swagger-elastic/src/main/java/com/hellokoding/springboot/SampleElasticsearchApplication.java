package com.hellokoding.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleElasticsearchApplication implements CommandLineRunner {

	@Autowired
	private CustomerRepository repository;

	@Autowired
	private UserService service;
	
	@Override
	public void run(String... args) throws Exception {
		/*
		this.repository.deleteAll();
		saveCustomers();
		fetchAllCustomers();
		fetchIndividualCustomers();
		
		saveUser();
		fetchAllUser();
		fetchIndividualUser();
		*/
	}

	private void saveUser() {
		service.saveUser(new User("Michael", "Wang"));
		service.saveUser(new User("Mike", "Zhao"));
	}
	private void fetchAllUser() {
		System.out.println("User found with findAll():");
		System.out.println("-------------------------------");
		for (User user : this.service.findAll()) {
			System.out.println(user);
		}
		System.out.println();
	}
	
	private void fetchIndividualUser() {
		System.out.println("User found with fetchIndividualUser('Michael'):");
		System.out.println("--------------------------------");
		System.out.println(this.service.findByFirstName("Michael"));
		return;
	}
	
	private void saveCustomers() {
		this.repository.save(new Customer("Alice", "Smith"));
		this.repository.save(new Customer("Bob", "Smith"));
	}

	private void fetchAllCustomers() {
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : this.repository.findAll()) {
			System.out.println(customer);
		}
		System.out.println();
	}

	private void fetchIndividualCustomers() {
		System.out.println("Customer found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(this.repository.findByFirstName("Alice"));

		System.out.println("Customers found with findByLastName('Smith'):");
		System.out.println("--------------------------------");
		for (Customer customer : this.repository.findByLastName("Smith")) {
			System.out.println(customer);
		}
	}

	public static void main(String[] args) throws Exception {
		//SpringApplication.run(SampleElasticsearchApplication.class, "--debug").close();
		//SpringApplication.run(SampleElasticsearchApplication.class, "--debug");
		//SpringApplication.run(WebApplication.class, args);
		SpringApplication.run(SampleElasticsearchApplication.class, args);
	}

}
