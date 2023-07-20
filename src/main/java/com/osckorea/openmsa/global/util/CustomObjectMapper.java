package com.osckorea.openmsa.global.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

@Component
public class CustomObjectMapper extends ObjectMapper {


    public CustomObjectMapper() {
          // 모르는 property에 대해 무시하고 넘어간다. DTO의 하위 호환성 보장에 필요하다.
          configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
          // ENUM 값이 존재하지 않으면 null로 설정한다. Enum 항목이 추가되어도 무시하고 넘어가게 할 때 필요하다.
          configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
          // serialize/deserialize java 8 java.time with Jackson JSON mapper
          registerModule(new JavaTimeModule());
    }

}
