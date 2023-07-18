package com.hanex.fms.starter.product.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hanex.fms.starter.product.domain.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product,Long> ,ProductRepositoryCustom {


    @Query(ProductSql.SELECT_BY_ID)
    Optional<Product> selectById(Long id);


    @Modifying
    @Query(ProductSql.UPDATE)
    int updateProduct(@Param("name")String name,@Param("stock")Integer stock,@Param("id") Long id);



}
