package com.hanex.fms.starter.product.dto;

import com.hanex.fms.global.enums.TableStatus;
import com.hanex.fms.global.security.CustomUser;
import com.hanex.fms.starter.product.domain.Product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.Instant;
import java.util.UUID;

public class ProductDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ProductSaveRequest {

        @Schema(description = "상품명",example = "스테이크볶음밥")
        private String name;

        @Schema(description = "상품 재고 수량",example = "100")
        private Integer stock;

        @Schema(description = "상품설명",example = "남녀노소 좋아하는 스테이크볶음밥")
        private String description;


        @Schema(description = "고객사 ID",example = "e1065afe-5d59-4293-b3f9-037d3122b8b6")
        private String customerId;

        @Schema(description = "공급사 ID",example = "5edfc5a9-3675-4b6d-a72e-43a7f5c6cf0f")
        private String supplyCompanyId;


        @Schema(description = "활성화 여부",example = "Y")
        private TableStatus productStatus;

        public Product toEntity(CustomUser user){
            return Product.builder()
                    .name(name)
                    .stock(stock)
                    .description(description)
                    .productStatus(productStatus)
                    .productCode(UUID.randomUUID().toString().replaceAll("-",""))
                    .customerId(AggregateReference.to(customerId))
                    .supplyCompanyId(AggregateReference.to(supplyCompanyId))
                    .createdBy(AggregateReference.to(String.valueOf(user.getUserId())))
                    .updatedBy(AggregateReference.to(String.valueOf(user.getUserId())))
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ProductInfoResponse {

        @Schema(description = "상품 고유 ID",example = "212")
        private Long id;

        @Schema(description = "상품명",example = "스테이크볶음밥")
        private String name;


        @Schema(description = "상품설명",example = "남녀노소 좋아하는 스테이크볶음밥")
        private String description;


        @Schema(description = "상품 코드",example = "880952124212")
        private String productCode;


        @Schema(description = "고객사 ID",example = "e1065afe-5d59-4293-b3f9-037d3122b8b6")
        private String customerId;

        @Schema(description = "공급사 ID",example = "5edfc5a9-3675-4b6d-a72e-43a7f5c6cf0f")
        private String supplyCompanyId;

        @Schema(description = "활성화 여부",example = "Y")
        private TableStatus productStatus;

        @Schema(description = "생성자 ID",example = "e1065afe-5d59-4293-b3f9-037d3122b8b6")
        private String createdBy;

        @Schema(description = "생성일시" ,example = "2023-06-07")
        private Instant createdAt;

        @Schema(description = "수정자 ID",example = "e1065afe-5d59-4293-b3f9-037d3122b8b6")
        private String updatedBy;

        @Schema(description = "수정일시" ,example = "2023-06-07")
        private Instant updatedAt;

    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ProductInfoUpdateRequest{

        @Schema(description = "상품명",example = "스테이크볶음밥")
        private String name;

        @Schema(description = "상품 재고 수량",example = "100")
        private Integer stock;

        @Schema(description = "상품설명",example = "남녀노소 좋아하는 스테이크볶음밥")
        private String description;

        @Schema(description = "고객사 ID",example = "e1065afe-5d59-4293-b3f9-037d3122b8b6")
        private String customerId;

        @Schema(description = "공급사 ID",example = "5edfc5a9-3675-4b6d-a72e-43a7f5c6cf0f")
        private String supplyCompanyId;


        @Schema(description = "활성화 여부",example = "Y")
        private TableStatus productStatus;

        public Product toEntity(CustomUser user){
            return Product.builder()
                    .name(name)
                    .description(description)
                    .productCode(UUID.randomUUID().toString().replaceAll("-",""))
                    .customerId(AggregateReference.to(customerId))
                    .supplyCompanyId(AggregateReference.to(supplyCompanyId))
                    .createdBy(AggregateReference.to(user.getUserId().toString()))
                    .updatedBy(AggregateReference.to(user.getUserId().toString()))
                    .build();
        }
    }


    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ProductUpdateRequest{

        @Schema(description = "상품명",example = "스테이크볶음밥")
        private String name;

        @Schema(description = "상품 재고 수량",example = "100")
        private Integer stock;

    }

}
