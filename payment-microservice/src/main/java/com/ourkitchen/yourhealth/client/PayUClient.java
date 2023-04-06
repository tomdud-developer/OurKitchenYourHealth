package com.ourkitchen.yourhealth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;



@FeignClient(value = "instantwebtools-api", url = "https://secure.snd.payu.com")
public interface PayUClient {

    @RequestMapping(
            method = RequestMethod.POST,
            headers = "Content-Type: application/x-www-form-urlencoded",
            params = { "id", "second" },
            value = "/_payment",
            produces = "application/json")
    @ResponseBody
    String processPayment(
            @RequestParam("key") String key,
            @RequestParam("txnid") String txnid,
            @RequestParam("amount") String amount,
            @RequestParam("firstname") String firstname,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("productinfo") String productinfo,
            @RequestParam("pg") String pg,
            @RequestParam("bankcode") String bankcode,
            @RequestParam("surl") String surl,
            @RequestParam("furl") String furl,
            @RequestParam("ccnum") String ccnum,
            @RequestParam("ccexpmon") String ccexpmon,
            @RequestParam("ccexpyr") String ccexpyr,
            @RequestParam("ccvv") String ccvv,
            @RequestParam("ccname") String ccname,
            @RequestParam("txn_s2s_flow") String txn_s2s_flow,
            @RequestParam("hash") String hash
    );


}
