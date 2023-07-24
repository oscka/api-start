package com.osckorea.openmsa.starter.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import com.osckorea.openmsa.global.api.ApiResponseDto;
import com.osckorea.openmsa.global.enums.UserStatus;
import com.osckorea.openmsa.starter.user.service.AdminService;

import java.util.UUID;

@Tag(name = "관리자",description = "관리자 API")
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "사용자 id 로 상세조회", description = "UUID 로 조회")
    @GetMapping("/user/{id}")
    public ApiResponseDto findById(@PathVariable(value = "id") UUID id){
        return ApiResponseDto.createOK(adminService.findById(id));
    }

    @Operation(summary = "UserStatus > 정지 update",description = "사용자 상태 정지")
    @PutMapping("/user/ban/{id}")
    public ApiResponseDto userBan(@PathVariable(value = "id") UUID id){
        adminService.userBan(id);
        return ApiResponseDto.DEFAULT_OK;
    }

    @Operation(summary = "UserStatus 를 조건으로 User List 조회",description = "사용자 리스트 조회")
    @GetMapping("/users")
    public ApiResponseDto findByUserStatus(@PageableDefault(size = 50, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                          @RequestParam(defaultValue = "ACTIVE") String userStatus){
        return ApiResponseDto.createOK(adminService.findByUserStatus(pageable,userStatus));
    }





}
