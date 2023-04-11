package com.ourkitchen.yourhealth.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ourkitchen.yourhealth.dto.PayUPaymentRequestDTO;
import com.ourkitchen.yourhealth.dto.PaymentRequestDTO;
import com.ourkitchen.yourhealth.model.PaymentStatus;
import io.micrometer.observation.annotation.Observed;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PayUClient implements PaymentInterface {

    @Value("${payU.process.payment.uri}")
    private String PAYU_PAYMENT_URL;

    @Value("${payU.process.payment.details.uri}")
    private String PAYU_PAYMENT_DETAILS_URL;

    @Override
    public String processPayment(PaymentRequestDTO paymentRequestDTO, String accessToken) {
        PayUPaymentRequestDTO payUPaymentRequestDTO = (PayUPaymentRequestDTO) paymentRequestDTO;

        try {
            String redirectURL = null;
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
            return redirectURL;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PaymentStatus paymentDetails(String orderId, String accessToken) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .followRedirects(false)
                .followSslRedirects(false)
                .retryOnConnectionFailure(true)
                .build();

        Request request = new Request.Builder()
                .url(PAYU_PAYMENT_DETAILS_URL + orderId)
                .header("Authorization", accessToken)
                .method("GET", null)
                .build();

        Response response = client.newCall(request).execute();
        String stringResponse = null;
        if (response.body() != null) {
            stringResponse = response.body().string();
        } else throw new IOException("stringResponse is null");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(stringResponse);
        String statusCode = responseJson.get("status").get("statusCode").asText();

        switch(statusCode) {
            case "SUCCESS":
                return PaymentStatus.PAYMENT_SUCCESSFULLY_FINISHED;
            default:
                return PaymentStatus.PAYMENT_ERROR;
        }
    }




}
