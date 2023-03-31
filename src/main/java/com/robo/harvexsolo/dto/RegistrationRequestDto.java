package com.robo.harvexsolo.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
public class RegistrationRequestDto {
    private String username;
    private String email;
    private String password;
//    private Date birthday;
}
