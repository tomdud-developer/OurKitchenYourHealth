package com.ourkitchen.yourhealth.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value = "order-microservice-client",
        url = "http://localhost:8080/order-microservice/api/v1/meals/meals/is-exists",
        configuration = PayUClient.Configuration.class
)
public interface OrderMicroserviceClient {
}
