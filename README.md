# CH4 과제


## LV 0 - 요금 폭탄 방지 AWS Budget 설정

![레벨 0](./images/aws_budget.png)

## LV 1 - 네트워크 구축 및 핵심 기능 배포

![레벨 1](./images/lv1-page.png)
EC2 퍼블릭 IP: 43.203.245.57

## LV 2 - DB 분리 및 보안 연결하기

### Actuator Info 엔드포인트 URL
![레벨 2](./images/Inf0.png)
http://43.203.245.57:8080/actuator/info

### RDS 보안 그룹 스크린샷
![레벨 2](./images/rds1.png)
![레벨 2](./images/rds2.png)

## LV 3 - 프로필 사진 기능 추가와 권한 관리 

### S3 비공개 버킷 프로필 이미지 접근 성공 스크린샷
![레벨 3](./images/downUrl.png)
![레벨 3](./images/downUrl2.png)

## LV 4 - Docker & CI/CD 파이프라인 구축

### Github Actions 성공 이미지
![레벨 4](./images/action.png)

### EC2 터미널 이미지
![레벨 4](./images/dockerps.png)

## LV 5 - 고가용성 아키텍처와 보안 도메인 연결 (ALB + ASG + HTTPS

### HTTPS 적용된 도메인 URL
https://api.jaegu.click/actuator/health

### Target Group(대상 그룹) 이미지
![레벨 5](./images/targetGroup.png)