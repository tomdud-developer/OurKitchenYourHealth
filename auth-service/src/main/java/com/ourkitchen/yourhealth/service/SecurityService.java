package com.ourkitchen.yourhealth.service;

import com.ourkitchen.yourhealth.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SecurityService {
    private final AppUserRepository appUserRepository;




}
