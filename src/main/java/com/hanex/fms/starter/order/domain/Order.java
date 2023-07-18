package com.hanex.fms.starter.order.domain;

import com.hanex.fms.global.enums.OrderStatus;
import com.hanex.fms.starter.customer.domain.Customer;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * 주문
 */
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table("order_tb")
public class Order {

	@Id
	private Long id;
	private AggregateReference<Customer, @NotNull String> customerId; // 고객사 ID (화주)
	private OrderStatus orderStatus; //주문진행상태
	private Instant orderDateTime; //주문등록일시

	@Valid
	@MappedCollection(idColumn = "order_id", keyColumn = "product_id")
	@Builder.Default
	private List<OrderLineItem> orderLineItems = new ArrayList<>(); // 판매상품 리스트
}
