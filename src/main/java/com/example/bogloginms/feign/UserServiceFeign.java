package com.example.bogloginms.feign;

import com.example.bogloginms.feign.feignDto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceFeign {

    @GetMapping("/api/user/{id}")
    UserDTO getUserById(@PathVariable(name = "id") String id);

    @GetMapping("/api/user/{login}")
    UserDTO getUserByLogin(@PathVariable(name = "login") String login);
}
