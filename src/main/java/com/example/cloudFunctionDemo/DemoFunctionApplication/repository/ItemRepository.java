package com.example.cloudFunctionDemo.DemoFunctionApplication.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.cloudFunctionDemo.DemoFunctionApplication.domain.Item;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
	
}
