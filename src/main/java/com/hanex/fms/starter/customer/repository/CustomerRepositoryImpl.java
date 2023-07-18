package com.hanex.fms.starter.customer.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.convert.EntityRowMapper;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import com.hanex.fms.starter.customer.domain.Customer;
import com.hanex.fms.starter.customer.dto.CustomerDto;

import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

    private final NamedParameterJdbcOperations jdbcOperations;
    private final EntityRowMapper<Customer> rowMapper;

    public CustomerRepositoryImpl(NamedParameterJdbcOperations jdbcOperations
                                ,RelationalMappingContext mappingContext
                                ,JdbcConverter jdbcConverter){
        this.jdbcOperations = jdbcOperations;
        this.rowMapper = new EntityRowMapper<>(
                (RelationalPersistentEntity<Customer>) mappingContext.getRequiredPersistentEntity(Customer.class),jdbcConverter);
    }


    @Override
    public List<CustomerDto.CustomerInfoResponse> searchCustomer(Pageable pageable) {
        return null;
    }
}
