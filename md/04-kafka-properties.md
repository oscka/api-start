# 05-kafka-properties.md

### Kafka Binder Properties

```yaml
    spring.cloud.stream.kafka.binder.brokers
# Kafka 바인더가 연결될 브로커 목록이다.
# 기본값 : localhost
    
    spring.cloud.stream.kafka.binder.defaultBrokerPort
# brokers 설정에 포트 정보가 존재하지 않는다면 해당 호스트 리스트들이 사용할 포트를 지정해준다.
# 기본값 : 9092
    
    spring.cloud.stream.kafka.binder.configuration
# 바인더로 작성된 모든 클라이언트에 전달 될 클라이언트 속성(프로듀서,컨슈머)의 키/값 맵이다.
# 이러한 속성은 프로듀서와 컨슈머 모두가 사용한다.
# 기본값 : empty map
    
    spring.cloud.stream.kafka.binder.headers
# 바인더에 의해 전송되는 사용자 지정 헤더 목록이다.
# 기본값 : null
    
    spring.cloud.stream.kafka.binder.healthTimeout
# 파티션 정보를 얻는 데 걸리는 시간(초).
# 기본값 : 10
    
    spring.cloud.stream.kafka.binder.requiredAcks
#  브로커에서 필요한 ack수이다.
#  기본값 : 1
    
    spring.cloud.stream.kafka.binder.minPartitionCount
# autoCreateTopics,autoAddPartitions true를 설정했을 경우에만 적용되는 설정이다.
# 바인더가 데이터를 생성하거나 소비하는 주제에 대해 바인더가 구성하는 최소 파티션 수이다.
# 기본값 : 1
    
    spring.cloud.stream.kafka.binder.replicationFactor
    #  autoCreateTopics true를 설정했을 경우에 자동 생성된 토픽 복제요소 수이다.
    #기본값 : 1
    
    spring.cloud.stream.kafka.binder.autoCreateTopics
  # true로 설정하면 토픽이 존재하지 않을 경우 자동으로 토픽을 만들어준다. 
  # 만약 false로 설정되어있다면 미리 토픽이 생성되어 있어야한다.
  # 기본값 : true
    
    spring.cloud.stream.kafka.binder.autoAddPartitions
  #  true로 설정하면 바인더가 필요할 경우 파티션을 추가한다.
  # 예를 들어 사용자의 메시지 유입이 증가하여 컨슈머를 추가하여 파티션수가 하나더 늘었다고 가정하자. 
  # 그러면 기존의 토픽의 파티션 수는 증설한 파티션의 총수보다 작을 것이고,
  # 이 설정이 true라면 바인더가 자동으로 파티션수를 증가시켜준다.
  # 기본값 : false
```
    
    
### Kafka Consumer Properties
```yaml
    spring.cloud.stream.kafka.bindings.<inputChannel>.consumer 접두어가 붙는다.
    
    autoRebalanceEnabled
#    파티션 밸런싱을 자동으로 처리해준다.
#    기본값 : true
    
    autoCommitOffset
#    메시지가 처리되었을 경우, 오프셋을 자동으로 커밋할지를 설정한다.
#    기본값 : true
    
    startOffset
#    새 그룹의 시작 오프셋이다. earliest, latest
#    기본값 : null(==eariest)
    
    resetOffsets
#    consumer의 오프셋을 startOffset에서 제공한 값으로 재설정할지 여부
#    기본값 : false
```    
    
### Kafka Producer Properties
```yaml
    bufferSize
#    kafka 프로듀서가 전송하기 전에 일괄 처리하려는 데이터 크기이다.
#    기본값 : 16384
    
    batchTimeout
# 프로듀서가 메시지를 보내기 전에 동일한 배치에 더 많은 메시지가 누적될 수 있도록 대기하는 시간. 
# 예를 들어 버퍼사이즈가 꽉 차지 않았을 경우 얼마나 기다렸다고 메시지 처리할 것인가를 정하는 시간이다.
# 기본값 : 0
```
-------------------------------------------
auto.offset.reset

auto.offset.reset에서 offset은 consumer offset입니다. 
만약 이번에 topic에 붙은 consumer의 offset정보가 존재하지 않는다면 
auto.offset.reset의 default값(latest)이나 또는 설정한 값을 따라가게 됩니다.

- latest 
  - 가장 마지막 offset부터
- earliest
  - 가장 처음 offset부터
- none
  - 해당 consumer group이 가져가고자 하는 topic의 consuer offset정보가 없으면 exception을 발생시킴.
-------------------------------------------

- RECORD
  - 레코드 단위로 프로세싱 이후 커밋
- BATCH
  - poll() 메서드로 호출된 레코드가 모두 처리된 이후 커밋 , 스프링 카프카 컨슈머의 AckMode 기본값
- TIME
  - 특정 시간 이후에 커밋이 옵션을 사용할 경우 시간 간격을 선언하는 AckTime 옵션을 설정해야 한다.
- COUNT
  - 특정 개수만큼 레코드가 처리된 이후 커밋이 옵션을 사용할 경우에는 레코드 개수를 선언하는 AckCount 옵션을 설정해야 한다.
- COUNT_TIME
  - TIME, COUNT 옵션 중 맞는 조건이 하나라도 나올 경우 커밋
- MANUAL
  - Acknowledgement.acknowledge() 메서드가 호출되면 다음번 poll() 때 커밋을 한다.
  - 매번 acknowledge() 메서드를 호출하면 BATCH 옵션과 동일하게 동작한다.
  - 이 옵션을 사용할 경우에는 AcknowledgingMessageListener 또는 BatchAcknowledgingMessageListener를 리스너로 사용해야 한다.
- MANUAL_IMMEDIATE
  - Acknowledgement.acknowledge() 메서드를 호출한 즉시 커밋한다.
  - 이 옵션을 사용할 경우에는 AcknowledgingMessageListener 또는 BatchAcknowledgingMessageListener를 리스너로 사용해야 한다.

-------------
재시도 구현을 위한 구성:
spring.cloud.stream.bindings.poppyPants.consumer.maxAttempts=3
spring.cloud.stream.bindings.poppyPants.consumer.backOffInitialInterval=900000
spring.cloud.stream.bindings.poppyPants.consumer.backOffMaxInterval=900000
spring.cloud.stream .bindings.poppyPants.consumer.backoffMultiplier=1.0
spring.cloud.stream.bindings.poppyPants.consumer.defaultRetryable=false
-------------