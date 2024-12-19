package com.osckorea.openmsa.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.osckorea.openmsa.global.config.Constants;
import com.osckorea.openmsa.global.payload.RequestHeaderPayload;
import com.osckorea.openmsa.global.util.ThreadUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("=======================[HttpInterceptor >> PRE]===============================");
        log.info("Request URI ===> " + request.getRequestURI());

        // HttpRequest Header 정보 세팅
        RequestHeaderPayload requestHeaderPayload = new RequestHeaderPayload();
        requestHeaderPayload.setUserId(request.getHeader(Constants.HeaderKeys.USER_ID));
        requestHeaderPayload.setUserRole(request.getHeader(Constants.HeaderKeys.USER_ROLE));
        requestHeaderPayload.setDeptId(request.getHeader(Constants.HeaderKeys.DEPT_ID));
        requestHeaderPayload.setSubjectId(request.getHeader(Constants.HeaderKeys.SUBJECT_ID));
        requestHeaderPayload.setServiceId(request.getHeader(Constants.HeaderKeys.SERVICE_ID));
        requestHeaderPayload.setMenuId(request.getHeader(Constants.HeaderKeys.MENU_ID));
        requestHeaderPayload.setViewId(request.getHeader(Constants.HeaderKeys.VIEW_ID));
        requestHeaderPayload.setTrId(request.getHeader(Constants.HeaderKeys.TR_ID));
        ThreadUtil.threadLocalRequestHeaderPayload.set(requestHeaderPayload);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public  void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("=======================[HttpInterceptor >> POST]===============================");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("=======================[HttpInterceptor >> COMPLETION]===============================");
        ThreadUtil.threadLocalRequestHeaderPayload.remove();
        
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
