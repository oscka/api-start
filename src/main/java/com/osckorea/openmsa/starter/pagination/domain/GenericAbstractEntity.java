package com.osckorea.openmsa.starter.pagination.domain;

import java.io.Serializable;

/**
 * [ GenericEntity ]
 * Generic 엔티티 추상 클래스 입니다.
 * 
 * @param <D> DTO Class Object Type
 */
public abstract class GenericAbstractEntity<D> implements Serializable{
    public abstract D toDto();
}
