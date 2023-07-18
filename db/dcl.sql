-- [참고] https://www.lesstif.com/dbms/postgresql-61899197.html

-- 계정 생성
-- CREATE USER {user_name} WITH PASSWORD '{user_password}';
create user hanex with password 'hanex1234';

-- psql 연결
psql -U hanex -d hanexdb  -W -h localhost

-- 계정 조회 (psql)
\du

-- 계정에 ROLE 추가 (데이터베이스를 생성할 수 있는 Role)
ALTER ROLE hanex CREATEDB REPLICATION;

-- database 생성
CREATE DATABASE hanexdb OWNER hanex ENCODING 'utf-8';

