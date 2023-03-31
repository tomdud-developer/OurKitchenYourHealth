package com.ourkitchen.yourhealth.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "appuser")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

    @Id
    //@GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

}
