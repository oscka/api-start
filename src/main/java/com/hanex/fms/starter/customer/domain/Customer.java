package com.hanex.fms.starter.customer.domain;

import com.hanex.fms.global.enums.Group;
import com.hanex.fms.global.enums.UserStatus;
import com.hanex.fms.global.util.encrypt.EncryptString;
import com.hanex.fms.starter.customer.dto.CustomerDto;
import com.hanex.fms.starter.user.domain.BaseUser;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.time.Instant;

@EqualsAndHashCode(of = "id")
@Builder
@Getter
@ToString
@Table("customer")
public class Customer {

    @Id
    private String id;

    @PositiveOrZero
    @Version
    private long version;

    @Valid
    @Column("user_id")
    private BaseUser baseUser;

    private UserStatus customerStatus;

    @Column("customer_name")
    private String name;

    private String email;

    private Group customerGroup;

    @Valid
    private EncryptString phone;

    private String memo;

    @Builder.Default
    @CreatedDate
    private Instant createdAt = Instant.now();

    @Builder.Default
    @LastModifiedDate
    private Instant updatedAt = Instant.now();

//    @CreatedBy
//    private AggregateReference<User, @NotNull UUID> createdBy;

//    @LastModifiedBy
//    private AggregateReference<User, @NotNull UUID> updatedBy;

    // ---------------- 비지니스 로직 --------------- //

    public CustomerDto.CustomerInfoResponse toDto(){
        return CustomerDto.CustomerInfoResponse.builder()
                .id(this.id)
                .customerStatus(this.customerStatus)
                .customerGroup(this.customerGroup)
                .loginId(this.baseUser.getLoginId())
                .role(this.baseUser.getUserRole())
                .email(this.email)
                .name(this.name)
                .phone(this.phone.getValue())
                .memo(this.memo)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }


}
