# starter
한 Express 템플릿 프로젝트 - Starter

## Project 기술 스택
- Spring Boot 2.7.12 , jdk 17, gradle 7.6.1
- Spring Security (with JWT token)
  - jwt dependency : io.jsonwebtoken
- Spring Data JDBC + Spring JDBC
- Postgresql (prod,dev) , H2 (local,test)
- log4j2 , MDC
- Spring i18n (message source > 다국어처리)
- Spring Data Redis
- swagger (springdoc-openapi)
- kafka streams (spring cloud streams)

## Project Description
1. [Project 요구사항 정리](md/01-requirement.md)
2. [Project 시작 및 사용방법](md/02-project-start.md)
3. [Project 에러 처리](/md/03-project-error.md)
4. [Kafka Streams - Spring Cloud Streams](/md/04-kafka-streams.md)