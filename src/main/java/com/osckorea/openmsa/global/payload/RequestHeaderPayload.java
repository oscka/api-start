package com.osckorea.openmsa.global.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "내부요청헤더")
public class RequestHeaderPayload {
    
    /**
	 * 사용자ID
	 */
    @Schema(description = "사용자ID", nullable = false)
    private String userId;

    /**
	 * 사용자권한
	 */
    @Schema(description = "사용자권한", nullable = false)
    private String userRole;

    /**
	 * 부서ID
	 */
    @Schema(description = "부서ID", nullable = false)
    private String deptId;

    /**
	 * 업무ID
	 */
    @Schema(description = "업무ID", nullable = false)
    private String subjectId;

    /**
	 * 서비스정보
	 */
    @Schema(description = "서비스정보", nullable = false)
    private String serviceId;

    /**
	 * 메뉴정보
	 */
    @Schema(description = "메뉴정보", nullable = false)
    private String menuId;

    /**
	 * 화면정보
	 */
    @Schema(description = "화면정보", nullable = false)
    private String viewId;

    /**
	 * 트랜잭션정보
	 */
    @Schema(description = "트랜잭션정보", nullable = false)
    private String trId;
}
