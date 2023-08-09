package com.osckorea.openmsa.starter.notice.repository.sql;

public class NoticeSql {
    public static final String SELECT_ALL = """
            SELECT *
            FROM tb_notice n
            """;

    public static final String INSERT_NOTICE = """
            INSERT INTO tb_notice(
                notic_no,
                subject,
                content,
                ip_addr,
                hit_cnt,
                use_yn,
                first_reg_id,
                first_reg_dt
            ) VALUES(
                nextval('tb_noti_no_seq'),
                :noticeSubject,
                :noticeContents,
                :ipAddress,
                :hitCount,
                :isActive,
                :writeId,
                :writeDate
            )
            """;

    public static final String UPDATE_BY_INDEX = """
            UPDATE tb_notice
            SET subject = :noticeSubject,
                content = :noticeContents,
                ip_addr = :ipAddress,
                use_yn = :isActive,
                last_change_id = :modifyId,
                last_change_dt = :modifyDate
            WHERE notic_no = :noticeIndex
            """;
    
    public static final String DELETE_BY_INDEX = """
            DELETE
            FROM tb_notice
            WHERE notic_no = :noticeIndex
            """;

    public final String customQueryBuilder(String query, String keyWord, String customQuery) {
        StringBuilder queryString = new StringBuilder(query);

        queryString.append("\n");
        queryString.append(keyWord);

        queryString.append(" ");
        queryString.append(customQuery);

        return queryString.toString();
    }
}
