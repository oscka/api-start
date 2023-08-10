package com.osckorea.openmsa.starter.pagination.config;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.osckorea.openmsa.global.exception.Exception400;

/**
 * Pageable 객체 내 각 멤버의 유효성을 검증합니다.
 */
@Component
public class PageableVerificationArgumentResolver extends PageableHandlerMethodArgumentResolver{

    public PageableVerificationArgumentResolver(SortArgumentResolver sortArgumentResolver) {
        super(sortArgumentResolver);
    }
    
    @Override
    public Pageable resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, 
        WebDataBinderFactory binderFactory
    ) {
        if(webRequest.getParameter("sort") == null ? true : false) {
            throw new Exception400("sort is null", "조회에 필요한 기준이 없습니다.");
        }

        final String pageText = webRequest.getParameter("page");
    
        final String sizeText = webRequest.getParameter("size");

        // 예외처리
        if(pageText != null) {
            if(!pageText.chars().allMatch(Character::isDigit)) {
                throw new Exception400("page="+pageText, "숫자를 입력하세요.");
            }
            if(Integer.parseInt(pageText) < 1) {
                throw new Exception400("page="+pageText, "페이지 번호는 최소한 1 이상입니다.");
            }
        }
        
        if(sizeText != null) {
            if(!sizeText.chars().allMatch(Character::isDigit)) {
                throw new Exception400("size="+sizeText, "숫자를 입력하세요.");
            }
            if(Integer.parseInt(sizeText) < 1) {
                throw new Exception400("size="+sizeText, "최소한 1개 이상을 조회하세요.");
            }
            int maxSize = this.getMaxPageSize();
            if(Integer.parseInt(sizeText) > maxSize) {
                throw new Exception400("size="+sizeText, "최대 조회가능한 페이지 크기는 " + maxSize + "입니다.");
            }
        }
        //

        return super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
    };
}
