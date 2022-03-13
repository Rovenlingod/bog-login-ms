package com.example.bogloginms.service;

import com.example.bogloginms.dto.LoginRequestDTO;
import com.example.bogloginms.feign.feignDto.UserDTO;

public interface LoginService {
    String login(LoginRequestDTO loginRequestDTO);
    String validateToken(String token);
}
