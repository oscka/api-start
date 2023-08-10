package com.osckorea.openmsa.starter.notice.repository;

import com.osckorea.openmsa.starter.notice.domain.Notice;

public interface NoticeCustomRepository {
    Integer insertNotice(Notice saveEntity);

    Integer updateNotice(Notice updateEntity);

    Integer deleteNoitce(Integer index);
}
