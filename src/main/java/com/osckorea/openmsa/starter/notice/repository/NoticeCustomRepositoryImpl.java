package com.osckorea.openmsa.starter.notice.repository;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import com.osckorea.openmsa.starter.notice.domain.Notice;
import com.osckorea.openmsa.starter.notice.repository.sql.NoticeSql;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NoticeCustomRepositoryImpl implements NoticeCustomRepository{
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    // private final NoticeCustomRowMapper customRowMapper;

    @Override
    public Integer insertNotice(Notice saveEntity) {
        SqlParameterSource namedObjectSqlParameterSource = new BeanPropertySqlParameterSource(saveEntity);

        return this.namedParameterJdbcTemplate.update(NoticeSql.INSERT_NOTICE, namedObjectSqlParameterSource);
    }

    @Override
    public Integer updateNotice(Notice updateEntity) {
        SqlParameterSource namedObjectSqlParameterSource = new BeanPropertySqlParameterSource(updateEntity);

        return this.namedParameterJdbcTemplate.update(NoticeSql.UPDATE_BY_INDEX, namedObjectSqlParameterSource);
    }

    @Override
    public Integer deleteNoitce(Integer index) {
        SqlParameterSource namedMapSqlParameter = new MapSqlParameterSource("noticeIndex", index);

        return this.namedParameterJdbcTemplate.update(NoticeSql.DELETE_BY_INDEX, namedMapSqlParameter);
    }

}
