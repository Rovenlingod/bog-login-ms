package com.example.bogloginms.dto;

import lombok.Data;
import lombok.ToString;

@Data
public class LoginRequestDTO {
    private String login;
    @ToString.Exclude
    private String password;
}
