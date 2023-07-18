package com.hanex.fms.starter.order.domain;

import lombok.*;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.hanex.fms.starter.product.domain.Product;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class OrderLineItem {

    private AggregateReference<Product, @NotNull Long> productId; // 상품 아이디
    private Integer quantity; // 주문 수량

}
