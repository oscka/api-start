package com.osckorea.openmsa.global.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import com.osckorea.openmsa.global.wrapper.CachingRequestWrapper;
import com.osckorea.openmsa.global.wrapper.CachingResponseWrapper;

public class HttpUtil {

    public static HttpServletRequest getHttpServletRequest() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if( attr == null) {
			return null;
		}
		return attr.getRequest();
	}

    public static String getRequestBody(HttpServletRequest request) {
        String reqBody = null;
        CachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, CachingRequestWrapper.class);
        if (wrapper != null) {
            if (wrapper.getContentLength() > 0) {
                InputStream inputStream = wrapper.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                reqBody = bufferedReader.lines().collect(Collectors.joining());
            }
        }
        return reqBody;
    }

    
    public static String getResponseBody(HttpServletResponse response)  {
        String resBody = null;
        CachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, CachingResponseWrapper.class);
        if (wrapper != null) {                
            InputStream inputStream = wrapper.getContentInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            resBody = bufferedReader.lines().collect(Collectors.joining());
        }
        return resBody;
    }

    public static Map getHeaders(HttpServletRequest request) {
        Map headerMap = new HashMap<>();
        Enumeration headerArray = request.getHeaderNames();
        while (headerArray.hasMoreElements()) {
            String headerName = (String) headerArray.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }

    public static String getParameters(final HttpServletRequest request) {
        final StringBuffer posted = new StringBuffer();
        final Enumeration<?> e = request.getParameterNames();
        if (e != null)
            posted.append("?");
        while (e != null && e.hasMoreElements()) {
            if (posted.length() > 1)
                posted.append("&");
            final String curr = (String) e.nextElement();
            posted.append(curr)
                    .append("=");
            if (curr.contains("password") || curr.contains("answer") || curr.contains("pwd")) {
                posted.append("*****");
            } else {
                posted.append(request.getParameter(curr));
            }
        }

        final String ip = request.getHeader("X-FORWARDED-FOR");
        final String ipAddr = (ip == null) ? getRemoteAddress(request) : ip;
        if (!StringUtils.isEmpty(ipAddr))
            posted.append("&_psip=").append(ipAddr);
        return posted.toString();
    }

    public static String getRemoteAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        // proxy 환경일 경우
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        // 웹로직 서버일 경우
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip != null && ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }

        return ip;
    }
}

