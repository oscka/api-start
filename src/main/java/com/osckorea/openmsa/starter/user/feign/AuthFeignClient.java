package com.osckorea.openmsa.starter.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.osckorea.openmsa.starter.user.dto.UserDto;

import java.util.Map;

@FeignClient(value = "authFeignClient", url = "${api.auth-service.url}")
public interface AuthFeignClient {

    @PostMapping("/security/jwtlogin")
    Map<Object,String> getToken(@RequestBody UserDto.LoginRequest request);
}
