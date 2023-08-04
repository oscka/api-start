package com.osckorea.openmsa.starter.user.kafka.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.osckorea.openmsa.starter.user.dto.UserDto;
import com.osckorea.openmsa.starter.user.feign.AuthFeignClient;
import com.osckorea.openmsa.starter.user.kafka.event.UserFindEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserFindService {

	private final AuthFeignClient authFeignClient;
    
    public Map<Object, String> login(UserFindEvent UserFindEvent){

        UserDto.LoginRequest request = new UserDto.LoginRequest(UserFindEvent.getLoginId(), "test1234");
		return authFeignClient.getToken(request);
	}
}
