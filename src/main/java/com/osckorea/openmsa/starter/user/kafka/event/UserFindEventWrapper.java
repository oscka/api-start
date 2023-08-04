package com.osckorea.openmsa.starter.user.kafka.event;

import com.osckorea.openmsa.global.payload.RequestHeaderPayloadWrapper;
import com.osckorea.openmsa.global.util.ThreadUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = false)
@EqualsAndHashCode(callSuper = false)
public class UserFindEventWrapper extends RequestHeaderPayloadWrapper {
    
    UserFindEvent userFindEvent;

    public static UserFindEventWrapper setRequestHeader (UserFindEvent userFindEvent) {

        return UserFindEventWrapper.builder()
                .requestHeaderPayload(ThreadUtil.threadLocalRequestHeaderPayload.get())
                .userFindEvent(userFindEvent)
                .build();
    }

}
