package com.osckorea.openmsa.starter.user.kafka.event;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFindEvent {

    @Builder.Default
    private String eventType = UserFindEvent.class.getSimpleName();
    private String loginId;
    private String name;

}
