package com.example.bogloginms;

import com.example.bogloginms.dto.LoginRequestDTO;
import com.example.bogloginms.exception.WrongLoginOrPasswordException;
import com.example.bogloginms.feign.UserServiceFeign;
import com.example.bogloginms.feign.feignDto.UserDTO;
import com.example.bogloginms.jwt.JwtTokenProvider;
import com.example.bogloginms.service.LoginServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @Mock
    private UserServiceFeign userServiceFeign;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private LoginServiceImpl loginService;

    @Test
    public void givenValidUser_whenLogin_thenReturnToken() {
        LoginRequestDTO loginRequest = LoginRequestDTO.builder()
                .login("testLogin")
                .password("testPassword")
                .build();

        String expectedToken = "eyJhbGciOiJIUzI1NiJ9.eyJVc2VybmFtZSI6InRlc3RMb2dpbiIsImV4cCI6MTY0ODAzODQ3NCwiaWF0IjoxNjQ4MDM4NDc0fQ.rbxEHsLjowg2HLpoq3d9MaUxCGa0O1UFzwantjDHXjo"; // random JWT token

        try (MockedStatic<BCrypt> mockedStatic = Mockito.mockStatic(BCrypt.class)) {
            mockedStatic.when(() -> BCrypt.checkpw("testPassword", "encodedPassword")).thenReturn(true);

            doReturn(expectedToken).when(jwtTokenProvider).createToken("testLogin");
            doReturn(UserDTO.builder()
                    .login("testLogin")
                    .encryptedPassword("encodedPassword")
                    .build()).when(userServiceFeign).getUserByLogin("testLogin");

            String resultToken = loginService.login(loginRequest);

            assertEquals(expectedToken, resultToken);
        }
    }

    @Test(expected = WrongLoginOrPasswordException.class)
    public void givenNullUser_whenLogin_thenThrowWrongLoginOrPasswordException() {
        loginService.login(null);
    }

    @Test(expected = WrongLoginOrPasswordException.class)
    public void givenNullLogin_whenLogin_thenThrowWrongLoginOrPasswordException() {
        LoginRequestDTO loginRequest = LoginRequestDTO.builder()
                .login(null)
                .password("testPassword")
                .build();
        loginService.login(loginRequest);
    }

    @Test(expected = WrongLoginOrPasswordException.class)
    public void givenNullPassword_whenLogin_thenThrowWrongLoginOrPasswordException() {
        LoginRequestDTO loginRequest = LoginRequestDTO.builder()
                .login("testLogin")
                .password(null)
                .build();
        loginService.login(loginRequest);
    }

    @Test(expected = WrongLoginOrPasswordException.class)
    public void givenNonExistentUser_whenLogin_thenThrowWrongLoginOrPasswordException() {
        LoginRequestDTO loginRequest = LoginRequestDTO.builder()
                .login("testLogin")
                .password("testPassword")
                .build();

        try (MockedStatic<BCrypt> mockedStatic = Mockito.mockStatic(BCrypt.class)) {
            mockedStatic.when(() -> BCrypt.checkpw("testPassword", "encodedPassword")).thenReturn(true);
            doReturn(null).when(userServiceFeign).getUserByLogin("testLogin");

            loginService.login(loginRequest);
        }
    }

    @Test(expected = WrongLoginOrPasswordException.class)
    public void givenWrongPassword_whenLogin_thenThrowWrongLoginOrPasswordException() {
        LoginRequestDTO loginRequest = LoginRequestDTO.builder()
                .login("testLogin")
                .password("testPassword")
                .build();

        try (MockedStatic<BCrypt> mockedStatic = Mockito.mockStatic(BCrypt.class)) {
            mockedStatic.when(() -> BCrypt.checkpw("testPassword", "encodedPassword")).thenReturn(false);
            doReturn(UserDTO.builder()
                    .encryptedPassword("encodedPassword")
                    .build()).when(userServiceFeign).getUserByLogin("testLogin");

            loginService.login(loginRequest);
        }
    }

}
