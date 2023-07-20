package com.osckorea.openmsa.starter.user.repository;

import java.util.UUID;

import com.osckorea.openmsa.starter.user.domain.User;

public interface UserRepositoryCustom {

    void deleteById(UUID id);

    void delete(User entity);
}
