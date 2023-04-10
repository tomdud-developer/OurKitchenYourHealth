package com.ourkitchen.yourhealth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ourkitchen.yourhealth.client.OrderMicroserviceClient;
import com.ourkitchen.yourhealth.client.PayUAuthClient;
import com.ourkitchen.yourhealth.client.PayUClient;
import com.ourkitchen.yourhealth.dto.OrderInfoDTO;
import com.ourkitchen.yourhealth.dto.PayUPaymentRequestDTO;
import com.ourkitchen.yourhealth.dto.ProcessPaymentRequestDTO;
import com.ourkitchen.yourhealth.util.Secrets;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.*;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PayUAuthClient payUAuthClient;

    private final PayUClient payUClient;
    private final OrderMicroserviceClient orderMicroserviceClient;




    public String processPayment(ProcessPaymentRequestDTO processPaymentRequest) throws HttpClientErrorException.Unauthorized {
        /*
            Based on openID retrieve information about client, firstname and email
        */
        String orderId = processPaymentRequest.getOrderId();

        OrderInfoDTO orderInfoDTO = orderMicroserviceClient.getOrderInfo(orderId).getBody();

        PayUPaymentRequestDTO payUPaymentRequestDTO = PayUPaymentRequestDTO.builder()
                .merchantPosId(Secrets.NONPRODUCTION_SANDBOX_PAYU_POS_ID.toString())
                .currencyCode("PLN")
                .build();


        return payUClient.processPayment(payUPaymentRequestDTO, getAccessToken());
    }


    private String getAccessToken() throws HttpClientErrorException.Unauthorized {

        String grant_type = "client_credentials";
        String access_token = null;
        ObjectMapper mapper = new ObjectMapper();

        String jsonString =  payUAuthClient.getAccessToken(
                grant_type,
                Secrets.NONPRODUCTION_SANDBOX_PAYU_CLIENT_ID.toString(),
                Secrets.NONPRODUCTION_SANDBOX_PAYU_CLIENT_SECRET.toString(),
                Secrets.NONPRODUCTION_SANDBOX_PAYU_CLIENT_EMAIL.toString()
        );

        try {
            Map<String, Object> map = mapper.readValue(jsonString, new TypeReference<Map<String,Object>>(){});
            access_token = (String) map.get("access_token");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Internal Error with mapping JSON object");
        }

        return "Bearer " + access_token;
    }


}
