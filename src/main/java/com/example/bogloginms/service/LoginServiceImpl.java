package com.example.bogloginms.service;

import com.example.bogloginms.dto.LoginRequestDTO;
import com.example.bogloginms.exception.WrongLoginOrPasswordException;
import com.example.bogloginms.feign.UserServiceFeign;
import com.example.bogloginms.feign.feignDto.UserDTO;
import com.example.bogloginms.jwt.JwtTokenProvider;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    private UserServiceFeign userServiceFeign;
    private JwtTokenProvider jwtTokenProvider;

    public LoginServiceImpl(UserServiceFeign userServiceFeign, JwtTokenProvider jwtTokenProvider) {
        this.userServiceFeign = userServiceFeign;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginRequestDTO loginRequestDTO) {
        UserDTO user = userServiceFeign.getUserByLogin(loginRequestDTO.getLogin());

        if (Objects.isNull(user)) {
            throw new WrongLoginOrPasswordException("Provided credentials are wrong");
        }

        if (!BCrypt.checkpw(loginRequestDTO.getPassword(), user.getEncryptedPassword())) {
            throw new WrongLoginOrPasswordException("Provided credentials are wrong");
        }

        return jwtTokenProvider.createToken(loginRequestDTO.getLogin());
    }
}
