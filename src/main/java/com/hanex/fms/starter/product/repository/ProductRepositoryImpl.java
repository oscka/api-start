package com.hanex.fms.starter.product.repository;

import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.transaction.annotation.Transactional;

import com.hanex.fms.starter.product.domain.Product;

import java.util.UUID;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JdbcAggregateOperations jdbcAggregateOperations;

    public ProductRepositoryImpl(JdbcAggregateOperations jdbcAggregateOperations) {
        this.jdbcAggregateOperations = jdbcAggregateOperations;
    }

    @Transactional
    public void deleteById(UUID id) {
        Product product = this.jdbcAggregateOperations.findById(id, Product.class);
        if (product == null) {
            throw new TransientDataAccessResourceException("product does not exist.id: " + id);
        }
        this.delete(product);
    }

    @Transactional
    public void delete(Product entity) {
        entity.delete();
        this.jdbcAggregateOperations.update(entity);
    }
}
