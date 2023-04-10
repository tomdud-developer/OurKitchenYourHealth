package com.ourkitchen.yourhealth.client;

import com.ourkitchen.yourhealth.config.OAuth2FeignInterceptor;
import com.ourkitchen.yourhealth.dto.OrderInfoDTO;
import com.ourkitchen.yourhealth.dto.ProcessPaymentRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        value = "order-microservice-client",
        url = "http://localhost:8080/orders-microservice/api/v1/orders",
        configuration = OAuth2FeignInterceptor.class
)
public interface OrderMicroserviceClient {

    @RequestMapping(
            method = RequestMethod.GET,
            headers = "Content-Type: application/json",
            value = "{orderId}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @ResponseStatus(HttpStatus.FOUND)
    ResponseEntity<OrderInfoDTO> getOrderInfo(@PathVariable String orderId);


}


