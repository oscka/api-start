package com.hanex.fms.starter.customer.service;

import com.hanex.fms.global.exception.Exception400;
import com.hanex.fms.global.exception.Exception404;
import com.hanex.fms.starter.customer.domain.Customer;
import com.hanex.fms.starter.customer.dto.CustomerDto;
import com.hanex.fms.starter.customer.repository.CustomerRepository;
import com.hanex.fms.starter.user.domain.BaseUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping("/v1/customer")
@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<CustomerDto.CustomerInfoResponse> searchCustomer(Pageable pageable){
        return customerRepository.searchCustomer(pageable);
    }

    @Transactional(readOnly = true)
    public CustomerDto.CustomerInfoResponse findById (String id){
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new Exception404("존재하지 않는 고객사 입니다."));
        return customer.toDto();
    }

    @Transactional
    public String save(CustomerDto.SaveRequest save){

        // baseUser > loginId 중복 검사
        Optional<BaseUser> baseUser = customerRepository.selectByLoginId(save.getLoginId());
        if (baseUser.isPresent()){
            throw new Exception400("loginId","중복되는 로그인 아이디 입니다.");
        }

        Customer customer = customerRepository.save(save.toEntity());
        return customer.getId();
    }

    @Transactional
    public void update(String id,CustomerDto.UpdateRequest update){
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new Exception404("존재하지 않는 고객사 입니다."));

        boolean isUpdated = customerRepository.changeCustomer(
                update.getEmail()
                ,update.getCustomerGroup()
                , update.getName()
                , update.getPhone()
                , update.getMemo()
                , id
        );

        log.debug("Customer Update Result :: {}",isUpdated);
    }

    @Transactional
    public void delete (String id){
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new Exception404("존재하지 않는 고객사 입니다."));
        // 실제 ROW 삭제 (HARD DELETE)
        customerRepository.delete(customer);
    }


}
