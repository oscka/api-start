package com.osckorea.openmsa.starter.notice.repository.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.osckorea.openmsa.starter.notice.domain.Notice;

@Component
public class NoticeCustomRowMapper implements RowMapper<Notice>{
    @Override
    public Notice mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Notice.builder()
                        .noticeIndex(rs.getInt("notic_no"))
                        .noticeSubject(rs.getString("subject"))
                        .noticeContents(rs.getString("content"))
                        .ipAddress(rs.getString("ip_addr"))
                        .hitCount(rs.getInt("hit_cnt"))
                        .isActive(rs.getString("use_yn"))
                        .writeId(rs.getString("first_reg_id"))
                        .writeDate(rs.getTimestamp("first_reg_dt"))
                        .modifyId(rs.getString("last_change_id"))
                        .modifyDate(rs.getTimestamp("last_change_dt"))
                        .build();
    }
}
