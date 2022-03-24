package com.example.bogloginms.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
public class LoginRequestDTO {
    private String login;
    @ToString.Exclude
    private String password;
}
