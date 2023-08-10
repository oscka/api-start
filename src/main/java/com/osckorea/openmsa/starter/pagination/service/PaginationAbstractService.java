package com.osckorea.openmsa.starter.pagination.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.osckorea.openmsa.starter.pagination.repository.PaginationRepository;
import com.osckorea.openmsa.starter.pagination.utils.GenericConverter;

/**
 * [ PaginationService ]
 * 페이지네이션 서비스 레이어 공통기능 추상화 클래스입니다.
 * 
 * 재정의가 필요한 기능은 메소드 오버라이딩(@Override)를 통해 구현이 필요합니다.
 * Functional Interface를 활용한 GenericConverter를 구현하여 사용해야합니다.
 * 
 * @param <E> Entity Class Object Type
 * @param <ET> Entity Primary Key Field Member Type
 * @param <D> DTO Class Object Type
 */
public abstract class PaginationAbstractService<E, ET, D> implements GenericConverter<E, D> {
    protected final PaginationRepository<E, ET> paginationRepository;

    public PaginationAbstractService(PaginationRepository<E, ET> paginationRepository) {
        this.paginationRepository = paginationRepository;
    }

    public Page<D> findByPageableWithPageInfo(Pageable pageRequest) {
        Page<E> result = this.paginationRepository.findAll(pageRequest);

        if(result.isEmpty()) {
            return new PageImpl<D>(
                new ArrayList<D>()
            );
        }

        return convert(result);
    }

    public List<D> findByPageable(Pageable pageRequest) {
        Page<E> result = this.paginationRepository.findAll(pageRequest); 

        if(result.isEmpty()) {
            return new ArrayList<D>();
        }

        return convert(result.getContent());
    }

}
