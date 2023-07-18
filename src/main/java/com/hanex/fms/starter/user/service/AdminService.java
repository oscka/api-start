package com.hanex.fms.starter.user.service;

import com.hanex.fms.global.annotation.CustomLog;
import com.hanex.fms.global.enums.UserStatus;
import com.hanex.fms.global.exception.Exception404;
import com.hanex.fms.starter.user.domain.User;
import com.hanex.fms.starter.user.dto.UserDto;
import com.hanex.fms.starter.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final UserRepository userRepository;

    public UserDto.UserInfoResponse findById(UUID id){
        return userRepository.findById(id).orElseThrow(()
                -> new Exception404("존재하지 않는 계정입니다.")).toDto();
    }

    public void userBan(UUID id){
        User user = userRepository.findById(id).orElseThrow(()->new Exception404("존재하지 않는 계정입니다."));
        user.ban();
        userRepository.save(user);
    }

    public List<UserDto.UserInfoResponse> findByUserStatus(Pageable pageable, String userStatus){
        return userRepository.findByUserStatus(pageable,UserStatus.valueOf(userStatus)).stream().map(users -> users.toDto()).collect(Collectors.toList());
    }

}
