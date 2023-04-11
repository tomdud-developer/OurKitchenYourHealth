package com.ourkitchen.yourhealth.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ourkitchen.yourhealth.dto.PayUPaymentRequestDTO;
import com.ourkitchen.yourhealth.dto.PayUPaymentResponseDTO;
import com.ourkitchen.yourhealth.dto.PaymentRequestDTO;
import com.ourkitchen.yourhealth.model.PaymentStatus;
import com.ourkitchen.yourhealth.util.Secrets;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PayUClient {

    @Value("${payU.process.payment.uri}")
    private String PAYU_PAYMENT_URL;

    @Value("${payU.process.payment.details.uri}")
    private String PAYU_PAYMENT_DETAILS_URL;

    private final PayUAuthClient payUAuthClient;

    public PayUPaymentResponseDTO processPayment(PaymentRequestDTO paymentRequestDTO) {
        PayUPaymentRequestDTO payUPaymentRequestDTO = (PayUPaymentRequestDTO) paymentRequestDTO;

        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .followRedirects(false)
                    .followSslRedirects(false)
                    .retryOnConnectionFailure(true)
                    .build();

            ObjectMapper objectMapper = new ObjectMapper();
            String processPaymentRequestJsonBody = objectMapper.writeValueAsString(payUPaymentRequestDTO);
            RequestBody requestBody = RequestBody.create(processPaymentRequestJsonBody, okhttp3.MediaType.parse("application/json"));
            String accessToken = getAccessToken();

            Request request = new Request.Builder()
                    .url(PAYU_PAYMENT_URL)
                    .header("Authorization", accessToken)
                    .method("POST", requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            String stringResponse = response.body().string();
            PayUPaymentResponseDTO responseDTO = objectMapper.readValue(stringResponse, PayUPaymentResponseDTO.class);

            return responseDTO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PaymentStatus getPaymentStatus(String payUOrderId) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .followRedirects(false)
                .followSslRedirects(false)
                .retryOnConnectionFailure(true)
                .build();

        String accessToken = getAccessToken();

        Request request = new Request.Builder()
                .url(PAYU_PAYMENT_DETAILS_URL + payUOrderId)
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
        String statusCode = responseJson.findValue("status").asText();

        switch(statusCode) {
            case "NEW":
                return PaymentStatus.PAYMENT_STARTED;
            case "COMPLETED":
                return PaymentStatus.PAYMENT_SUCCESSFULLY_FINISHED;
            case "DATA_NOT_FOUND":
                return PaymentStatus.PAYMENT_ERROR;
            default:
                return PaymentStatus.PAYMENT_ERROR;
        }
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
