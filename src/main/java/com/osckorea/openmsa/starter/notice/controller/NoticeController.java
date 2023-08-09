package com.osckorea.openmsa.starter.notice.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.osckorea.openmsa.starter.notice.domain.Notice;
import com.osckorea.openmsa.starter.notice.dto.NoticeDto;
import com.osckorea.openmsa.starter.notice.service.NoticeService;
import com.osckorea.openmsa.starter.pagination.controller.PaginationAbstractController;
import com.osckorea.openmsa.starter.pagination.service.PaginationAbstractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "공지사항 게시글 RESTful API", description = "공지사항 게시글 페이지네이션 및 CRUD 관련 API")
@RestController
@RequestMapping("v1/notices")
public class NoticeController extends PaginationAbstractController<Notice, Integer, NoticeDto>{
    private final NoticeService noticeService;

    public NoticeController(
        PaginationAbstractService<Notice, Integer, NoticeDto> paginationService,
        NoticeService noticeService
    ) {
        super(paginationService);

        this.noticeService = noticeService;
    }

    @Operation(summary = "공지사항 게시글 번호로 조회", description = "특정 번호로 단건 조회합니다.")
    @GetMapping("/:{index}")
    public NoticeDto findByNoticeNumber(
        @PathVariable("index") Integer index
    ) {
        return this.noticeService.findByNoticeNumber(index);
    }

    @Operation(summary = "공지사항 게시글 제목으로 조회", description = "특정 제목으로 단건 조회합니다.")
    @GetMapping("/:{subject}")
    public NoticeDto findByNoticeSubject(
        @PathVariable("subject") String subject
    ) {
        return NoticeDto.builder().build();
    }

    @Operation(summary = "공지사항 게시글 생성", description = "새로운 공지사항 게시글을 등록합니다.")
    @PostMapping
    public String createNotice(
        @RequestBody NoticeDto dto
    ) {
        return "Effected Row : " + this.noticeService.saveNotice(dto);
    }

    @Operation(summary = "공지사항 게시글 수정", description = "특정 게시글을 수정합니다.")
    @PutMapping("/:{index}")
    public String editNotice(
        @PathVariable("index") Integer index,
        @RequestBody NoticeDto dto
    ) {
        return "Effected Row : " + this.noticeService.updateNotice(index, dto);
    }

    @Operation(summary = "공지사항 게시글 삭제", description = "특정 게시글을 삭제합니다.")
    @DeleteMapping("/:{index}")
    public String removeNotice(
        @PathVariable("index") Integer index
    ) {
        return "Effected Row : " + this.noticeService.deleteNotice(index);
    }
}
