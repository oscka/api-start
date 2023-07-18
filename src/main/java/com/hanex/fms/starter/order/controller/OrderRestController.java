package com.hanex.fms.starter.order.controller;

import com.hanex.fms.global.api.ApiResponseDto;
import com.hanex.fms.starter.order.dto.OrderDto;
import com.hanex.fms.starter.order.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "출고 API" ,description = "OMS 출고")
@RequestMapping("/v1/order")
@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final OrderService orderService;

    @Operation(summary = "출고 생성",description = "출고 생성")
    @PostMapping
    public ApiResponseDto createOrder(@RequestBody @Valid OrderDto.CreateOrderRequest request){
        orderService.createOrder(request);
        return ApiResponseDto.DEFAULT_OK;
    }


    @Operation(summary = "출고 단일 조회",description = "출고 아이디로 단일 조회")
    @GetMapping("/{id}")
    public ApiResponseDto findByOrderId(@PathVariable Long id){
        orderService.findByOrderId(id);
        return ApiResponseDto.DEFAULT_OK;
    }



}
