package com.osckorea.openmsa.starter.notice.repository;

import com.osckorea.openmsa.starter.notice.domain.Notice;
import com.osckorea.openmsa.starter.pagination.repository.PaginationRepository;
import java.util.List;
import java.sql.Timestamp;

public interface NoticeRepository extends PaginationRepository<Notice, Integer>, NoticeCustomRepository{
    List<Notice> findByNoticeSubjectContaining(String noticeSubject);

    List<Notice> findByNoticeContentsContaining(String noticeContents);

    Notice findByNoticeSubject(String noticeSubject);

    List<Notice> findByHitCount(Integer hitCount);

    List<Notice> findByWriteDateBefore(Timestamp writeDate);

    List<Notice> findByWriteDateAfter(Timestamp writeDate);

    List<Notice> findByModifyDateBefore(Timestamp modifyDate);

    List<Notice> findByModifyDateAfter(Timestamp modifyDate);
}
