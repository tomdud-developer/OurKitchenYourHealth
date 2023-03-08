package com.ourkitchen.yourhealth.repository;

import com.ourkitchen.yourhealth.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
}
