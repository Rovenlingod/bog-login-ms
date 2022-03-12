package com.example.bogloginms.feign.feignDto;

import lombok.Data;

@Data
public class UserDTO {
    private String id;
    private String login;
    private String encryptedPassword;
    private String email;
    private String firstName;
    private String lastName;
}
