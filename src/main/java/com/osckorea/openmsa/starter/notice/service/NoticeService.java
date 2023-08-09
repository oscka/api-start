package com.osckorea.openmsa.starter.notice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.osckorea.openmsa.starter.notice.domain.Notice;
import com.osckorea.openmsa.starter.notice.dto.NoticeDto;
import com.osckorea.openmsa.starter.notice.repository.NoticeRepository;
import com.osckorea.openmsa.starter.pagination.repository.PaginationRepository;
import com.osckorea.openmsa.starter.pagination.service.PaginationAbstractService;

@Service
public class NoticeService extends PaginationAbstractService<Notice, Integer, NoticeDto>{
    private final NoticeRepository noticeRepository;

    public NoticeService(
        PaginationRepository<Notice, Integer> paginationRepository,
        NoticeRepository noticeRepository
    ) {
        super(paginationRepository);

        this.noticeRepository = noticeRepository;
    }

    public NoticeDto findByNoticeNumber(Integer index) {
        return this.noticeRepository.findById(index).orElse(new Notice()).toDto();
    }

    public NoticeDto findByNoticeSubject(String subject) {
        return this.noticeRepository.findByNoticeSubject(subject).toDto();
    }

    @Transactional
    public Integer saveNotice(NoticeDto dto) {
        return this.noticeRepository.insertNotice(dto.toEntity());
    }

    @Transactional
    public Integer updateNotice(Integer index, NoticeDto dto) {
        return this.noticeRepository.updateNotice(dto.toEntity(index));
    }

    @Transactional
    public Integer deleteNotice(Integer index) {
        return this.noticeRepository.deleteNoitce(index);
    }

    @Override
    public NoticeDto apply(Notice entity) {
        return entity.toDto();
    }
}
