# starter
OpenMSA inner 템플릿 프로젝트 - Starter

이 프로젝트는 redis, kafka, portgresql db 기반으로 마이크로서비스를 개발하기 위한 템플릿 프로젝트입니다.

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
1. [Project 시작 및 사용방법](md/01-project-start.md)
2. [Project 에러 처리](/md/02-project-error.md)
3. [Kafka Streams - Spring Cloud Streams](/md/03-kafka-streams.md)
4. [Kafka Properties](/md/04-kafka-properties.md)
5. [Skaffold Build and Dockerize](/md/05-skaffold-and-kustomize.md)