package com.osckorea.openmsa.starter.pagination.controller;

import java.util.List;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import com.osckorea.openmsa.starter.pagination.annotation.PageableSchema;
import com.osckorea.openmsa.starter.pagination.service.PaginationAbstractService;
import io.swagger.v3.oas.annotations.Operation;

/**
 * [ PaginationController]
 * 페이지네이션 컨트롤러 공통기능 추상화 클래스입니다.
 * 
 * 재정의가 필요한 기능은 메소드 오버라이딩(@Override)을 통해 구현이 필요합니다.
 * 
 * @param <E> Entity Class Object Type
 * @param <ET> Entity Primary Key Field Member Type
 * @param <D> DTO Class Object Type
 */
public abstract class PaginationAbstractController<E, ET, D> {
    protected final PaginationAbstractService<E, ET, D> paginationService;

    public PaginationAbstractController(PaginationAbstractService<E, ET ,D> paginationService) {
        this.paginationService = paginationService;
    }
    
    @PageableSchema
    @Operation(summary = "페이징 조회",description = "페이지네이션 된 데이터 및 페이지 요청 정보를 조회합니다.")
    @GetMapping("/pagination")
    public Page<D> findByPageableWithPageInfo(
        @ParameterObject @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        return this.paginationService.findByPageableWithPageInfo(pageable);
    }

    @PageableSchema
    @GetMapping("/pagination/contents")
    @Operation(summary = "페이징 데이터만 조회", description = "페이지네이션 된 데이터만 조회합니다.")
    public List<D> findByPageable(@ParameterObject @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return this.paginationService.findByPageable(pageable);
    }
}
