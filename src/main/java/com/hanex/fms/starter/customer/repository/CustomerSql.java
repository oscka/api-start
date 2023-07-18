package com.hanex.fms.starter.customer.repository;

public class CustomerSql {

    public final static String SELECT_BY_LOGIN_ID = """
            SELECT 
              b.login_id
                , b.user_id
                , b.user_role
            FROM base_user B
            WHERE B.login_id = :loginId
        """;
}
