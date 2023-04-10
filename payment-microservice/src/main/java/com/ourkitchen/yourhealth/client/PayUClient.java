package com.ourkitchen.yourhealth.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ourkitchen.yourhealth.dto.PayUPaymentRequestDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PayUClient {

    @Value("${payU.process.payment.uri}")
    private String PAYU_PAYMENT_URL;

    public String processPayment(PayUPaymentRequestDTO payUPaymentRequestDTO, String accessToken) {
        String redirectURL = null;
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .followRedirects(false)
                    .followSslRedirects(false)
                    .retryOnConnectionFailure(true)
                    .build();

            ObjectMapper objectMapper = new ObjectMapper();
            String processPaymentRequestJsonBody = objectMapper.writeValueAsString(payUPaymentRequestDTO);
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

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return redirectURL;
    }
}
