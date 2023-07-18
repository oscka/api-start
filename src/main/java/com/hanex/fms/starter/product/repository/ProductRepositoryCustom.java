package com.hanex.fms.starter.product.repository;

import java.util.UUID;

import com.hanex.fms.starter.product.domain.Product;

public interface ProductRepositoryCustom {

    void deleteById(UUID id);

    void delete(Product product);



}
