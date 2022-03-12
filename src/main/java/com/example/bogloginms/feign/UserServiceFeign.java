package com.example.bogloginms.feign;

import com.example.bogloginms.feign.feignDto.OAuthUserDTO;
import com.example.bogloginms.feign.feignDto.UserDTO;
import com.example.bogloginms.feign.feignDto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserServiceFeign {

    @GetMapping("/api/user/{id}")
    UserDTO getUserById(@PathVariable(name = "id") String id);

    @GetMapping("/api/user")
    UserDTO getUserByLogin(@RequestParam(name = "login") String login);

    @PostMapping("/api/user/oauth")
    UserResponseDTO saveOrUpdateOAuthUser(OAuthUserDTO oAuthUserDTO);
}
