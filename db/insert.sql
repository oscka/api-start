--------------------------------------
-- user_tb insert
-- 테스트 용도 USER
-- 비밀번호 통일함 : test1234
--------------------------------------

INSERT INTO user_tb
(id, login_id, user_name, user_status, user_role, email, user_password, phone, created_at, updated_at)
VALUES('277e49ab-4846-47aa-88d8-d135c11843c9'::uuid, 'user1', '홍길동', 'ACTIVE', 'ROLE_CUSTOMER', 'user1@hanex.com', '$2a$10$nD37s21bS6z53V0zSwtQ2eEQ.NRUut5F0ZvzGV8CaGI8DtWNAvUS2', '01012345678', '2023-05-30 12:57:21.749', '2023-05-30 12:57:21.786');

INSERT INTO user_tb
(id, login_id, user_name, user_status, user_role, email, user_password, phone, created_at, updated_at)
VALUES('e2524fbe-9166-47fc-b63e-761bb4ab7c4f'::uuid, 'admin', '관리자', 'ACTIVE', 'ROLE_ADMIN', 'admin@demo.com', '$2a$10$z1iL9HpqPFbPEmDpthdIsOZr5uO36GznTTJ6yBPK27J7OYqmM1l6G', '01012347890', '2023-05-30 13:27:30.299', '2023-05-30 13:27:30.300');

INSERT INTO user_tb
(id, login_id, user_name, user_status, user_role, email, user_password, phone, created_at, updated_at)
VALUES('7de5eb3f-fe59-453e-a0c1-f1f497ceee5c'::uuid, 'test', '테스트', 'ACTIVE', 'ROLE_MEMBER', 'test@demo.com', '$2a$10$Yf6yGPfbLcAn.0rRe.UcFuE2TRDHHKLOJ6r1XIHqzCvrW6sq5TZCW', '01056781234', '2023-05-30 16:35:39.466', '2023-05-30 16:35:39.493');

--------------------------------------
-- base_user insert
-- customer 와 member_tb 의 로그인, password , ROLE 정보를 갖고 있다.
-- login table 로 사용할 예정
-- 비밀번호 통일함 : test1234
--------------------------------------
INSERT INTO base_user
(id, user_id, login_id, "password", user_role)
VALUES(1, '0cffe143-d103-4525-a0d6-f6b780d97f3f', 'customer01', '$2a$10$LXFGD10re0RMyFn18gAVBOoKTHHH7Xcln2Q3kIPB8exHr6jWvf6Sa', 'ROLE_CUSTOMER');

INSERT INTO base_user
(id, user_id, login_id, "password", user_role)
VALUES(3, 'f2741827-b891-41cb-b809-63440424dca8', 'member_01', '$2a$10$laB73UtlDyiCxFbW.XfHuOlp6GF/oAsX3CJ3GVfqgsGdIRLq5tAzC', 'ROLE_MEMBER');

--------------------------------------
-- customer insert
--------------------------------------
INSERT INTO customer
(id, "version", customer_status, customer_group, customer_name, phone, memo, email, created_by, created_at, updated_by, updated_at)
VALUES('0cffe143-d103-4525-a0d6-f6b780d97f3f', 1, 'ACTIVE', 'FINANCE', '인삼공사', '01012345678', '고객사 메모', 'customer01@test.com', NULL, '2023-06-01 16:45:27.035', NULL, '2023-06-01 16:45:27.035');

--------------------------------------
-- member insert
--------------------------------------
INSERT INTO member_tb
(id, customer_id, member_code, member_type, manager_name, member_status, ceo_name, registration_number, phone, memo, email, created_by, created_at, updated_by, updated_at)
VALUES('f2741827-b891-41cb-b809-63440424dca8', '0cffe143-d103-4525-a0d6-f6b780d97f3f', 'e315282134c9470791fc6100785edb61', 'CLIENT', '홍길동', 'ACTIVE', '홍길동', '130-81-16025', '02-3400-5474', '고객메모', 'member_01@test.com', '0cffe143-d103-4525-a0d6-f6b780d97f3f', '2023-06-01 18:10:39.633', '0cffe143-d103-4525-a0d6-f6b780d97f3f', '2023-06-01 18:10:39.679');

