package com.hanex.fms.starter.member.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hanex.fms.starter.member.domain.Member;
import com.hanex.fms.starter.member.dto.MemberSearchCondition;


public interface MemberRepositoryCustom {

    Page<Member> findByCustomerIdAndSearchCondition(
            Pageable pageable,
            MemberSearchCondition memberSearchCondition
    );

    boolean updateMemberInfo(String id , Member member);
}
