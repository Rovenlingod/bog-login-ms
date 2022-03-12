package com.example.bogloginms.feign.feignDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthUserDTO {

    private String email;
    private String name;
}
