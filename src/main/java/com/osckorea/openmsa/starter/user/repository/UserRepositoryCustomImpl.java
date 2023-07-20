package com.osckorea.openmsa.starter.user.repository;

import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.transaction.annotation.Transactional;

import com.osckorea.openmsa.starter.user.domain.User;

import java.util.UUID;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	private final JdbcAggregateOperations jdbcAggregateOperations;

	public UserRepositoryCustomImpl(JdbcAggregateOperations jdbcAggregateOperations) {
		this.jdbcAggregateOperations = jdbcAggregateOperations;
	}

	@Transactional
	public void deleteById(UUID id) {
		User user = this.jdbcAggregateOperations.findById(id, User.class);
		if (user == null) {
			throw new TransientDataAccessResourceException("user does not exist.id: " + id);
		}
		this.delete(user);
	}

	@Transactional
	public void delete(User entity) {
		entity.delete();
		this.jdbcAggregateOperations.update(entity);
	}

}
