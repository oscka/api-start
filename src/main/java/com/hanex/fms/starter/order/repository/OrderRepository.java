package com.hanex.fms.starter.order.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hanex.fms.starter.order.domain.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order,Long> ,OrderRepositoryCustom {
}
