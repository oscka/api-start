package com.osckorea.openmsa.global.util.jdbc;

import org.springframework.data.jdbc.core.JdbcAggregateOperations;

public class WithInsertImpl<T> implements WithInsert<T>{

	private final JdbcAggregateOperations jdbcAggregateOperations;

	public WithInsertImpl(JdbcAggregateOperations jdbcAggregateOperations) {
		this.jdbcAggregateOperations = jdbcAggregateOperations;
	}

	@Override
	public JdbcAggregateOperations getJdbcAggregateOperation() {
		return this.jdbcAggregateOperations;
	}

}