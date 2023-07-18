package com.hanex.fms.starter.member.repository;

import com.hanex.fms.global.enums.EnumType;
import com.hanex.fms.starter.member.dto.MemberSearchCondition;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class MemberSql {

    public final static String SELECT_BY_LOGIN_ID = """
            SELECT 
              b.login_id
                , b.user_id
                , b.user_role
            FROM base_user B
            WHERE B.login_id = :loginId
        """;

    public static String SELECT_BY_CONDITION(Pageable pageable,MemberSearchCondition member){
        return """
                SELECT 
                     m.id 
                     , m.manager_name 
                     , m.member_code 
                     , m.ceo_name 
                     , m.member_type
                     , m.member_status
                     , m.registration_number
                     , m.phone 
                     , m.memo 
                     , m.customer_id 
                     , m.email
                     , m.created_by
                     , m.created_at
                     , m.updated_by
                     , m.updated_at
                FROM member_tb m 
                """
                +
                whereAnd (
                        notEmpty(member.getCustomerId(), "customer_id = :customerId"),
                        notEmpty(member.getManagerName(), "manager_name = :managerName"),
                        notEmptyForObject(member.getMemberType(), "member_type = '"),
                        notEmptyForObject(member.getMemberStatus(), "member_status = '")
                )
                +
                " ORDER BY " + orderBy(pageable.getSort())
                +
                " LIMIT :pageSize OFFSET :offset"
                ;
    }


    public static String COUNT_BY_ID_WITH_CUSTOMER(MemberSearchCondition member){
        return """
            SELECT
                COUNT(*)
            FROM member_tb m
            """
                +
            whereAnd (
                    notEmpty(member.getCustomerId(), "customer_id = :customerId"),
                    notEmpty(member.getManagerName(), "manager_name = :managerName"),
                    notEmptyForObject(member.getMemberType(), "member_type = '"),
                    notEmptyForObject(member.getMemberStatus(), "member_status = '")
            );
    }

    public static final String CUSTOMER_ID_CONDITION =
            "AND customer_id = :customerId \n";

    private static String notEmpty(String param, String condition) {
        return StringUtils.isEmpty(param)? null: condition;
    }

    /**
       spring data jdbc 에서 enum name 을 인식하지 못하므로
       해당 메서드에서 null check 하는 동시에 enum id 를 넣어줌
     */
    private static String notEmptyForObject(EnumType param, String condition) {
        return ObjectUtils.isEmpty(param)? null: condition + param.getId() + "'";
    }

    private static String whereAnd(String ... conditions) {

        List<String> finalCond = Arrays.stream(conditions)
                .filter(Objects::nonNull)
                .filter(Predicate.not(String::isEmpty))
                .collect(Collectors.toList());

        //Assert.notEmpty(finalCond);
        if (finalCond.isEmpty()){
            return "";
        } else {
            return "WHERE " + finalCond.stream().collect(joining("\nAND "));
        }

    }


    private static String orderBy(Sort sort) {
        return sort.stream()
                .map(order -> order.getProperty() + " " + order.getDirection().name())
                .collect(joining(", "));
    }

    public static final String UPDATE = """
		UPDATE member_tb
		SET member_type = :memberType,
			ceo_name = :ceoName,
			registration_number = :registrationNumber,
			phone = :phone,
			memo = :memo
		WHERE id = :id
	""";
}
