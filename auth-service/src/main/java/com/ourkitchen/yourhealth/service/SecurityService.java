package com.ourkitchen.yourhealth.service;

import com.ourkitchen.yourhealth.dto.RegisterRequest;
import com.ourkitchen.yourhealth.model.AppUser;
import com.ourkitchen.yourhealth.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SecurityService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void registerUser(RegisterRequest registerRequest) {

        if(appUserRepository.findByUsername(registerRequest.getUsername()).isPresent())
            throw new RuntimeException("User already exist");

        //registerRequest.setPassword(
         //       passwordEncoder.encode(registerRequest.getPassword())
       // );

        AppUser appUser = AppUser.builder()
                //.id(null)
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .email(registerRequest.getEmail())
                .build();

        appUserRepository.save(appUser);
    }
}
