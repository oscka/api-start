package com.hanex.fms.starter.order.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.firstNonBlank;

public class OrderSql {

    public static String SELECT_BY_ID_WITH_CLIENT(Sort sort){
        return """
                SELECT 
                    o.order_id 
                    , o.member_id  
                    , o.order_code 
                    , o.company_order_code  
                    , o.receiver_name 
                    , o.phone 
                    , o.order_status 
                    , o.order_date 
                    , o.memo 
                    , o.channel_id 
                    , s.shipping_method_id 
                    , s.shipping_address 
                    , s.request_shipping_dt  
                    , s.shipping_message 
                    , p.sales_product_code 
                    , oi.sales_product_id 
                    , oi.quantity 
                FROM tb_order o 
                    LEFT OUTER JOIN tb_member m m.id = o.member_id  
                    LEFT OUTER JOIN tb_product p p.id = o.product_id  
                    LEFT OUTER JOIN tb_shipping s s.id = o.shipping_id  
                    LEFT OUTER JOIN order_item oi oi.order_id = o.id 
                """
                +
                whereAnd (
                        notEmpty(":clientId","client_id :clientId")
                )
                +
                " ORDER BY " + orderBy(sort)
                +
                " LIMIT :pageSize OFFSET :offset"
                ;
    }

    public static String COUNT_BY_ID_WITH_CLIENT(){
        return """
            SELECT
                    COUNT(*)
            FROM tb_order o
                LEFT OUTER JOIN tb_member m m.id = o.member_id
                LEFT OUTER JOIN tb_product p p.id = o.product_id
                LEFT OUTER JOIN tb_shipping s s.id = o.shipping_id
                LEFT OUTER JOIN order_item oi oi.order_id = o.id
                """
                +
                whereAnd (
                        notEmpty(":clientId","client_id :clientId")
                )
                ;
    }

    private static String notEmpty(String param, String condition) {
        return StringUtils.isEmpty(param)? null: condition;
    }

    private static String whereAnd(String ... conditions) {
        List<String> finalCond = Collections.singletonList(firstNonBlank(conditions));
        Assert.notEmpty(finalCond);
        return " WHERE " + String.join(finalCond + "\nAND");
    }


    private static String orderBy(Sort sort) {
        return sort.stream()
                .map(order -> order.getProperty() + " " + order.getDirection().name())
                .collect(joining(", "));
    }
}
