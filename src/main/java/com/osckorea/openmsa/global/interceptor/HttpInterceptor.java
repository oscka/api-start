package com.osckorea.openmsa.global.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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
        requestHeaderPayload.setUserId(request.getHeader("x-user-id"));
        requestHeaderPayload.setUserRole(request.getHeader("x-user-role"));
        requestHeaderPayload.setDeptId(request.getHeader("x-dept-id"));
        requestHeaderPayload.setSubjectId(request.getHeader("x-subject-id"));
        requestHeaderPayload.setServiceId(request.getHeader("x-service-id"));
        requestHeaderPayload.setMenuId(request.getHeader("x-menu-id"));
        requestHeaderPayload.setViewId(request.getHeader("x-view-id"));
        requestHeaderPayload.setTrId(request.getHeader("x-tr-id"));
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
