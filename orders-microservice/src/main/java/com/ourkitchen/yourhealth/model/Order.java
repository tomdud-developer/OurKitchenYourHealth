package com.ourkitchen.yourhealth.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
@Builder
public class Order {

    @Id
    private String id;
    private String userId;
    private LocalDateTime startDate;
    private Integer daysNumber;
    @DocumentReference(lazy=true)
    private List<OrderOneDay> orderOneDays;



}
