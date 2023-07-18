package com.hanex.fms.starter.order.service;

import com.hanex.fms.global.exception.Exception404;
import com.hanex.fms.starter.order.domain.Order;
import com.hanex.fms.starter.order.dto.OrderDto;
import com.hanex.fms.starter.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public void createOrder(OrderDto.CreateOrderRequest request){
        orderRepository.save(request.toEntity());
    }

    public void findByOrderId(Long id){
        Order order = orderRepository.findById(id).orElseThrow(()->new Exception404("해당하는 주문을 찾을 수 없습니다."));
    }




}
