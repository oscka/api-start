package com.osckorea.openmsa.global.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import feign.Retryer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@EnableFeignClients(basePackages ="com.osckorea.openmsa")
@Configuration
public class OpenFeignConfig implements Jackson2ObjectMapperBuilderCustomizer {


    @Bean
    public Retryer retryer() {
        // 재시도는 1초를 시작으로, 최대 2초로 재시도하고, 최대 3번을 재시도한다.
        return new Retryer.Default(1000, 2000, 3);
    }

    @Bean
    public FeignFormatterRegistrar dateTimeFormatterRegistrar() {
        return registry -> {
            DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
            registrar.setUseIsoFormat(true);
            registrar.registerFormatters(registry);
        };
    }

    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        // 참고 : https://kwonnam.pe.kr/wiki/java/jackson
        jacksonObjectMapperBuilder
                // ENUM 값이 존재하지 않으면 null로 설정한다. Enum 항목이 추가되어도 무시하고 넘어가게 할 때 필요하다.
                .featuresToEnable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
                // 모르는 property에 대해 무시하고 넘어간다. DTO의 하위 호환성 보장에 필요하다.
                .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
