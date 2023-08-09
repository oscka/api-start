package com.osckorea.openmsa.starter.pagination.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * [ Pagination Repository ]
 * 페이지네이션 리포지토리 Generic 인터페이스 입니다.
 * 
 * @param <E> Entity Class Object Type
 * @param <ET> Entity Primary Key Field Memeber Type
 */
@NoRepositoryBean
public interface PaginationRepository<E, ET> extends PagingAndSortingRepository<E, ET>{

}
