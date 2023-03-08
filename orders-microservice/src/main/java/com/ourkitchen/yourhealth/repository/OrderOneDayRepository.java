package com.ourkitchen.yourhealth.repository;

import com.ourkitchen.yourhealth.model.OrderOneDay;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderOneDayRepository extends MongoRepository<OrderOneDay, String> {
}
