package com.ourkitchen.yourhealth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ourkitchen.yourhealth.client.OrderMicroserviceClient;
import com.ourkitchen.yourhealth.client.PayUAuthClient;
import com.ourkitchen.yourhealth.client.PayUClient;
import com.ourkitchen.yourhealth.dto.OrderInfoDTO;
import com.ourkitchen.yourhealth.dto.PayUPaymentRequestDTO;
import com.ourkitchen.yourhealth.model.PaymentDetails;
import com.ourkitchen.yourhealth.model.PaymentServiceEnum;
import com.ourkitchen.yourhealth.model.PaymentStatus;
import com.ourkitchen.yourhealth.util.Secrets;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PayUAuthClient payUAuthClient;

    private final PayUClient payUClient;
    private final OrderMicroserviceClient orderMicroserviceClient;
    private final PaymentDetailsService paymentDetailsService;



    public String processPayment(String orderId) throws HttpClientErrorException.Unauthorized {
        /*
            Based on openID retrieve information about client, firstname and email
        */
        Map<String, Object> claims = retrieveAuthData();
        String userId = claims.get("sub").toString();
        String email = claims.get("email").toString();
        String firstName = claims.get("given_name").toString();
        String lastName = claims.get("family_name").toString();

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String clientIp = attr.getRequest().getRemoteAddr();
        OrderInfoDTO orderInfoDTO = orderMicroserviceClient.getOrderInfo(orderId).getBody();
        String totalAmount = String.valueOf(
                orderInfoDTO.getTotalPrice().multiply(BigDecimal.valueOf(100)).intValueExact()
        );

        //Storing paymentDetails
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .clientId(userId)
                .currencyCode("PLN")
                .email(email)
                .orderId(orderId)
                .paymentServiceEnum(PaymentServiceEnum.PAYU)
                .paymentStatus(PaymentStatus.PAYMENT_STARTED)
                .totalAmount(totalAmount)
                .build();

        paymentDetails = paymentDetailsService.savePaymentDetails(paymentDetails);

        PayUPaymentRequestDTO payUPaymentRequestDTO = PayUPaymentRequestDTO.builder()
                .merchantPosId(Secrets.NONPRODUCTION_SANDBOX_PAYU_POS_ID.toString())
                .buyer(
                        PayUPaymentRequestDTO.BuyerDTO.builder()
                                .email(email)
                                .firstName(firstName)
                                .lastName(lastName)
                                .build()
                )
                .currencyCode("PLN")
                .totalAmount(totalAmount)
                .description(
                        String.format("Provide healthy meals for yourself for %d days from %s",
                                orderInfoDTO.getDaysNumber(),
                                orderInfoDTO.getStartDate().toString())
                )
                .visibleDescription(
                        String.format("Provide healthy meals for yourself for %d days from %s",
                                orderInfoDTO.getDaysNumber(),
                                orderInfoDTO.getStartDate().toString())
                )
                .extOrderId(String.valueOf(paymentDetails.getId()))
                .customerIp(clientIp)
                .build();


        return payUClient.processPayment(payUPaymentRequestDTO, getAccessToken());
    }

    private Map<String, Object> retrieveAuthData() {
        //Retrieve UserId
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> claims = jwtAuthenticationToken.getToken().getClaims();

        return claims;
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
