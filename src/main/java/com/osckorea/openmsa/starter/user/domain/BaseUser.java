package com.osckorea.openmsa.starter.user.domain;

import javax.validation.constraints.NotNull;

import com.osckorea.openmsa.global.enums.UserRole;

import lombok.Builder;
import lombok.Value;

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
