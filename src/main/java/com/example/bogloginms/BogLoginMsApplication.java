package com.example.bogloginms;

import com.example.bogloginms.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableConfigurationProperties(JwtProperties.class)
@EnableAspectJAutoProxy
public class BogLoginMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BogLoginMsApplication.class, args);
    }

}
