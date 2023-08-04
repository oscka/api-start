package com.osckorea.openmsa.global.aop;

import java.io.IOException;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osckorea.openmsa.global.exception.Exception404;
import com.osckorea.openmsa.global.payload.RequestHeaderPayloadWrapper;
import com.osckorea.openmsa.global.util.ThreadUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class kafkaHandler {
    
    Jackson2JsonObjectMapper mapper = new Jackson2JsonObjectMapper();
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Pointcut : Kafka Consumer(Function Interface) accept 호출시 실행
     */
    @Pointcut("execution(* com.osckorea.openmsa..kafka..accept(..))")
    // @Pointcut("bean(kafkaFunction*) && execution(* com.osckorea.openmsa..kafka..accept(..))")
    // @Pointcut("@annotation(com.osckorea.openmsa.global.annotation.CustomKafkaConsumer)")
    public void kafkaConsumer() {}

    /**
     * Around : kafkaConsumer
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("kafkaConsumer()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        // 1. Method 확인 : Consumer(Functional Interface) accept 호출시
        Object returnValue = null;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        if(method.toString().matches("(.*)java.util.function.Consumer.accept(.*)")){

            // 2. Args 확인 : kafka Event Message 세팅
            Object[] objects = joinPoint.getArgs();
            Message<String> msg = (Message<String>) objects[0];

            // 3. Before : MDC 세팅, ThreadLocal 세팅
            beforeTransaction(msg);

            try {
                returnValue = joinPoint.proceed();
            } catch (Throwable e) {
                afterTransaction(msg);
                return returnValue;
            }
        
            // 4. After : MDC clear, ThreadLocal remove
            afterTransaction(msg);
        }
        else {
            try {
                returnValue = joinPoint.proceed();
            } catch (RuntimeException e) {
                throw new Exception404(e.getMessage());
            }
        }

        return returnValue;
    }

    /**
     * beforeTransaction
     * @param msg
     * @throws IOException
     */
    private void beforeTransaction(Message<String> msg) throws IOException {

        /// 1. Header 정보
        RequestHeaderPayloadWrapper requestHeaderPayloadWrapper = mapper.fromJson(msg.getPayload(), RequestHeaderPayloadWrapper.class);

        // 2. MDC 세팅
        MDC.put("trace_id", requestHeaderPayloadWrapper.getRequestHeaderPayload().getTrId());

        // 3. ThreadLocal 세팅
        ThreadUtil.threadLocalRequestHeaderPayload.set(requestHeaderPayloadWrapper.getRequestHeaderPayload());

    }

    /**
     * afterTransaction
     * @param msg
     * @throws IOException
     */
    private void afterTransaction(Message<String> msg) throws IOException {

        // 1. ThreadLocal remove
        ThreadUtil.threadLocalRequestHeaderPayload.remove();

        // 2. MDC clear
        MDC.clear();
    }
}
