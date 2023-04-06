package com.ourkitchen.yourhealth.service;

import com.ourkitchen.yourhealth.client.PayUClient;
import com.ourkitchen.yourhealth.dto.ProcessPaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PayUClient payUClient;

    public String processPayment(ProcessPaymentRequest processPaymentRequest) {
        String clientId = processPaymentRequest.getClientId();

        /*
            Based on clientId retrieve information about client, firstname and email
             For now lets define them
        */

        String email = "user@gmail.com";
        String phone = "123456789";
        String firstName = "UserFirstName";
        String key = "";
        String txnid = "";
        String furl = "https://apiplayground-response.herokuapp.com/fail";
        String surl = "https://apiplayground-response.herokuapp.com/sucess";
        String hash = "ccc029894dcc03a164f281b7a64596a19785e8a61ae81d008ef482e1534a99a67eee346d3cbf9ffcf1ce63b0e2faee26f2e4a20e6aef471c25b424c33971bb41";
        String url = payUClient.processPayment(
                key,
                txnid,
                processPaymentRequest.getAmount(),
                firstName,
                email,
                phone,
                processPaymentRequest.getProductinfo(),
                processPaymentRequest.getPg(),
                processPaymentRequest.getBankcode(),
                surl,
                furl,
                processPaymentRequest.getCcnum(),
                processPaymentRequest.getCcexpmon(),
                processPaymentRequest.getCcexpyr(),
                processPaymentRequest.getCcvv(),
                processPaymentRequest.getCcname(),
                processPaymentRequest.getTxn_s2s_flow(),
                hash
        );
        return url;
    }
}
