package com.hanex.fms.starter.product.domain;


import com.hanex.fms.global.enums.TableStatus;
import com.hanex.fms.starter.customer.domain.Customer;
import com.hanex.fms.starter.member.domain.Member;
import com.hanex.fms.starter.product.dto.ProductDto;
import com.hanex.fms.starter.user.domain.User;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.*;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.Instant;

/**
 * 상품
 */

@EqualsAndHashCode(of = "id")
@ToString
@Getter
@Table("product_tb")
public class Product {

	@Id
	private Long id;

	@PositiveOrZero
	@Version
	private long version;

	@Column("product_name")
	private String name;

	private Integer stock; // 재고

	private String description; // 상품 설명

	private String productCode; // 상품코드

	private TableStatus productStatus; // 상품 활성화 여부


	private AggregateReference<Customer, @NotBlank @Size(max = 200) String> customerId;  // 고객 아이디
	private AggregateReference<Member, @NotBlank @Size(max = 200) String> supplyCompanyId; // 공급사 아이디

	@CreatedDate
	private Instant createdAt;

	@LastModifiedDate
	private Instant updatedAt;

	@CreatedBy
	private AggregateReference<User,@NotBlank String> createdBy;

	@LastModifiedBy
	private AggregateReference<User,@NotBlank String> updatedBy;

	@Builder
	public Product(String name, Integer stock, String description, String productCode, TableStatus productStatus, AggregateReference<Customer, @NotBlank @Size(max = 200) String> customerId, AggregateReference<Member, @NotBlank @Size(max = 200) String> supplyCompanyId, Instant createdAt, Instant updatedAt, AggregateReference<User, @NotBlank String> createdBy, AggregateReference<User, @NotBlank String> updatedBy) {
		this.name = name;
		this.stock = stock;
		this.description = description;
		this.productCode = productCode;
		this.productStatus = productStatus;
		this.customerId = customerId;
		this.supplyCompanyId = supplyCompanyId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}

	public ProductDto.ProductInfoResponse toDto(){
		return ProductDto.ProductInfoResponse.builder()
				.id(id)
				.productCode(productCode)
				.productStatus(productStatus)
				.name(name)
				.description(description)
				.supplyCompanyId(supplyCompanyId.getId())
				.customerId(customerId.getId())
				.createdAt(createdAt)
				.createdBy(String.valueOf(this.createdBy.getId()))
				.updatedAt(updatedAt)
				.updatedBy(String.valueOf(this.updatedBy.getId()))
				.build();
	}

	// ---------------- 비지니스 로직 ---------------------------
	public void delete(){
		this.productStatus = TableStatus.N;
	}


}
