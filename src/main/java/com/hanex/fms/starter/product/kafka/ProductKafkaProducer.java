package com.hanex.fms.starter.product.kafka;

import com.hanex.fms.global.exception.Exception500;
import com.hanex.fms.global.util.CustomObjectMapper;
import com.hanex.fms.starter.product.kafka.event.ProductChanged;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

import java.util.UUID;

// @EnableBinding(value= {StreamProcessor.class})
@Slf4j
@RequiredArgsConstructor
@Component
public class ProductKafkaProducer {
   //private final StreamProcessor processor;

    private final StreamBridge streamBridge;

    public void updateProduct(ProductChanged productChanged){
        try {

            // 방법 1)
            // String payload = new CustomObjectMapper().writeValueAsString(productChanged);
            // MessageChannel outputChannel = processor.output();
            // outputChannel.send(MessageBuilder
            //         .withPayload(payload)
            //         .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
            //         .build());

            // 방법 2)
            boolean result = streamBridge.send("productUpdate-out-0", MessageBuilder
                .withPayload(productChanged)
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString())
                .build());
            
            log.info("[Kafka-Producer] --- ProductKafkaProducer.updateProduct --- productUpdate-out-0 >>> result : {}", result);

        } catch (Exception e){
            e.printStackTrace();
            throw new Exception500(e.getMessage());
        }
    }
}
