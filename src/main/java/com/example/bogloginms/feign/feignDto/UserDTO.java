package com.example.bogloginms.feign.feignDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private String id;
    private String login;
    private String encryptedPassword;
    private String email;
    private String firstName;
    private String lastName;
}
