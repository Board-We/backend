# Board, Me (보드미)

![보드미 콜라주](https://github.com/Board-We/backend/assets/29030538/7ce0f374-0616-4fb0-a5b0-5ffdc01d83d1)

- 나만의 롤링페이퍼 보드를 만들어서 공유하는 서비스입니다.
    
- 원하는 기간동안 롤링페이퍼를 받고, 원하는 기간동안 공개할 수 있어요.
    
- 보드 배경과 롤링페이퍼 색상을 자유롭게 꾸밀 수 있어요.
    
- 검색을 통해 전체 공개 보드를 찾을 수 있어요.

[⭐보드미 바로 가기](https://main.d2f3nm5ta023uv.amplifyapp.com/)

<br/>

## Table of Contents

- [Skils](#skils)
- [Team](#team)
- [프로젝트 진행 및 이슈 관리](#프로젝트-진행-및-이슈-관리)
- [ERD](#erd)
- [API Document](#api-document)
- [배포](#배포)
- [구현과정(설계 및 의도)](#구현과정설계-및-의도)
- [TIL 및 회고](#tiltoday-i-learn-및-회고)

<br/>

## Skils

<div align=center> 
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/spring boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/spring data jpa-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">

<br/>

<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
<img src="https://img.shields.io/badge/Github Actions-2088FF?style=for-the-badge&logo=Githubactions&logoColor=white">
<img src="https://img.shields.io/badge/AWS Ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">
<img src="https://img.shields.io/badge/AWS s3-569A31?style=for-the-badge&logo=amazons3&logoColor=white">

<br/>

<img src="https://img.shields.io/badge/Github-181717?style=for-the-badge&logo=Github&logoColor=white">
<img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white">
<img src="https://img.shields.io/badge/erd cloud-%23000000.svg?style=for-the-badge&logo=diagrams.net&logoColor=white">
</div>

<br/>

## Team

- 기획/디자인 2명, FE 3명, BE 3명으로 구성

### Backend

| 팀원                                             | 담당                                                                             |
|------------------------------------------------|--------------------------------------------------------------------------------|
| [원정연](https://github.com/jjungyeun) | 보드 상세 조회, 보드 테마 관련 기능, 이미지 관련 기능(AWS S3), <br> DevOps (Github Actions, AWS EC2) |
| [김영진](https://github.com/jcdad3000)           | 보드 생성/삭제, 메모 관련 기능                                                             |
| [김현우](https://github.com/KimHyunWoo12)                | 보드 검색, 인기보드 리스트 조회, 추천 보드 리스트 조회                                               |

<br/>

## 프로젝트 진행 및 이슈 관리

<img src="https://img.shields.io/badge/Github-181717?style=for-the-badge&logo=Github&logoColor=white">
<img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white">


### 요구사항 분석 및 일정 관리 - Notion

[요구사항 분석 - Link](https://wonwonjung.notion.site/7d25f7d956b74345b9bce7d973d951cf?pvs=4)

![task-management](https://github.com/Board-We/backend/assets/29030538/7a7467c7-ee4a-4c3b-8efb-a02a6c2b464a)

### 이슈 관리 - Github
![issue-management](https://github.com/Board-We/backend/assets/29030538/8231a24c-05e8-48d7-bf38-321294208792)

<br/>

## ERD

[ERD Cloud - Link](https://www.erdcloud.com/d/qosW2HRqNwG4G3uPu)

![erd](https://github.com/Board-We/backend/assets/29030538/d61efab5-2060-4483-b9c9-0a8229901c26)

<br/>

## API Document
[API 명세서 - Notion Link](https://wonwonjung.notion.site/API-Document-95633a2466e840c792c2387f1cddb398?pvs=4)

<br/>

## 배포

<img src="https://img.shields.io/badge/Github Actions-2088FF?style=for-the-badge&logo=Githubactions&logoColor=white">
<img src="https://img.shields.io/badge/AWS Ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">
<img src="https://img.shields.io/badge/AWS s3-569A31?style=for-the-badge&logo=amazons3&logoColor=white">

![ci-cd](https://github.com/Board-We/backend/assets/29030538/75b52b9b-d224-4f88-8f1e-bcdd4ffbe63d)

![aws](https://github.com/Board-We/backend/assets/29030538/cc7c0b5f-05c5-4345-87cf-1db28b368851)

![postman](https://github.com/Board-We/backend/assets/29030538/cbb8c5bd-38c9-4cde-8f37-a9121a5c41ef)


- main 브랜치로 병합하면 Github Actions에서 자동적으로 아래 동작들을 수행한다.
  1. 프로젝트 빌드
  2. 빌드된 파일을 S3에 업로드
  3. EC2에서 빌드된 파일을 불러와서 서버 실행(배포)

<br/>


## 구현과정(설계 및 의도)


<details>

<summary>DB Primary Key 선정 - click</summary>

#### 논의 사항

- 각 테이블의 unique한 컬럼(또는 컬럼의 조합)을 PK로 사용하는 것이 좋을지, 아니면 서비스 내부적으로만 사용되는 임의의 ID 컬럼을 추가하여 사용하는 것이 좋을지

#### 조사 내용

- 별도 ID 컬럼이 필요한 이유
  - 실무에서는 언젠가 요구조건이 바뀔 수 있기 때문에 비즈니스와 관련 없는 컬럼을 ID로 지정하는 것이 좋다.
  - 비즈니스와 관련된 컬럼을 PK로 지정하게 되면 해당 컬럼에 대한 요구조건이 변경될 경우 이를 FK로 사용하는 모든 테이블에 영향이 간다.
  - 단, 컬럼이 명확한 값을 가지며 PK조건을 완전히 만족하고, 미래에도 변할 가능성이 없다면 PK로 설정해도 좋다.
- Auto Increment ID
  - 분산형 시스템 사용 시 여러 데이터베이스 끼리 동기화가 잘 이루어지지 않으면, ID가 중복되어 생성될 수 있다.
  - 키를 예측하기 쉽기 때문에 SQL Injection 공격에 취약해질 수 있다.
- UUID
  - 100% unique한 것은 아니지만 충돌할 가능성이 굉장히 낮다. (10^-38 정도의 확률)
  - UUID를 사용하려면 BINARY(16), VARCHAR 등을 사용해야 한다.
    - int 타입을 쓰는 auto increment보다 메모리를 더 많이 차지하고, 서버에서 UUID를 생성해서 넣어줘야 하므로 INSERT 시간이 더 걸린다는 단점이 있다.

#### 결론

- 단일 DB라면 AUTO_INCREMENT 사용, 다중 DB를 사용하는 분산형 환경이면 데이터 일관성을 위해 UUID를 사용하는 것을 고려하는 것이 좋다.
- `보드미`에서는 단일 DB를 사용하며 id가 예측되는 상황에 예민하지 않기 때문에 Auto Increment ID를 PK로 사용하였다.
  - 단, 보드마다 보드 코드를 UUID로 생성하여 보드에 접근하는 URL에는 숫자 키가 아닌 코드를 사용하였다.

</details>

<details>
<summary>엔티티 생성 방식 - click</summary>

#### 가능한 방식

1. 생성자
   - 모든 필드를 포함한 생성자를 만들어 사용한다.
   - 필드가 많은 경우 생성자 사용시 인자를 넣는 순서가 헷갈릴 수 있다.
2. static 생성 메서드
   - 엔티티 생성 과정에서 비즈니스 로직이 들어가거나, 의미있는 메서드 이름이 필요한 경우세 사용한다.
   - 1번 방식과 마찬가지로 파라미터가 많으면 사용에 어려움을 느낄 수 있다.
3. Builder
   - builder 패턴을 활용하여 파라미터가 많은 경우 사용성을 높일 수 있다.
     - Lombok의 `@Builder`를 사용하여 간편하게 만들 수 있다.

#### 결론

- 필드가 적은 경우(2개 이하)에만 생성자 방식을 사용하고 그 외에는 모두 Builder 방식을 사용하기로 결정하였다.
- 생성자(Builder 포함) 파라미터에서 DB의 PK가 되는 id를 제외하여 개발자가 실수로 id를 지정하지 않도록 하였다.
- 생성자(Builder 포함) 내에 엔티티 필드의 유효성을 검증하는 로직을 추가하였다.
  - ex) 보드의 `startDate`는 `endDate` 보다 이후일 수 없다.

</details>

<details>
<summary>DEV / PROD 환경 분리 - click</summary>

- AWS에 서버를 올리면서 개발 환경과 운영 환경을 따로 관리할 필요성이 생겼다.
  - 환경에 따라 달라야 했던 설정은 DB 관련 정보, 로그 전략, AWS 관련 정보 등이 있었다.
- `application.yml`을 환경마다 만들어 각 환경에 필요한 설정값을 넣어주었다.
  - `application-dev.yml` - 인텔리제이로 실행 시 AWS 키를 넣어줄 수 있는 설정, 개발용 S3 버킷 이름, 로그 설정 파일
  - `application-prod.yml` - 운영용 포트 번호, 로그 설정 파일
    - prod용 설정값의 일부는 Github Actions에서 빌드 시 자동으로 세팅되게 하였다.
- 로그 설정 파일을 분리하여 환경마다 필요한 레벨의 로그가 출력되도록 하였다.
  - `log4j2-dev.xml` - 서버 콘솔에 로그 출력 
  - `log4j2-prod.xml` - 일자마다 로그 파일 생성

</details>

<br/>

## TIL(Today I Learn) 및 회고

<details>
<summary>조회수 증가 로직의 비동기 처리 - click</summary>

- 현재 작성된 코드는 사용자가 보드를 조회하는 로직 내에서 조회수를 증가시킨다.
  - 조회용 API이지만 조회수 업데이트를 위해 트랜잭션이 해당 row에 대한 lock을 필요로 한다.
  - 따라서 다른 트랜잭션이 lock을 갖고 있는 경우에 lock이 반환되길 기다려야 하며, 이는 응답 반환 시간에 영향을 줄 수 있다.
- `보드미` 서비스는 조회수가 실시간으로 완벽히 정확하지 않아도 괜찮기 때문에, 조회 로직과 조회수 증가 로직을 분리할 필요가 있다.
  - 조회 로직에서 비동기로 조회수 증가 로직을 호출만하고, 조회 로직을 바로 종료한다.
  - 조회만 일어나기 때문에 lock이 필요하지 않으며, 빠르게 사용자에게 응답을 반환할 수 있다.

</details>

<details>
<summary>Github Actions로 AWS EC2에 자동 배포하기 - click</summary>

#### 문제 상황

- 백엔드 서버를 docker 이미지로 만들고 AWS EC2 서버 내에서 다운받아 실행시키는 과정을 통해 서버를 배포하였다.
  - 수동으로 배포했기 때문에 서버 코드가 수정될 때마다 위 과정을 반복해야 했다.
  - 최초 배포 후에 프론트엔드와 연동하면서 수정사항이 많았기 때문에 수동 배포는 매우 번거로웠으며, 자동 배포를 적용하고자 하였다.

#### 해결 과정

- CD를 적용하고자 Github Actions를 사용하였다.
- [`deploy.yaml`](https://github.com/Board-We/backend/blob/develop/.github/workflows/deploy.yaml)에 배포 절차를 순서대로 정의하여 main 브랜치로 병합 시 자동적으로 실행되게 하였다.
  1. Spring Boot 내에 환경변수 세팅 (`application-prod.yml`)
  2. Spring Boot 프로젝트 빌드 (`.jar` 파일 생성)
  3. `.jar`를 zip 파일로 만들어 S3에 업로드
  4. CodeDeploy 배포 프로세스 실행
- CodeDeploy 절차
  1. EC2 내부에서 지정된 경로에 `.zip` 파일 저장
  2. Spring Boot 프로젝트를 실행하는 스크립트 실행

</details>