package com.osckorea.openmsa.global.util.jdbc;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.ValueExtractor;

/**
 * 해당 class 를 사용하려면 아래 경로에 파일을 생성해야합니다.
 * FileName : resources/META-INF/services/javax.validation.valueextraction.ValueExtractor
 * FileContent : com.osckorea.openmsa.global.util.jdbc.AggregateReferenceValueExtractor
 * 참고 : https://docs.spring.io/spring-data/jdbc/docs/2.2.3/api/org/springframework/data/jdbc/core/mapping/AggregateReference.IdOnlyAggregateReference.html
 */
public class AggregateReferenceValueExtractor implements ValueExtractor<AggregateReference<?, @ExtractedValue ?>> {
    public AggregateReferenceValueExtractor() {
    }

    // TODO 해당 class 설정 관련 문서 쓸것
    @Override
    public void extractValues(AggregateReference<?, ?> originalValue, ValueReceiver receiver) {
        receiver.value("id", originalValue.getId());
    }
}

