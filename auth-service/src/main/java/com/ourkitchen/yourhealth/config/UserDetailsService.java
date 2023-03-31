package com.ourkitchen.yourhealth.config;

import com.ourkitchen.yourhealth.model.AppUser;
import com.ourkitchen.yourhealth.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> optionalAppUser = appUserRepository.findByUsername(username);


        UserDetails userDetails =
                optionalAppUser.map(appUser -> UserDetailsImplementation.builder()
                    .username(appUser.getUsername())
                    .password(appUser.getPassword())
                    .build()
                    )
                    .orElseThrow(
                            () -> new UsernameNotFoundException(
                                    String.format("User with username {} not found", username)
                            )
                    );

        return userDetails;
    }
}
