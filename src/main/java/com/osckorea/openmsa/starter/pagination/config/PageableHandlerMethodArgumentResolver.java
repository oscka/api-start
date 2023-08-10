package com.osckorea.openmsa.starter.pagination.config;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.data.web.PageableHandlerMethodArgumentResolverSupport;
import org.springframework.data.web.SortArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PageableHandlerMethodArgumentResolver extends PageableHandlerMethodArgumentResolverSupport implements PageableArgumentResolver{
    // Dependency Injection
    private final SortArgumentResolver sortArgumentResolver;
    
    public PageableHandlerMethodArgumentResolver(
        SortArgumentResolver sortArgumentResolver
    ) {
        this.sortArgumentResolver = sortArgumentResolver;

        // Pageable 인터페이스 설정 적용
        this.configurePageableResolver();
    }
    //

    /**
     * Pageable 설정
     */
    private void configurePageableResolver() {
        // 페이지 번호가 1번부터 시작할 수 있습니다.
        setOneIndexedParameters(true);
        // 최대 조회가능한 페이지의 크기를 제한합니다. 현재 크기 : 30
        setMaxPageSize(30);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Pageable.class.equals(parameter.getParameterType());
    }
    
    @Override
    public Pageable resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, 
        WebDataBinderFactory binderFactory
    ) {
        String page = webRequest.getParameter(getPageParameterName());
    
        String pageSize = webRequest.getParameter(getSizeParameterName());
    
        // Sort 객체 생성
        Sort sort = sortArgumentResolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        // Pageable 객체 생성
        Pageable pageable = getPageable(parameter, page, pageSize);

        // 정렬 옵션 값이 존재한다면, 페이징 및 정렬이 가능한 PageRequest를 반환합니다.
        if(sort.isSorted()) {
            return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }
    
        return pageable;
    };
}
