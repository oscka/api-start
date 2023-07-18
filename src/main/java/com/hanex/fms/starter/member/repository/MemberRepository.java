package com.hanex.fms.starter.member.repository;

import com.hanex.fms.global.util.jdbc.WithInsert;
import com.hanex.fms.starter.member.domain.Member;
import com.hanex.fms.starter.user.domain.BaseUser;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends CrudRepository<Member, String> , MemberRepositoryCustom, WithInsert<Member> {


    // baseUser > loginId 중복 검사
    @Query(MemberSql.SELECT_BY_LOGIN_ID)
    Optional<BaseUser> selectByLoginId(@Param("loginId") String loginId);
}
