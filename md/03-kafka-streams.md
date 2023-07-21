# 04-kafka-streams.md

### 1. docker-compose for Kafka
- [docker-compose 로 Kafka, Zookeeper 설치](etc/kafka-docker-compose.yml)

(!) 사전에 docker-compose 가 설치돼있어야합니다.  
docker-compose 로 Kafka , Zookeeper 를 설치합니다.  
Local PC Test 용도 Single Broker 입니다.  

```shell
# docker-compose 실행
docker-compose -f kafka-docker-compose.yml up -d

# docker 상태,로그 확인
docker ps
docker logs -f {CONTAINER ID} 

# docker-compose 종료
docker-compose -f kafka-docker-compose.yml down -v
```

### 2. Kafka shell example
- [Kafka Command Example](etc/kafka-command-example.sh)  

위 파일에서는 Topic 생성및 확인 , Message 관리 Command 를 간단히 설명합니다.

### 3. Kafka Streams?
1) [Kafka 공식 문서 - Kafka Streams 란](https://kafka.apache.org/26/documentation/streams/core-concepts)
2) [java example - Kafka Streams VS Kafka Consumer](https://www.baeldung.com/java-kafka-streams-vs-kafka-consumer)
3) [kafka Consumer api vs Streams api](https://stackoverflow.com/questions/44014975/kafka-consumer-api-vs-streams-api)

  
Kafka Streams 는 Kafka 에 저장된 데이터를 처리하고 분석하기 위한 클라이언트 라이브러리이다.  

Kafka (pub/sub) 방식으로 데이터 변환 작업을 개발할경우 application 개발자가 전부 코딩으로 구현해야하지만,  
Kafka Streams 로 구현할 경우  ```group By, count, filter, join, aggregate``` 등 스트림 프로세싱 API 제공하기 때문에 더 빠르고 편리하게 개발을 진행할 수 있다.  





### 4. spring cloud stream 용어 및 개념
- [Spring 공식 문서 - Spring Cloud Stream](https://cloud.spring.io/spring-cloud-stream-binder-kafka/spring-cloud-stream-binder-kafka.html#_kafka_binder_properties)

Spring Cloud Stream 은 Spring Cloud 에서 제공하는 메시징 시스템 프레임워크이다.

RabbitMQ, Apache Kafka, Kafka Streams, Amazon Kinesis 등 다양한 binder 를 제공하고 있다.


- **바인더 (Binder)**
  - 외부 메시징 시스템과의 통합을 제공하는 구성 요소
- **바인딩 (Binding)**
  - 외부 메시징 시스템과 최종 사용자가 제공하는 애플리케이션 코드(생산자/소비자) 사이를 연결한다.
- **메시지**
  - 생산자와 소비자가 대상 바인더(및 외부 메시징 시스템을 통한 다른 응용 프로그램)와 통신하기 위해 사용하는 표준 데이터 구조


### 5. application.yml (Spring Cloud Stream Binder Kafka)
[Spring 공식문서 - application.yml 속성](https://cloud.spring.io/spring-cloud-stream-binder-kafka/spring-cloud-stream-binder-kafka.html)

```yaml
spring:
  cloud:
    stream:
      defaultBinder: kafka
      # spring.cloud.stream.function
      function:
        definition: productUpdate
      # spring.cloud.stream.bindings
      bindings:
        productUpdate-in-0:
          destination: product-update-topic
          group: product
          contentType: application/json
          consumer:
            max-attempts: 2
        productUpdate-out-0:
          destination: product-update-topic
          contentType: application/json
      # spring.cloud.stream.kafka
      kafka:
        # spring.cloud.stream.kafka.default
        default:
          consumer:
            ack-mode: MANUAL_IMMEDIATE
          producer:
            useTopicHeader: true
        # spring.cloud.stream.kafka.bindings
        bindings:
          productUpdate-in-0:
            consumer:
              autoCommitOffset: false # 메시지가 처리되었을 경우, 오프셋을 자동으로 커밋할지를 설정한다.
              enableDlq: true
              dlqName: product-update-dlq
              #dlqPartitions: 1
        # spring.cloud.stream.kafka.binder
        binder:
          autoCreateTopics: false # true로 설정하면 토픽이 존재하지 않을 경우 자동으로 토픽을 만들어준다.
          producerProperties:
            key.serializer: org.apache.kafka.common.serialization.StringSerializer
            value.serializer: org.apache.kafka.common.serialization.ByteArraySerializer
```
- spring.cloud.stream.kafka.binder.brokers
  - Kafka 바인더가 연결되는 브로커 목록
- spring.cloud.stream.bindings.output.contentType: application/json
  - 기본 인코딩이 비활성화된 경우(기본값) 프레임워크는 사용자가 설정한 contentType을 사용하여 메시지를 변환


### 6. starter 프로젝트 sample code flow

1. Create Topic (토픽 생성)
   product-update-topic, product-update-dlq 을 만들어준다.
```shell
docker exec kafka kafka-topics --create --topic product-update-topic --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1
docker exec kafka kafka-topics --create --topic product-update-dlq --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1
```

2. product 정보 수정
   - swagger > product api 화면에서 상품 정보를 수정한다.
   - 이벤트 발생시 아래 명령어로 터미널에서 확인할 수 있다.
```shell
[appuser@c0715a9c629a ~]$ kafka-console-consumer --topic product-update-topic --from-beginning --bootstrap-server kafka:9092
{"eventType":"ProductChanged","productId":1,"productName":"스테이크볶음밥","productStock":50}
```

### 7. Error Handling

메세지 처리 도중 에러가 발생했을 때 처리하는 방법

#### 1) Drop Failed Messages (실패한 메세지 삭제)
첫번째 오류처리기는 단순히 오류메시지를 기록한다.  
두번째 오류처리기는 특정 메시지 시스템의 컨텍스트에서 오류 메시지를 처리하는 책임이 있는 바인더 특정 오류 처리기이다.

#### 2) Handle Error Messages (오류 메시지 처리)


#### 3) DLQ ( Dead Letter Queue )
- DLQ 가 설정 되어 있을경우 실패한 메세지를 후속처리 하기위해 설정한 목적지로 전송된다.
- DLQ 메커니즘 특성상 반드시 group 이 지정돼있어야한다.(실패한 메세지를 전송할 토픽에 group 이름이 들어감)
- 기본값 >> DLQ: false

```yaml
# DLQ name 설정
spring.cloud.stream.kafka.streams.bindings.input.consumer.dlqName: foo-dlq
```

#### 4) Retry Template (재시도 프로퍼티 속성)



### 8. Kafka 설정
[Kafka properties 설정](./05-kafka-properties.md)
