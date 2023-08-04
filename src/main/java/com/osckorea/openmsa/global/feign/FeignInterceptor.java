package com.osckorea.openmsa.global.feign;

import javax.servlet.http.HttpServletRequest;

import com.osckorea.openmsa.global.config.Constants;
import com.osckorea.openmsa.global.exception.Exception404;
import com.osckorea.openmsa.global.payload.RequestHeaderPayload;
import com.osckorea.openmsa.global.util.HttpUtil;
import com.osckorea.openmsa.global.util.ThreadUtil;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignInterceptor implements RequestInterceptor {


    @Override
    public void apply(RequestTemplate requestTemplate) {

        HttpServletRequest request = HttpUtil.getHttpServletRequest();

        try {
            // HttpRequest Header 정보 세팅
            // HttpServletRequest Null 일 경우 (ex : kafka 비동기통신)
            if (request == null) {
                RequestHeaderPayload requestHeaderPayload = ThreadUtil.threadLocalRequestHeaderPayload.get();
                requestTemplate.header(Constants.HeaderKeys.USER_ID, requestHeaderPayload.getUserId());
                requestTemplate.header(Constants.HeaderKeys.USER_ROLE, requestHeaderPayload.getUserRole());
                requestTemplate.header(Constants.HeaderKeys.DEPT_ID, requestHeaderPayload.getDeptId());
                requestTemplate.header(Constants.HeaderKeys.SUBJECT_ID, requestHeaderPayload.getSubjectId());
                requestTemplate.header(Constants.HeaderKeys.SERVICE_ID, requestHeaderPayload.getServiceId());
                requestTemplate.header(Constants.HeaderKeys.MENU_ID, requestHeaderPayload.getMenuId());
                requestTemplate.header(Constants.HeaderKeys.VIEW_ID, requestHeaderPayload.getViewId());
                requestTemplate.header(Constants.HeaderKeys.TR_ID, requestHeaderPayload.getTrId());
            } else {
                requestTemplate.header(Constants.HeaderKeys.USER_ID, request.getHeader(Constants.HeaderKeys.USER_ID));
                requestTemplate.header(Constants.HeaderKeys.USER_ROLE, request.getHeader(Constants.HeaderKeys.USER_ROLE));
                requestTemplate.header(Constants.HeaderKeys.DEPT_ID, request.getHeader(Constants.HeaderKeys.DEPT_ID));
                requestTemplate.header(Constants.HeaderKeys.SUBJECT_ID, request.getHeader(Constants.HeaderKeys.SUBJECT_ID));
                requestTemplate.header(Constants.HeaderKeys.SERVICE_ID, request.getHeader(Constants.HeaderKeys.SERVICE_ID));
                requestTemplate.header(Constants.HeaderKeys.MENU_ID, request.getHeader(Constants.HeaderKeys.MENU_ID));
                requestTemplate.header(Constants.HeaderKeys.VIEW_ID, request.getHeader(Constants.HeaderKeys.VIEW_ID));
                requestTemplate.header(Constants.HeaderKeys.TR_ID, request.getHeader(Constants.HeaderKeys.TR_ID));
            }

        } catch (Exception404 e) {
            log.error(e.getMessage(), e);
            throw new Exception404(e.getMessage());
        }

    }
}
