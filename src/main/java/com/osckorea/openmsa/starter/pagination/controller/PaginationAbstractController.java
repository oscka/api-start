package com.osckorea.openmsa.starter.pagination.controller;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.osckorea.openmsa.starter.pagination.service.PaginationAbstractService;

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

    @GetMapping(params = {"page", "size", "sortBy"})
    public List<D> findallByPagingAndSorting(
        @RequestParam(value = "page") final Integer page,
        @RequestParam(value = "size") final Integer pageSize,
        @RequestParam(value = "sortBy") final String sortBy,
        @RequestParam(value = "sortOrder") final String sortOrder
    ) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Direction.fromString(sortOrder), sortBy);

        return this.paginationService.findAllEntities(pageRequest);
    }

    @GetMapping(params = {"page", "size"})
    public List<D> findAllByPaging(
        @RequestParam(value = "page") final Integer page,
        @RequestParam(value = "size") final Integer pageSize
    ) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);

        return this.paginationService.findAllEntities(pageRequest);
    }

    @GetMapping(params = {"sortBy"})
    public List<D> findAllBySorting(
        @RequestParam(value = "sortBy") final String sortBy,
        @RequestParam(value = "sortOrder") final String sortOrder
    ) {
        Sort sort = Sort.by(Direction.fromString(sortOrder), sortBy);

        return this.paginationService.findAllEntities(sort);
    }
}
