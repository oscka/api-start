package com.hanex.fms.starter.user.repository;

import java.util.UUID;

import com.hanex.fms.starter.user.domain.User;

public interface UserRepositoryCustom {

    void deleteById(UUID id);

    void delete(User entity);
}
