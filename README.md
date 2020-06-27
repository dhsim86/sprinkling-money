# sprinkling-money

## 도메인

* 요구사항에 따라 다음과 같이 도메인 모델링 진행

### User

* 사용자를 표헌, 대화방 (Room)과 N:N 관계

### Room

* 대화방 표현, 사용자 (User)와 N:N 관계

### Distribution

* 뿌리기건
* 뿌리기 요청시 비즈니스 로직을 담는다.
  * 뿌리기 요청시, 요청한 사용자와 같은 대화방에 있는 사람들을 위한 DistributionInfo 생서
  * 뿌린 금액에 대해 받기 사용자 validation 체크

### DistributionInfo

* 뿌리기 요청시 사용자 수에 맞게 생성된 뿌리기 분배건
* 받은 사용자 정보 및 받아간 금액을 표현

### Token

* 사용자 토큰

## Layer

* DDD에 따른 계층화

### Application Layer
  * package: com.kakaopay.sprinkling_money.application
  * 응용 서비스 계층
  
### Domain Layer
  * package: com.kakaopay.sprinkling_money.domain
  * 비즈니스 로직이 담긴 도메인 클래스 및 도메인 서비스 클래스들 위치
  
### Infrastructure Layer
  * package: com.kakaopay.sprinkling_money.infrastructure
  * 영속성 관리 및 토큰 발급 클래스들 위치

### Presentation Layer
  * package: com.kakaopay.sprinkling_money.presentation
  * Controller
  
## Token 발급

  * 랜덤한 3자리 길이의 문자열로 구성된 토큰 발급시 SecureRandom 사용
  * [a-z][A-Z][0-9]의 모든 문자가 담긴 String에서 SecureRandom가 생성한 랜덤한 index로 문자 3개를 추출하여 토큰 생성
  * 토큰 발급시 MySQL 데이터베이스에 이미 발급된 토큰인지 확인하여, 여러 서버들이 서비스하더라도 문제없도록 한다.

## 사용자 금액 분배

  * 금액 분배시 사용자 인원 수에 맞게 균등하게 분배
  * 일정 금액 분배 후 남는 금액은 인원들 중 하나를 랜덤으로 선택하여 추가 분배

## 개발 환경

  * Spring Boot 2.2.8.RELEASE
  * JPA (with QueryDSL)

## 실행

  1. QueryDSL 위해 mvn clean compile 실행
  2. 애플리케이션 서버 실행 (mvn spring-boot:run)
  
