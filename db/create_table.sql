--------------------------------------
-- create_table.sql
--------------------------------------
/*
[테이블 목록]
고객사 (화주) customer
사용자 (거래처,실행사) member_tb
로그인 base_user
jwt 테스트용도 테이블 : user_tb

[추후...]
고객사 권한 매핑 client_auth
권한 auth_mgmt
그룹 group_mgmt
*/

/*
[참고]
postgres 예약어일경우
>> [테이블명이 예약어일경우] 테이블명 + '_tb'
>>>> (ex) login_tb
>> [컬럼명이 예약어일경우] 테이블명 + 컬럼명
>>>> (ex) customer_group
[예약어 확인]
https://postgresql.kr/docs/10/sql-keywords-appendix.html
*/


-- base_user: 로그인
CREATE TABLE base_user (
	id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	user_id varchar(40) NOT NULL UNIQUE,
	login_id varchar(100) NOT NULL UNIQUE,
	"password" varchar(255) NOT NULL,
	user_role varchar(100) NOT NULL,
	PRIMARY KEY(id)
);

-- customer: 고객사
CREATE TABLE customer (
  id varchar(40) NOT NULL,
  version int8,
  customer_status varchar(30) NOT NULL,
  customer_group varchar(30) NOT NULL,
  customer_name varchar(255) NOT NULL,
  phone varchar(150),
  memo varchar(1000),
  email varchar(255),
  created_by varchar(40),
  created_at timestamp,
  updated_by varchar(40),
  updated_at timestamp,
  PRIMARY KEY(id)
);


-- member_tb : 사용자 (거래처,실행사)
CREATE TABLE member_tb (
  id varchar(40) NOT NULL,
  customer_id varchar(40) NOT NULL,
  member_code varchar(40) NOT NULL,
  member_type varchar(30) NOT NULL,
  manager_name varchar(255) NOT NULL,
  member_status varchar(255) NOT NULL,
  ceo_name varchar(255) ,
  registration_number varchar(255) ,
  phone varchar(255) ,
  memo varchar(255) ,
  email varchar(255) ,
  created_by varchar(40),
  created_at timestamp,
  updated_by varchar(40),
  updated_at timestamp,
  CONSTRAINT member_tb_pkey PRIMARY KEY (id)
);

-- user_tb: JWT 로그인용
-- 추후 han express admin 용 테이블로 수정예정
CREATE TABLE user_tb (
    id varchar(40) NOT NULL,
    login_id VARCHAR(255) UNIQUE,
    user_name VARCHAR(255),
    user_status VARCHAR(100),
    user_role VARCHAR(100),
    email VARCHAR(150) ,
    user_password VARCHAR(255),
    phone VARCHAR(150),
    created_at timestamp,
    updated_at timestamp,
    PRIMARY KEY (id)
);

-- product_tb: 상품
CREATE TABLE product_tb (
	id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	version int8,
	product_name varchar(255) NOT NULL,
	stock int8 NOT NULL,
	description varchar(500),
	product_code varchar(255),
	product_status varchar(50),
	customer_id varchar(200),
	supply_company_id varchar(200),
	created_by varchar(40),
    created_at timestamp,
    updated_by varchar(40),
    updated_at timestamp,
	PRIMARY KEY(id)
);
-----------------
-- fk
-----------------
ALTER TABLE member_tb ADD CONSTRAINT fk_member_tb_customer_id_customer_id FOREIGN KEY (customer_id) REFERENCES customer(id);

-------------------------------------- 개발 예정 --------------------------------------------


-- menu_mgmt : 메뉴 관리
CREATE TABLE menu_mgmt (
  code varchar(20) PRIMARY KEY,
  menu_name varchar(100),
  menu_url varchar(50),
  upper_menu_code varchar(20),
  sort_seq int,
  created_by varchar(30),
  created_at timestamp,
  updated_by varchar(30),
  updated_at timestamp
);

-- menu_auth : 메뉴 권한 관리
CREATE TABLE menu_auth (
  menu_auth_id varchar(32) PRIMARY KEY,
  menu_code varchar(20) NOT NULL,
  auth_id varchar(32) NOT NULL,
  use_yn char(1),
  inq_auth varchar(20),
  inq_yn char(1),
  insert_yn char(1),
  update_yn char(1),
  delete_yn char(1),
  excel_up_yn char(1),
  excel_down_yn char(1),
  created_by varchar(30),
  created_at timestamp,
  updated_by varchar(30),
  updated_at timestamp
);


-- group_mgmt : 그룹 관리 (고객사)
CREATE TABLE group_mgmt (
  code varchar(20) primary key,
  name varchar(100),
  upper_code varchar(20),
  unit char(7),
  use_yn char(1),
  sort_seq int,
  memo varchar(4000),
  created_by varchar(30),
  created_at timestamp,
  updated_by varchar(30),
  updated_at timestamp
);

-- auth_mgmt : 권한 관리
CREATE TABLE auth_mgmt (
  id varchar(32) primary key,
  auth_code varchar(20) ,
  auth_name varchar(20) ,
  sort_seq int,
  created_by varchar(30),
  created_at timestamp,
  updated_by varchar(30),
  updated_at timestamp
);

-- client_auth: 고객사 권한매핑
CREATE TABLE client_auth (
  id varchar(32)  primary key,
  client_id varchar(32) ,
  auth_code varchar(20) ,
  group_code varchar(20) ,
  auth_id varchar(32)  NOT NULL,
  created_by varchar(30),
  created_at timestamp,
  updated_by varchar(30),
  updated_at timestamp
);