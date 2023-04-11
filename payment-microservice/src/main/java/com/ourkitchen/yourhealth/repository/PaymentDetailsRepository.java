package com.ourkitchen.yourhealth.repository;

import com.ourkitchen.yourhealth.model.PaymentDetails;
import jakarta.persistence.OrderBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {
    List<PaymentDetails> findByOrderId(String orderId);

}
