package com.hanex.fms.starter.customer.repository;

import org.springframework.data.domain.Pageable;

import com.hanex.fms.starter.customer.dto.CustomerDto;

import java.util.List;

public interface CustomerRepositoryCustom {

    List<CustomerDto.CustomerInfoResponse> searchCustomer(Pageable pageable);
}
