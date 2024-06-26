-- 시험용 SQL 파일

-- TB_USER 테이블 생성 및 SEQ_UNO 시퀀스 생성

CREATE TABLE TB_USER(

USER_NO NUMBER PRIMARY KEY,

USER_ID VARCHAR2(50) UNIQUE NOT NULL,

USER_NAME VARCHAR2(50) NOT NULL,

USER_AGE NUMBER NOT NULL

);

CREATE SEQUENCE SEQ_UNO;

INSERT INTO TB_USER VALUES(SEQ_UNO.NEXTVAL, 'gd_hong', '홍길동', 20);

INSERT INTO TB_USER VALUES(SEQ_UNO.NEXTVAL, 'sh_han', '한소희', 28);

INSERT INTO TB_USER VALUES(SEQ_UNO.NEXTVAL, 'jm_park', '지민', 27);

INSERT INTO TB_USER VALUES(SEQ_UNO.NEXTVAL, 'jm123', '지민', 25);

COMMIT;

SELECT * FROM TB_USER 
WHERE USER_ID = 'gd_hong';


---------------------------------------------------------------------------------------

CREATE TABLE CUSTOMER(

CUSTOMER_NO NUMBER PRIMARY KEY,

CUSTOMER_NAME VARCHAR2(60) NOT NULL,

CUSTOMER_TEL VARCHAR2(30) NOT NULL,

CUSTOMER_ADDRESS VARCHAR2(200) NOT NULL

);

[시퀀스]

CREATE SEQUENCE SEQ_CUSTOMER_NO NOCACHE;

SELECT * FROM CUSTOMER
ORDER BY CUSTOMER_NO ;


-- 240423 강찬혁 작업 내용
-- 목적 : 메인메뉴의 즐겨찾기 테스트
-- BOTTOM_MENU 테이블의 샘플 데이터 삽입
-- MEMBER_MENU 테이블의 샘플 데이터 삽입
-- BOTTOM_MENU

INSERT INTO BOTTOM_MENU
VALUES (991, '임시메뉴1', 881);

INSERT INTO BOTTOM_MENU
VALUES (992, '임시메뉴2', 882);

INSERT INTO BOTTOM_MENU
VALUES (993, '임시메뉴3', 883);

-- INSERT INTO MEMBER_MENU VALUES (2, 991, 'N'); 
-- INSERT INTO MEMBER_MENU VALUES (3, 992, 'N'); 
-- INSERT INTO MEMBER_MENU VALUES (4, 993, 'N'); 

COMMIT;


SELECT BOTTOM_MENU_CODE 
FROM MEMBER_MENU mm 	
WHERE MEMBER_NO = 1;


-- 좋아하는 하위 게시판 이름 
SELECT BOTTOM_MENU_NAME 
FROM BOTTOM_MENU bm 
WHERE BOTTOM_MENU_CODE IN (
    SELECT BOTTOM_MENU_CODE
    FROM MEMBER_MENU mm 
    WHERE MEMBER_NO = 1
);

SELECT bm.BOTTOM_MENU_NAME
FROM MEMBER_MENU mm
JOIN BOTTOM_MENU bm ON mm.BOTTOM_MENU_CODE = bm.BOTTOM_MENU_CODE
WHERE mm.MEMBER_NO = 1;

SELECT BOTTOM_MENU_NAME 
FROM BOTTOM_MENU bm 
JOIN MEMBER_MENU mm ON bm.BOTTOM_MENU_CODE = mm.BOTTOM_MENU_CODE
WHERE mm.MEMBER_NO = 1;

SELECT BOTTOM_MENU_NAME
		FROM BOTTOM_MENU bm
		JOIN MEMBER_MENU mm USING (BOTTOM_MENU_CODE)
		WHERE mm.MEMBER_NO = 1;
--
--SELECT TOP_MENU_NAME 
--FROM TOP_MENU tm 
--WHERE TOP_MENU_CODE IN (
--
--	SELECT TOP_MENU_CODE  
--	FROM BOTTOM_MENU bm 
--	WHERE BOTTOM_MENU_CODE IN (
--	    SELECT BOTTOM_MENU_CODE
--	    FROM MEMBER_MENU mm 
--	    WHERE MEMBER_NO = 1)
--);
--
--SELECT TOP_MENU_CODE  
--FROM BOTTOM_MENU bm 
--WHERE BOTTOM_MENU_CODE IN (
--    SELECT BOTTOM_MENU_CODE
--    FROM MEMBER_MENU mm 
--    WHERE MEMBER_NO = 1
--);

-- 좋아하는 상위 게시판 이름
SELECT tm.TOP_MENU_NAME 
FROM TOP_MENU tm 
INNER JOIN BOTTOM_MENU bm ON tm.TOP_MENU_CODE = bm.TOP_MENU_CODE
INNER JOIN MEMBER_MENU mm ON bm.BOTTOM_MENU_CODE = mm.BOTTOM_MENU_CODE
WHERE mm.MEMBER_NO = 1;

-- 임시 탈퇴

DELETE
FROM "MEMBER" 
WHERE MEMBER_EMAIL = '027620@naver.com';

COMMIT;

































