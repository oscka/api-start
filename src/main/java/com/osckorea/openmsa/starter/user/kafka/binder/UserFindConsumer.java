package com.osckorea.openmsa.starter.user.kafka.binder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import com.osckorea.openmsa.global.util.ThreadUtil;
import com.osckorea.openmsa.starter.user.kafka.event.UserFindEventWrapper;
import com.osckorea.openmsa.starter.user.kafka.service.UserFindService;

import java.util.function.Consumer;

/**
 * kafka consumer sample
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class UserFindConsumer {

    private Jackson2JsonObjectMapper mapper = new Jackson2JsonObjectMapper();
    private final UserFindService userFindService;

    /**
     * findUser Consumer
     * 
     * @return
     */
    @Bean
    Consumer<Message<String>> kafkaFunctionFindUser() {
        return ((message) -> {
            try {
                log.info("[Kafka-Consumer] >> UserKafkaConsumer.findUser >> Header : {}", message.getHeaders());
                log.info("[Kafka-Consumer] >> UserKafkaConsumer.findUser >> Payload : {}", message.getPayload());
                log.info("[Kafka-Consumer] >> UserKafkaConsumer.findUser >> threadLocalRequestHeaderPayload : {}",ThreadUtil.threadLocalRequestHeaderPayload.get().toString());

                // 로직 수행
                UserFindEventWrapper userFindEventWrapper = mapper.fromJson(message.getPayload(), UserFindEventWrapper.class);
                userFindService.login(userFindEventWrapper.getUserFindEvent());

                // 로직 수행후 offset 커밋
                Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
                if (acknowledgment != null) {
                    log.info("[Kafka-Consumer] >> UserKafkaConsumer.kafkaFunctionFindUser >> Acknowledgment provided!! ");
                    acknowledgment.acknowledge();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

}
