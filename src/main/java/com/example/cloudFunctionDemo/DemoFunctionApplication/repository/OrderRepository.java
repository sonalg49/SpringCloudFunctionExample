package com.example.cloudFunctionDemo.DemoFunctionApplication.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.cloudFunctionDemo.DemoFunctionApplication.domain.Order;

public interface OrderRepository extends MongoRepository<Order, String> {

}