--------------------------------------
-- product_tb insert
--------------------------------------
INSERT INTO product_tb
(id, product_name, stock, description, product_code, product_status, customer_id, supply_company_id, created_at, updated_at, "version", created_by, updated_by)
VALUES(1, '스테이크볶음밥', 100, '남녀노소 좋아하는 스테이크볶음밥', '8d93ee4cc95044e99459d1534d81e3ec', 'Y', 'e1065afe-5d59-4293-b3f9-037d3122b8b6', '5edfc5a9-3675-4b6d-a72e-43a7f5c6cf0f', '2023-06-13 14:48:52.863', '2023-06-13 14:48:52.863', 1, '277e49ab-4846-47aa-88d8-d135c11843c9', '277e49ab-4846-47aa-88d8-d135c11843c9');

--------------------------------------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------- 개발 전 --------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------
-- auth_mgmt insert
--------------------------------------

INSERT INTO auth_mgmt
(id, auth_code, auth_name, sort_seq, created_by, created_at, updated_by, updated_at)
VALUES('215939fc7d5349738c8ec4a320a1dd02', 'ROLE_0', '시스템관리자', 0, 'SADMIN', NULL, 'SADMIN', NULL);
INSERT INTO auth_mgmt
(id, auth_code, auth_name, sort_seq, created_by, created_at, updated_by, updated_at)
VALUES('a436d3593e3a47f2aa85b521ce1f97a9', 'ROLE_2', '본사직원', 2, 'SADMIN', NULL, 'SADMIN', NULL);
INSERT INTO auth_mgmt
(id, auth_code, auth_name, sort_seq, created_by, created_at, updated_by, updated_at)
VALUES('d2d5cce3d3324df9a26c9a0f8da8572b', 'ROLE_99', '퇴사자', 99, 'SADMIN', NULL, 'SADMIN', NULL);
INSERT INTO auth_mgmt
(id, auth_code, auth_name, sort_seq, created_by, created_at, updated_by, updated_at)
VALUES('d844ee481095455eadf84981f83394ef', 'ROLE_3', '팀장', 3, 'SADMIN', NULL, 'SADMIN', NULL);
INSERT INTO auth_mgmt
(id, auth_code, auth_name, sort_seq, created_by, created_at, updated_by, updated_at)
VALUES('f0b97eda8e514bd2b01e79b25a8f31dd', 'ROLE_1', '관리자', 1, 'SADMIN', NULL, 'SADMIN', NULL);


--------------------------------------
-- group_mgmt insert
--------------------------------------
INSERT INTO group_mgmt
(code, name, upper_code, unit, use_yn, sort_seq, memo, created_by, created_at, updated_by, updated_at)
VALUES('GRP001', '경영팀', NULL, '1', 'Y', 10, '경영팀 memo' ,'SADMIN', NULL, 'SADMIN', NULL);
INSERT INTO group_mgmt
(code, name, upper_code, unit,  use_yn, sort_seq, memo, created_by, created_at, updated_by, updated_at)
VALUES('GRP002', '재무팀', 'GRP001', '2', 'Y', 20, '재무팀 memo', 'SADMIN', NULL, 'SADMIN', NULL);
INSERT INTO group_mgmt
(code, name, upper_code, unit, use_yn, sort_seq, memo, created_by, created_at, updated_by, updated_at)
VALUES('GRP003', '재고팀', 'GRP001', '2', 'Y', 30, '재고팀 memo', 'SADMIN', NULL, 'SADMIN', NULL);
INSERT INTO group_mgmt
(code, name, upper_code, unit, use_yn, sort_seq, memo, created_by, created_at, updated_by, updated_at)
VALUES('GRP004', '업무1팀', 'GRP001', '2', 'Y', 40, ' 업무1팀 memo', 'SADMIN', NULL, 'SADMIN', NULL);
INSERT INTO group_mgmt
(code, name, upper_code, unit, use_yn, sort_seq, memo, created_by, created_at, updated_by, updated_at)
VALUES('GRP005', '기획팀', 'GRP001', '2', 'Y', 50, '기획팀 memo', 'SADMIN', NULL, 'SADMIN', NULL);
INSERT INTO group_mgmt
(code, name, upper_code, unit, use_yn, sort_seq, memo, created_by, created_at, updated_by, updated_at)
VALUES('GRP007', 'CSR팀', 'GRP005', '3',  'N', 70, '기획 > CSR팀  memo', 'SADMIN', NULL, 'SADMIN', NULL);


