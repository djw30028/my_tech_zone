package com.hellokoding.springboot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;
	
	public void saveUser(User user) {
		repository.save(user);
	}
	
	public Iterable<User> findAll() {
		return repository.findAll();
	}
	
	public User findByFirstName(String firstName) {
		return repository.findByFirstName(firstName);
	}
}
