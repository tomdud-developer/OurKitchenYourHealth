package com.ourkitchen.yourhealth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class PaymentMicroserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMicroserviceApplication.class, args);
    }



}