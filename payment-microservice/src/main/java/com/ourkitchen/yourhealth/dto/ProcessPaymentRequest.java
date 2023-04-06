package com.ourkitchen.yourhealth.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class ProcessPaymentRequest {
    private String clientId;
    private String amount;
    private String productinfo;
    private String pg;
    private String bankcode;
    private String ccnum;
    private String ccexpmon;
    private String ccexpyr;
    private String ccvv;
    private String ccname;
    private String txn_s2s_flow;
}
