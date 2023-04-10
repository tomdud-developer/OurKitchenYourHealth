package com.ourkitchen.yourhealth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ourkitchen.yourhealth.client.OrderMicroserviceClient;
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
import java.net.MalformedURLException;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PayUClient payUClient;
    private final OrderMicroserviceClient orderMicroserviceClient;

    private final String PAYU_PAYMENT_URL = "https://secure.snd.payu.com/api/v2_1/orders";


    public String processPayment(ProcessPaymentRequestDTO processPaymentRequest) throws HttpClientErrorException.Unauthorized {
         /*
            Gettig OAUTH2.0 ACCESS token
         */
        String accessToken = "Bearer " + getAccessToken();
        String clientId = processPaymentRequest.getClientId();
        String orderId = processPaymentRequest.getOrderId();


        /*
            Based on clientId retrieve information about client, firstname and email
            For now lets define them
        */
        OrderInfoDTO orderInfoDTO = orderMicroserviceClient.getOrderInfo(orderId).getBody();

        PayUPaymentRequestDTO payUPaymentRequestDTO = PayUPaymentRequestDTO.builder()
                .merchantPosId(Secrets.NONPRODUCTION_SANDBOX_PAYU_POS_ID.toString())
                .currencyCode("PLN")
                .build();


        String redirectURL = null;
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .followRedirects(false)
                    .followSslRedirects(false)
                    .retryOnConnectionFailure(true)
                    .build();

            ObjectMapper objectMapper = new ObjectMapper();
            String processPaymentRequestJsonBody = objectMapper.writeValueAsString(processPaymentRequest);
            RequestBody requestBody = RequestBody.create(processPaymentRequestJsonBody, okhttp3.MediaType.parse("application/json"));

            Request request = new Request.Builder()
                    .url(PAYU_PAYMENT_URL)
                    .header("Authorization", accessToken)
                    .method("POST", requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            String stringResponse = response.body().string();
            JsonNode responseJson = objectMapper.readTree(stringResponse);

            redirectURL = responseJson.get("redirectUri").asText();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return redirectURL;
    }


    private String getAccessToken() throws HttpClientErrorException.Unauthorized {

        String grant_type = "client_credentials";
        String access_token = null;
        ObjectMapper mapper = new ObjectMapper();

        String jsonString =  payUClient.getAccessToken(
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

        return access_token;
    }


}
