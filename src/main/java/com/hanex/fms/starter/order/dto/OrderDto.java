package com.hanex.fms.starter.order.dto;

import com.hanex.fms.global.enums.OrderStatus;
import com.hanex.fms.starter.order.domain.Order;
import com.hanex.fms.starter.order.domain.OrderLineItem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDto {


    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class CreateOrderRequest {

        @Schema(description = "주문상태",example = "스테이크볶음밥")
        private OrderStatus orderStatus;



        @Schema(description = "주문일",example = "스테이크볶음밥")
        private Instant orderDateTime;

        @Schema(description = "고객사 ID",example = "스테이크볶음밥")
        private String customerId;

        private List<OrderItem> orderItems;

        public Order toEntity(){
            return Order.builder()
                    .orderStatus(orderStatus)
                    .orderDateTime(orderDateTime)
                    .customerId(AggregateReference.to(customerId))
                    .orderLineItems(orderItems
                            .stream()
                            .map(OrderItem::toEntity).collect(Collectors.toList()))
                    .build();
        }


    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class OrderItem {

        @Schema(description = "상품 id",example = "스테이크볶음밥")
        private Long productId;
        @Schema(description = "수량",example = "10")
        private Integer quantity;

        public OrderLineItem toEntity(){
            return OrderLineItem.builder()
                    .productId(AggregateReference.to(productId))
                    .quantity(quantity)
                    .build();
        }
    }


}
