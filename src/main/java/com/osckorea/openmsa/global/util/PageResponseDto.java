package com.osckorea.openmsa.global.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

/**
 * Page (org.springframework.data.domain.Page) 데이터를 반환하는 ResponseDto
 */
@Getter
public class PageResponseDto {

    private Object data;

    private PageInfo pageInfo;

    private PageResponseDto(Object data, Page page) {
        this.data = data;
        this.pageInfo =
                new PageInfo(
                        page.getNumber()+1,
                        page.getSize(),
                        (int) page.getTotalElements(),
                        page.getTotalPages()
                );
    }
    public static PageResponseDto of(Object data, Page page) {
        return new PageResponseDto(data, page);
    }

    @AllArgsConstructor
    @Getter
    private static class PageInfo {
        private int page;
        private int size;
        private int totalElements;
        private int totalPages;
    }
}
