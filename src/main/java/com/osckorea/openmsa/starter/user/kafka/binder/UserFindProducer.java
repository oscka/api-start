package com.osckorea.openmsa.starter.user.kafka.binder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

import com.osckorea.openmsa.global.exception.Exception500;
import com.osckorea.openmsa.starter.user.kafka.event.UserFindEvent;
import com.osckorea.openmsa.starter.user.kafka.event.UserFindEventWrapper;

import java.util.UUID;

// @EnableBinding(value= {StreamProcessor.class})
@Slf4j
@RequiredArgsConstructor
@Component
public class UserFindProducer {

    private final StreamBridge streamBridge;

    public void findUser(UserFindEvent userFindEvent){
        try {

            // header 정보 세팅
            UserFindEventWrapper userFindEventWrapper = UserFindEventWrapper.setRequestHeader(userFindEvent);

            // message 전송
            boolean result = streamBridge.send("kafkaFunctionFindUser-out-0", MessageBuilder
                .withPayload(userFindEventWrapper)
                .setHeader(KafkaHeaders.KEY, UUID.randomUUID().toString())
                .build());
            
            log.info("[Kafka-Producer] --- UserKafkaProducer.findUser --- findUser-out-0 >>> result : {}", result);

        } catch (Exception e){
            e.printStackTrace();
            throw new Exception500(e.getMessage());
        }
    }
}
