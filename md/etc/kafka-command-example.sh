# kafka-command-example.sh
# docker-compose 로 띄운 Kafka 에서 사용하는 간단한 Command

# Create Topic (토픽 생성)
docker exec kafka kafka-topics --create --topic product-update-dlq --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1

# Describe Topic (생성된 토픽 확인)
docker exec kafka kafka-topics --describe --topic product-update-topic --bootstrap-server kafka:9092

# Remove Topic (토픽 삭제)
docker exec kafka kafka-topics --delete --topic product-update-topic --bootstrap-server kafka:9092
docker exec kafka kafka-topics --delete --topic domainEventModel-out-0 --bootstrap-server kafka:9092

# Show List Topic (토픽 리스트 확인)


# 컨슈머 실행
## 1) 컨테이너 내부의 쉘로 접속
docker exec -it kafka bash

## 2) 컨슘할 토픽을 지정하고, 브로커를 지정하기 위해서 --bootstrap-server 를 이용
# [appuser@c0715a9c629a ~]$ kafka-console-consumer --topic {토픽명} --bootstrap-server kafka:9092
kafka-console-consumer --topic product-update-topic --bootstrap-server kafka:9092
#  해당 토픽의 모든 메시지 출력
kafka-console-consumer --topic product-update-topic --from-beginning --bootstrap-server kafka:9092

# 프로듀서 실행하기
## 1) 컨테이너 내부의 쉘로 접속
docker exec -it kafka bash
## 2)
kafka-console-producer --topic product-update-topic --broker-list kafka:9092
## 3) > 가 나오면 write 할 메세지를 적으면 된다.

## 컨슈머 그룹
# 컨슈머 그룹은 따로 생성하는 명령어 필요 없이 컨슈머 동작 시 컨슈머 그룹명을 지정하면 새로 생성됩니다.

# 컨슈머 그룹 리스트 조회
docker exec -it kafka bash
kafka-consumer-groups --list --bootstrap-server kafka:9092


