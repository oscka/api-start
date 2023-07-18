# 01. 요구사항 정리

## 1.권한
참고 | https://osc-korea.atlassian.net/wiki/spaces/consulting/pages/810877437

![권한.jpg](etc/auth.jpg)

풀필먼트 시스템은 권한 기반이기 때문에 고객사의 거래처도 대상이 될 수 있다.

### 권한 Dimension 설명
- A (관리항목)  > 화주, 채널 등
- B (업무)     > CSR 그룹, 재고관리 그룹, 계약관리 그룹, 기획 그룹 등
- C (기능)     > Dashboard, CO, SO, 계약 등

#### 고객사, 거래처, 실행사, 택배사  
- 고객사(화주)
  - 한Express에게 물류 서비스 대행을 요청하는 고객  
- 거래처
  - 고객사의 거래처 (예 : 인삼공사의 온라인 쇼핑몰 or 개인 고객 or 매장 점주)  
- 실행사
  - 지입차량,배,창고 등을 가지고 있는 실제 물류를 실행할 대상  
- 택배사
  - 대부분의 택배사는 api 인터페이스를 제공하기 때문에 그를 활용하는 대상

### CODE 참고

현재 ENUM 으로 권한과 그룹을 Check 하고 있으나 추후 권한, 그룹이 추가되거나 복잡해질경우 DB table 을 만들어 Check 하는 방향으로 변경해야 할 것같다. 

- UserRole.java
```java
@RequiredArgsConstructor
@Getter
public enum UserRole implements EnumType {

	ROLE_ADMIN("관리자"), // 관리자 (내부 사용자)

	ROLE_CUSTOMER("고객사"), // 고객사 (화주)

	ROLE_MEMBER("사용자") // 사용자 (거래처,실행사,택배사)
	;
	// 생략
}
```
Group.java
```java
@RequiredArgsConstructor
@Getter
public enum Group implements EnumType{


    FINANCE("재무"),
    CSR("CSR"),
    INVENTORY("재고"),
    PROJECT("기획")
    ;

    // 생략
```


[word]
client customer member user account

 
