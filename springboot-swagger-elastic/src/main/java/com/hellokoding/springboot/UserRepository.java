package com.hellokoding.springboot;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserRepository extends ElasticsearchRepository<User, String> {
	public User findByFirstName(String firstName);
}
