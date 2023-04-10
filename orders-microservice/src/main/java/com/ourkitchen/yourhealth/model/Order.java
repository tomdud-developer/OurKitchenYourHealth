package com.ourkitchen.yourhealth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String id;
    private String userId;
    private LocalDateTime startDate;
    private Long daysNumber;
    @DocumentReference(lazy=true)
    private List<OrderOneDay> orderOneDays;
    private BigDecimal totalPrice;

    private Boolean isConfirmed;

    private OrderStatus orderStatus;
}
