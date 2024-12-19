package com.osckorea.openmsa.starter.user.domain;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.relational.core.mapping.Column;

import com.osckorea.openmsa.global.enums.UserRole;

import jakarta.validation.constraints.NotNull;

@Builder
@Value
public class BaseUser {

    @NotNull
    private String loginId;

    @NotNull
    private String password;

    @NotNull
    private UserRole userRole;

}
