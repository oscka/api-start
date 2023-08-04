package com.osckorea.openmsa.global.feign;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osckorea.openmsa.global.exception.Exception404;
import com.osckorea.openmsa.global.payload.RequestHeaderPayload;
import com.osckorea.openmsa.global.util.HttpUtil;
import com.osckorea.openmsa.global.util.ThreadUtil;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignInterceptor implements RequestInterceptor {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void apply(RequestTemplate requestTemplate) {

        log.info("======================[FeignInterceptor >> START]===================");

        // if(requestTemplate.feignTarget().name().toString().equalsIgnoreCase("monitoring"))
        // return;

        HttpServletRequest request = HttpUtil.getHttpServletRequest();
        RequestHeaderPayload requestHeaderPayload = new RequestHeaderPayload();

        try {
            Enumeration eHeader = request.getHeaderNames();
            while (eHeader.hasMoreElements()) {
                String headerKey = (String) eHeader.nextElement();
                String headerValue = request.getHeader(headerKey);
                log.info("key : " + headerKey + " || value : " + headerValue);

            }

            // HttpRequest Header 정보 세팅
            // HttpServletRequest Null 일 경우 (ex : kafka 비동기통신)
            if (request == null) {
                requestHeaderPayload = ThreadUtil.threadLocalRequestHeaderPayload.get();
                requestTemplate.header("x-user-id", requestHeaderPayload.getUserId());
                requestTemplate.header("x-user-role", requestHeaderPayload.getUserRole());
                requestTemplate.header("x-dept-id", requestHeaderPayload.getDeptId());
                requestTemplate.header("x-subject-id", requestHeaderPayload.getSubjectId());
                requestTemplate.header("x-service-id", requestHeaderPayload.getServiceId());
                requestTemplate.header("x-menu-id", requestHeaderPayload.getMenuId());
                requestTemplate.header("x-view-id", requestHeaderPayload.getViewId());
                requestTemplate.header("x-tr-id", requestHeaderPayload.getTrId());
            } else {
                requestTemplate.header("x-user-id", request.getHeader("x-user-id"));
                requestTemplate.header("x-user-role", request.getHeader("x-user-role"));
                requestTemplate.header("x-dept-id", request.getHeader("x-dept-id"));
                requestTemplate.header("x-subject-id", request.getHeader("x-subject-id"));
                requestTemplate.header("x-service-id", request.getHeader("x-service-id"));
                requestTemplate.header("x-menu-id", request.getHeader("x-menu-id"));
                requestTemplate.header("x-view-id", request.getHeader("x-view-id"));
                requestTemplate.header("x-tr-id", request.getHeader("x-tr-id"));
            }

        } catch (Exception404 e) {
            log.error(e.getMessage(), e);
            throw new Exception404(e.getMessage());
        }

        log.info("======================[FeignInterceptor >> END]===================");
    }
}
