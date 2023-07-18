package com.hanex.fms.starter.member.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.convert.EntityRowMapper;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.hanex.fms.starter.member.domain.Member;
import com.hanex.fms.starter.member.dto.MemberSearchCondition;

import java.util.List;

@Slf4j
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final NamedParameterJdbcOperations jdbcOperations;
    private final EntityRowMapper<Member> rowMapper;

    public MemberRepositoryImpl(NamedParameterJdbcOperations jdbcOperations,
                                RelationalMappingContext mappingContext,
                                JdbcConverter jdbcConverter) {

        this.jdbcOperations = jdbcOperations;
        this.rowMapper = new EntityRowMapper<>(
                (RelationalPersistentEntity<Member>) mappingContext.getRequiredPersistentEntity(Member.class),
                jdbcConverter);
    }



    @Override
    public Page<Member> findByCustomerIdAndSearchCondition(Pageable pageable, MemberSearchCondition condition) {

        // SORT property 는 entity property 명으로 변경되야 한다.
        // (https://github.com/spring-projects/spring-data-jdbc/pull/210)

        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("customerId",condition.getCustomerId())
                .addValue("managerName",condition.getManagerName())
                .addValue("offset", pageable.getOffset())
                .addValue("pageSize", pageable.getPageSize());

        String sql = MemberSql.SELECT_BY_CONDITION(pageable,condition);

        List<Member> members = this.jdbcOperations.query(
                sql
                ,parameterSource
                ,this.rowMapper
        );

        return PageableExecutionUtils.getPage(members, pageable, () ->
                this.jdbcOperations.queryForObject(MemberSql.COUNT_BY_ID_WITH_CUSTOMER(condition)
                        ,parameterSource
                        , Long.class));
    }

    /**
     * member 수정
     */
    @Override
    public boolean updateMemberInfo(String id , Member member) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id)
                //.addValue("memberType", member.getMemberType())
                .addValue("ceoName", member.getCeoName())
                .addValue("registrationNumber", member.getRegistrationNumber())
                .addValue("phone", member.getPhone())
                .addValue("memo", member.getMemo());
        int affected = jdbcOperations.update(MemberSql.UPDATE, parameterSource);
        return affected == 1;
    }
}
