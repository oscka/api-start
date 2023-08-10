package com.osckorea.openmsa.starter.notice.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.osckorea.openmsa.starter.notice.domain.Notice;
import com.osckorea.openmsa.starter.pagination.dto.GenericAbstractDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "공지사항 게시글 DTO")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NoticeDto extends GenericAbstractDto<Notice, Integer>{
    @Schema(description = "공지사항 게시글 번호", nullable = false ,example = "1")
    private Integer index;

    @Schema(description = "공지사항 게시글 제목", nullable = false, example = "Test Subject")
    private String subject;

    @Schema(description = "공지사항 게시글 내용", nullable = false, example = "Test Content")
    private String contents;

    @Schema(description = "공지사항 작성자 IP", nullable = false, example = "127.0.0.1")
    private String ipAddr;

    @Schema(description = "공지사항 캐쉬 Hit 수", nullable = false, example = "0")
    @Builder.Default
    private Integer hitCnt = 0;

    @Schema(description = "공지사항 활성화 여부", nullable = false, example = "Y")
    @Builder.Default
    private String isActive = "Y";

    @Schema(description = "공지사항 게시글 작성자", nullable = false, example = "user1")
    private String writeId;
    
    @Schema(description = "공지사항 게시글 수정자", example = "admin")
    private String modifyId;
    
    @Schema(description = "공지사항 게시글 작성일", nullable = false, example = "2023-08-09 13:53:01.99")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Date writeDate;

    @Schema(description = "공지사항 게시글 수정일", example = "2023-08-09 13:53:02.99")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Date modifyDate;

    @Override
    public Notice toEntity() {
        return Notice.builder()
                        .noticeSubject(this.subject)
                        .noticeContents(this.contents)
                        .hitCount(this.hitCnt)
                        .ipAddress(this.ipAddr)
                        .isActive(this.isActive)
                        .writeId(this.writeId)
                        .writeDate(Timestamp.valueOf(LocalDateTime.now()))
                        .build();
    }

    @Override
    public Notice toEntity(Integer id) {
        return Notice.builder()
                        .noticeIndex(id)
                        .noticeSubject(this.subject)
                        .noticeContents(this.contents)
                        .hitCount(this.hitCnt)
                        .ipAddress(this.ipAddr)
                        .isActive(this.isActive)
                        .modifyId(this.modifyId)
                        .modifyDate(Timestamp.valueOf(LocalDateTime.now()))
                        .build();
    }
    
}
