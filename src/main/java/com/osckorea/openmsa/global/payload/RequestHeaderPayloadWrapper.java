package com.osckorea.openmsa.global.payload;

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
public class RequestHeaderPayloadWrapper {
    
    protected RequestHeaderPayload requestHeaderPayload;

}
