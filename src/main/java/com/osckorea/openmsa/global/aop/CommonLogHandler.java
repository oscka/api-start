package com.osckorea.openmsa.global.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class CommonLogHandler {

    @Pointcut("@annotation(com.osckorea.openmsa.global.annotation.CustomLog)")
    public void customLog(){}

    @Pointcut("@annotation(com.osckorea.openmsa.global.annotation.CustomErrorLog)")
    public void customErrorLog(){}

    @AfterReturning("customLog()")
    public void logAdvice(JoinPoint jp) throws Exception {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        log.debug("[CommonLogHandler] : [{}]" , method.getName());
    }

    @Before("customErrorLog()")
    public void errorLogAdvice(JoinPoint jp) throws Exception {
        Object[] args = jp.getArgs();

        for (Object arg : args) {
            if(arg instanceof Exception){
                Exception e = (Exception) arg;
                log.error("[CommonLogHandler] ERROR > {}",e.getMessage());
                //log.error(Arrays.toString(e.getStackTrace()));
            }
        }
    }
}
