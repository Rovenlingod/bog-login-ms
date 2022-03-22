package com.example.bogloginms.service;

import com.example.bogloginms.dto.LoginRequestDTO;
import com.example.bogloginms.exception.UnauthorisedException;
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

    /**
     * Authenticates user
     * @param loginRequestDTO contains login and password
     * @return JWT token
     * @exception WrongLoginOrPasswordException if user doesn't exist
     * @exception WrongLoginOrPasswordException if password is wrong
     */
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

    /**
     * Validates JWT token
     * @param token JWT token
     * @return username bound to the token
     */
    @Override
    public String validateToken(String token) {
        if (jwtTokenProvider.validateToken(token)) {
            return jwtTokenProvider.getUsername(token);
        } else {
            throw new UnauthorisedException("Provided token is invalid");
        }
    }
}
