package com.osckorea.openmsa.starter.pagination.dto;

/**
 * [ GenericDto ]
 * Generic DTO 추상 클래스입니다.
 * 
 * @param <E> Entity Class Object Type
 * @param <ET> Entity Primary Key Field Memeber Type
 */
public abstract class GenericAbstractDto<E, ET> {
    public abstract E toEntity();

    public abstract E toEntity(ET id);
}
