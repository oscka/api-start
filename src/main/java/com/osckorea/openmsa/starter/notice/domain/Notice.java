package com.osckorea.openmsa.starter.notice.domain;

import java.sql.Timestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import com.osckorea.openmsa.starter.notice.dto.NoticeDto;
import com.osckorea.openmsa.starter.pagination.domain.GenericAbstractEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "공지사항 게시글 엔티티")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tb_notice")
public class Notice extends GenericAbstractEntity<NoticeDto>{
    @Schema(description = "공지사항 게시글 번호", nullable = false ,example = "1")
    @Id
    @Column("notice_no")
    private Integer noticeIndex;

    @Schema(description = "공지사항 게시글 제목", nullable = false, example = "Test1")
    @Column("subject")
    private String noticeSubject;

    @Schema(description = "공지사항 게시글 내용", nullable = false, example = "CONTENT0001")
    @Column("content")
    private String noticeContents;

    @Schema(description = "공지사항 작성자 IP", nullable = false, example = "127.0.0.1")
    @Column("ip_addr")
    private String ipAddress;

    @Schema(description = "공지사항 캐쉬 Hit 수", nullable = false, example = "0")
    @Column("hit_cnt")
    private Integer hitCount;

    @Schema(description = "공지사항 활성화 여부", nullable = false, example = "Y")
    @Column("use_yn")
    private String isActive;

    @Schema(description = "공지사항 게시글 작성자", nullable = false, example = "admin")
    @Column("first_reg_id")
    private String writeId;

    @Schema(description = "공지사항 게시글 작성일", nullable = false, example = "2023-08-09 13:53:01.99")
    @Column("first_reg_dt")
    private Timestamp writeDate;

    @Schema(description = "공지사항 게시글 수정자", example = "admin")
    @Column("last_change_id")
    private String modifyId;

    @Schema(description = "공지사항 게시글 수정일", example = "2023-08-09 13:53:02.99")
    @Column("last_change_dt")
    private Timestamp modifyDate;

    @Override
    public NoticeDto toDto() {
        return NoticeDto.builder()
                        .index(this.noticeIndex)
                        .subject(this.noticeSubject)
                        .contents(this.noticeContents)
                        .ipAddr(this.ipAddress)
                        .hitCnt(this.hitCount)
                        .writeId(this.writeId)
                        .writeDate(this.writeDate)
                        .modifyId(this.modifyId)
                        .modifyDate(this.modifyDate)
                        .build();
    }
}
