package com.ourkitchen.yourhealth.client;

import com.ourkitchen.yourhealth.dto.ProcessPaymentRequestDTO;
import feign.Feign;
import feign.Request;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@FeignClient(
        value = "payu-client",
        url = "https://secure.snd.payu.com",
        configuration = PayUAuthClient.Configuration.class
)
public interface PayUAuthClient {

    /*
        Problems with 302 code, OpenFeign is complicated with 302 response
    */
    @RequestMapping(
            method = RequestMethod.POST,
            headers = "Content-Type: application/json",
            value = "/api/v2_1/orders",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @ResponseStatus(HttpStatus.FOUND)
    ResponseEntity<String> processPayment(
            @RequestBody ProcessPaymentRequestDTO processPaymentRequest,
            @RequestHeader(name="Authorization") String accessToken
    );

    class Configuration {
        @Bean
        public Feign.Builder builder() {
            Request.Options defaultOpts = new Request.Options();
            return Feign.builder()
                    .options(new Request.Options(
                            defaultOpts.connectTimeoutMillis(),
                            defaultOpts.connectTimeoutUnit(),
                            defaultOpts.readTimeout(),
                            defaultOpts.connectTimeoutUnit(),
                            false // Disable follow redirects.
                    ));
        }
    }



    @RequestMapping(
            method = RequestMethod.POST,
            headers = "Content-Type: application/x-www-form-urlencoded",
            params = { "grant_type", "client_id", "client_secret", "email" },
            value = "/pl/standard/user/oauth/authorize",
            produces = "application/json")
    @ResponseBody
    String getAccessToken(@RequestParam String grant_type,
                          @RequestParam String client_id,
                          @RequestParam String client_secret,
                          @RequestParam String email
    );

}
