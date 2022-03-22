package com.example.bogloginms.logging;

import com.example.bogloginms.dto.LoginRequestDTO;
import com.example.bogloginms.exception.WrongLoginOrPasswordException;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
@Aspect
@Log4j2
public class LoggingAspect {

    @Pointcut("within(com.example.bogloginms..*)")
    protected void loggingAllOperations() {
    }

    @AfterReturning("execution(* com.example.bogloginms.service.LoginService.login(..)) && args(loginRequestDTO)")
    public void logLogin(JoinPoint joinPoint, LoginRequestDTO loginRequestDTO) {
        log.info("User with username \"" + loginRequestDTO.getLogin() + "\" logged in successfully.");
    }

    @AfterReturning(value = "execution(* com.example.bogloginms.service.CustomOidcUserService.loadUser(..))", returning = "user")
    public void logLogin(JoinPoint joinPoint, OidcUser user) {
        log.info("User with username \"" + user.getAttribute("email") + "\" logged in successfully using google oauth.");
    }

    @AfterReturning(value = "execution(* com.example.bogloginms.controller.LoginController.validateToken(..))", returning = "username")
    public void logValidatingToken(JoinPoint joinPoint, ResponseEntity<String> username) {
        log.info("User with username \"" + username.getBody() + "\" validated his token successfully." );
    }

    @AfterThrowing(value = "execution(* com.example.bogloginms.service.LoginService.login(..)) && args(loginRequestDTO)", throwing = "exception")
    public void logLoginExceptions(JoinPoint joinPoint, LoginRequestDTO loginRequestDTO, WrongLoginOrPasswordException exception) {
        log.warn("User with username \"" + loginRequestDTO.getLogin() + "\" tried to log in using wrong credentials.");
        log.warn("Exception : " + exception.getClass().getName() + ". Message: " + exception.getMessage());
    }

    @AfterThrowing(pointcut = "loggingAllOperations()", throwing = "exception")
    public void logAllExceptions(JoinPoint joinPoint, Throwable exception) {
        if (!(exception instanceof WrongLoginOrPasswordException)) {
            log.error("Exception occurred while executing method \"" + joinPoint.getSignature() + "\"");
            log.error("Cause: " + exception.getCause() + ". Exception : " + exception.getClass().getName() + ". Message: " + exception.getMessage());
            log.error("Provided arguments: ");
            Object[] arguments = joinPoint.getArgs();
            for (Object a :
                    arguments) {
                if (Objects.isNull(a)) {
                    log.error("[null]");
                } else {
                    log.error("Class name: " + a.getClass().getSimpleName() + ". Value: " + a);
                }
            }
        }
    }
}
