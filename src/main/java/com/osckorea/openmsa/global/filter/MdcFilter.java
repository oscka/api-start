package com.osckorea.openmsa.global.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.osckorea.openmsa.global.config.Constants;

@Component
public class MdcFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        
        MDC.put("trace_id", request.getHeader(Constants.HeaderKeys.TR_ID));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        MDC.clear();
        Filter.super.destroy();
    }
}
