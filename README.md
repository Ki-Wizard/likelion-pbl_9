# LIKELION PBL 9 - Relationship & Transaction

Spring Boot 3.x, Spring Data JPA, MySQL 기반으로 `Member`와 `Assignment`의 1:N 양방향 관계를 구현한 과제입니다.
작성자 : 김민수

## Requirements

- JDK 17+
- MySQL database: `likelion_pbl`
- Spring Web
- Spring Data JPA
- MySQL Connector/J

## Run

```bash
CREATE DATABASE likelion_pbl;
```

```bash
./mvnw spring-boot:run
```

## API

### Member

| Method | URI | Description |
| --- | --- | --- |
| POST | `/members/lions` | Lion 등록 |
| POST | `/members/staffs` | Staff 등록 |
| GET | `/members` | 전체 멤버 조회 |
| GET | `/members/{id}` | 멤버 단건 조회 |
| PUT | `/members/lions/{id}` | Lion 수정 |
| PUT | `/members/staffs/{id}` | Staff 수정 |
| DELETE | `/members/{id}` | 멤버 삭제 |

### Assignment

| Method | URI | Description |
| --- | --- | --- |
| POST | `/members/{memberId}/assignments` | 과제 등록 |
| GET | `/members/{memberId}/assignments` | 멤버별 과제 목록 조회 |
| GET | `/assignments/{id}` | 과제 단건 조회 |
| PUT | `/assignments/{id}` | 과제 수정 |
| DELETE | `/assignments/{id}` | 과제 삭제 |

## Test Profile

로컬 MySQL 없이 검증하려면 H2 기반 `test` 프로필을 사용합니다.

```bash
./mvnw test
./mvnw spring-boot:run -Dspring-boot.run.profiles=test
```
