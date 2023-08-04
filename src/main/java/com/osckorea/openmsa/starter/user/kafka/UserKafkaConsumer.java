package com.osckorea.openmsa.starter.user.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import com.osckorea.openmsa.global.util.ThreadUtil;
import com.osckorea.openmsa.starter.user.dto.UserDto;
import com.osckorea.openmsa.starter.user.feign.AuthFeignClient;

import java.util.function.Consumer;

/**
 * kafka consumer sample
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class UserKafkaConsumer {

    private final AuthFeignClient authFeignClient;

    /**
     * findUser Consumer
     * 
     * @return
     */
    @Bean
    Consumer<Message<String>> findUser() {
        return ((input) -> {
            try {
                log.info("[Kafka-Consumer] >> UserKafkaConsumer.findUser >> Header : {}", input.getHeaders());
                log.info("[Kafka-Consumer] >> UserKafkaConsumer.findUser >> Payload : {}", input.getPayload());
                log.info("[Kafka-Consumer] >> UserKafkaConsumer.findUser >> threadLocalRequestHeaderPayload : {}",ThreadUtil.threadLocalRequestHeaderPayload.get().toString());

                UserDto.LoginRequest request = new UserDto.LoginRequest("admin", "test1234");
                authFeignClient.getToken(request);

                // 로직 수행후 offset 커밋
                Acknowledgment acknowledgment = input.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT,
                        Acknowledgment.class);
                if (acknowledgment != null) {
                    log.info("[Kafka-Consumer] >> UserKafkaConsumer.findUser >> Acknowledgment provided!! ");
                    acknowledgment.acknowledge();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    // 방법 1) StreamListener
    // @StreamListener(StreamProcessor.INPUT)
    // public void productChanged(@Payload ProductChanged productChanged){
    // try {
    // log.info("productChanged StreamListener : {}", new
    // CustomObjectMapper().writeValueAsString(productChanged));
    // } catch (Exception e){
    // log.error("json parse error {}",e);
    // }
    // }

}
