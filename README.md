![image](https://user-images.githubusercontent.com/487999/79708354-29074a80-82fa-11ea-80df-0db3962fb453.png)

# 예제 - 음식배달
[이벤트 스토밍]

![이벤트스토밍](https://user-images.githubusercontent.com/16378278/203219614-1060fe60-263d-4fba-9f55-d2a6b88bf1ac.PNG)


[새로운 기능]
1. 사고 시 주문 취소
라이더가 배달 도중 음식이 훼손된 경우 주문을 취소하는 과정
![image](https://user-images.githubusercontent.com/16378278/203236196-a879274d-e011-4fc3-867e-44efffacd210.png)
![image](https://user-images.githubusercontent.com/16378278/203236234-e3100a21-e1f3-4bfb-a53f-081ad77f0201.png)

2. 음식마다 주문수 확인 (인기있는 음식 확인)
주문을 시작하고 요리가 시작되었을때(취소할 수 없는 상태) 주문된 요리의 갯수를 올림
![image](https://user-images.githubusercontent.com/16378278/203236477-fca3bc57-c77b-4dac-810c-d57b1ed8a76c.png)
![image](https://user-images.githubusercontent.com/16378278/203236506-4b7d5e23-96b9-4637-b057-61b60587ebeb.png)

 


본 예제는 MSA/DDD/Event Storming/EDA 를 포괄하는 분석/설계/구현/운영 전단계를 커버하도록 구성한 예제입니다.
이는 클라우드 네이티브 애플리케이션의 개발에 요구되는 체크포인트들을 통과하기 위한 예시 답안을 포함합니다.
- 체크포인트 : https://workflowy.com/s/assessment-check-po/T5YrzcMewfo4J6LW

# Table of contents

- [예제 - 음식배달](#---)
  - [서비스 시나리오](#서비스-시나리오)
  - [체크포인트](#체크포인트)
  - [분석/설계](#분석설계)
  - [구현:](#구현-)
    - [DDD 의 적용](#ddd-의-적용)
    - [폴리글랏 퍼시스턴스](#폴리글랏-퍼시스턴스)
    - [폴리글랏 프로그래밍](#폴리글랏-프로그래밍)
    - [동기식 호출 과 Fallback 처리](#동기식-호출-과-Fallback-처리)
    - [비동기식 호출 과 Eventual Consistency](#비동기식-호출-과-Eventual-Consistency)
  - [운영](#운영)
    - [CI/CD 설정](#cicd설정)
    - [동기식 호출 / 서킷 브레이킹 / 장애격리](#동기식-호출-서킷-브레이킹-장애격리)
    - [오토스케일 아웃](#오토스케일-아웃)
    - [무정지 재배포](#무정지-재배포)
  - [신규 개발 조직의 추가](#신규-개발-조직의-추가)

# 서비스 시나리오

배달의 민족 커버하기 - https://1sung.tistory.com/106

기능적 요구사항
1. 고객이 메뉴를 선택하여 주문한다
1. 고객이 결제한다
1. 주문이 되면 주문 내역이 입점상점주인에게 전달된다
1. 상점주인이 확인하여 요리해서 배달 출발한다
1. 고객이 주문을 취소할 수 있다
1. 주문이 취소되면 배달이 취소된다
1. 고객이 주문상태를 중간중간 조회한다
1. 주문상태가 바뀔 때 마다 카톡으로 알림을 보낸다


비기능적 요구사항
1. 트랜잭션
    1. 결제가 되지 않은 주문건은 아예 거래가 성립되지 않아야 한다  Sync 호출 
1. 장애격리
    1. 상점관리 기능이 수행되지 않더라도 주문은 365일 24시간 받을 수 있어야 한다  Async (event-driven), Eventual Consistency
    1. 결제시스템이 과중되면 사용자를 잠시동안 받지 않고 결제를 잠시후에 하도록 유도한다  Circuit breaker, fallback
1. 성능
    1. 고객이 자주 상점관리에서 확인할 수 있는 배달상태를 주문시스템(프론트엔드)에서 확인할 수 있어야 한다  CQRS
    1. 배달상태가 바뀔때마다 카톡 등으로 알림을 줄 수 있어야 한다  Event driven

[CQRS]
[주문 상태 확인]
고객 또는 점주는 현재 주문의 상태를 지속적으로 확인 할 수 있다.
![image](https://user-images.githubusercontent.com/16378278/203234918-0c5d4411-e52c-49cf-9f77-7e6586f126ea.png)
[음식 주문 개수 확인]
주문된 음식의 총 개수를 확인 할 수 있다.
![image](https://user-images.githubusercontent.com/16378278/203235591-9dc332ed-79d2-4e65-a6dc-7f71d0b9672a.png)


[Compensation / Correlation]

![image](https://user-images.githubusercontent.com/16378278/203238149-d36c67de-fcab-4b2f-95dc-e8271b2207df.png)
![image](https://user-images.githubusercontent.com/16378278/203236917-3ec5c11c-284a-49ed-aa1e-9a66f8a41fb2.png)


  - API 게이트웨이
    - API GW를 통하여 마이크로 서비스들의 집입점을 통일할 수 있는가?
    - 게이트웨이와 인증서버(OAuth), JWT 토큰 인증을 통하여 마이크로서비스들을 보호할 수 있는가?

![image](https://user-images.githubusercontent.com/16378278/203221326-7ffc559c-552c-413e-a1f6-208d6a93c872.png)



## 동기식 호출 과 Fallback 처리

![image](https://user-images.githubusercontent.com/16378278/203220623-0cfa23cb-ac21-4e4c-af18-158d2778807d.png)

![image](https://user-images.githubusercontent.com/16378278/203219984-0551c536-4884-48e1-8c5b-be3f3679b1b0.png)


[Circuit Breaker]

![image](https://user-images.githubusercontent.com/16378278/203221063-35d97281-5838-4d8e-aaab-4cc46359fccc.png)







## 비동기식 호출 / 시간적 디커플링 / 장애격리 / 최종 (Eventual) 일관성 테스트


![image](https://user-images.githubusercontent.com/16378278/203220353-e5dba4ac-0a96-44b0-9255-5c244d9c1a85.png)
![image](https://user-images.githubusercontent.com/16378278/203220472-e7c86bde-147b-4ae8-a6db-4b36d7df5958.png)





