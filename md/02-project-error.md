# Project 에러 처리

### ERROR 처리 과정
에러가 발생할경우  GlobalExceptionHandler.java 와 CommonLogHandler.java 에서 처리 하고 있습니다.

Exception400, Exception401, Exception402, Exception403, Exception404, Exception500  클래스가 RunTimeException 을 상속하고,
이를 GlobalExceptionHandler.java 에서 처리하고 있습니다.
```java
// GlobalExceptionHandler.java

@CustomErrorLog
@ExceptionHandler(Exception400.class)
public ResponseEntity<?> badRequest(Exception400 e){
    return new ResponseEntity<>(e.body(), e.status());
}

@CustomErrorLog
@ExceptionHandler(Exception.class)
public ResponseEntity<?> unknownServerError(Exception e){
    ApiResponseDto<String> apiResponseDto = new ApiResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR, "unknownServerError", e.getMessage());
    return new ResponseEntity<>(apiResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
}
```

GlobalExceptionHandler.java 의 badRequest 메서드를 보면 @CustomErrorLog 라는 어노테이션을 확인할 수 있습니다.

@CustomErrorLog 라는 어노테이션으로 인해 CommonLogHandler.java 에서 @Before("customErrorLog()") 를 확인할 수 있습니다.
```java
// CommonLogHandler.java 
    @Before("customErrorLog()")
    public void errorLogAdvice(JoinPoint jp) throws Exception {
        Object[] args = jp.getArgs();

        for (Object arg : args) {
            if(arg instanceof Exception){
                Exception e = (Exception) arg;
                log.error("[CommonLogHandler] ERROR > {}",e.getMessage());
                log.error(Arrays.toString(e.getStackTrace()));
            }
        }
    }
```
@Before 어노테이션은 Spring AOP 에서 제공하는 어노테이션으로, 해당 메소드 실행 전 Advice 실행을 의미합니다.

따라서 ResponseEntity 를 반환하기전 error log 를 console 에 출력후, ResponseEntity 객체를 반환합니다.

### ERROR RESPONSE
- 에러 발생시 ApiResponseDto.java 를 직렬화한 json 을 반환하게됩니다.

Exception400 응답 json
```json
{
  "status": 400,
  "msg": "badRequest",
  "data": {
    "key": "loginId",
    "value": "중복되는 로그인 아이디 입니다."
  }
}
```

Exception500 응답 json
```json
{
  "status": 500,
  "msg": "unknownServerError",
  "data": "PreparedStatementCallback; bad SQL grammar [SELECT\n     m.id\n     , m.name\n     , m.manager_name\n     , m.member_code\n     , m.ceo_name\n     , m.registration_number\n     , m.phone\n     , m.memo\n     , m.client_id\n     , m.email\n     , m.created_by\n     , m.created_at\n     , m.updated_by\n     , m.updated_at\nFROM member_tb m\n WHERE  ORDER BY string ASC LIMIT ? OFFSET ?]; nested exception is org.postgresql.util.PSQLException: ERROR: syntax error at or near \"ORDER\"\n  Position: 290"
}
```


### Error Stack Trace 를 봐야할때

개발중 error stack trace 를 봐야한다면, try ~ catch block 으로 감싸줘야합니다.
- 기존 (AS-IS) 
```java
public void save(MemberDto.SaveRequest save){
    memberRepository.insert(save.toEntity());
}
```

- ERROR TRACE 볼때 (TO-BE)
```java
public void save(MemberDto.SaveRequest save){
    try {
        memberRepository.insert(save.toEntity());
    }catch (Exception e){
        e.printStackTrace();
    }
}
```
