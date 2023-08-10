package com.osckorea.openmsa.starter.pagination.utils;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * [ GenericConverter ]
 * 
 * Functional Interface를 활용한 Generic Type 변환 인터페이스 입니다.
 * 
 * @param <I> Input Class Object Type
 * @param <O> Output Class Object Type
 */
public interface GenericConverter<I, O> extends Function<I, O> {
    default O convert(final I object) {
        if (object != null) {
            return this.apply(object);
        }

        return null;
    }

    default List<O> convert(final Collection<I> objectList) {
        if(objectList != null) {
            return objectList.stream().map(this::apply).toList();
        }

        return null;
    }

    default Page<O> convert(final Page<I> objectPageInfo) {
        if(objectPageInfo != null) {
            return new PageImpl<O>(
                objectPageInfo.getContent().stream().map(this::apply).toList(),
                objectPageInfo.getPageable(),
                objectPageInfo.getTotalElements()
            ); 
        }

        return null;
    }
}