# Flix Code Challenge

# Requirement
1. Crew 관리
   - 기사 정보 (이름, 연락처, 근무 시작일) 관리
   - 근무 가능 시간 관리
   - 휴가 및 휴식 시간 관리
2. Schedule 관리
   - 노선별 운행 시간표 관리
   - 기사 배정 및 변경
   - 비상 상황 대응을 위한 대체 기사 관리
3. 근무 규정 준수
   - 최대 연속 근무 시간 제한
   - 최소 휴식 시간 보장
   - 월간 총 근무 시간 관리
4. 실시간 모니터링
   - 현재 운행 중인 기사 현황
   - 다음 교대 시간 알림
   - 지연 및 문제 상황 관리

## 주요 기능
1. 크루 배정
   - 스케쥴에 가용 가능한 크루 조회
     - 근무 시간 제한 확인
     - 휴식 시간 확인
     - 자격 요건 확인
2. 엔드 포인트
   - POST /api/v1/assignments/{scheduleId}/assign: 특정 스케쥴에 크루 배정
   - GET /api/v1/assignments/crew/{crewId}: 특정 크루 배정 현황 조회
   - GET /api/v1/crews/available: 현재 가용 가능한 크루 목록 조회
   - POST /api/v1/schedules: 새로운 스케쥴 등록
3. 비즈니스 규칙
    - 일일 근무 시간 8시간 이하
    - 원간 근무 시간 160시간 이하
    - 일일 휴식 시간 보장
    - 자격증 유효성 검증

# Non Functional Requirement
1. 확장성: 분산 시스템 (ApplicationPublisher?), 캐싱
2. 안정성: 장애 복구, 데이터 백업, 트랜잭션 관리,


## Flyway
Database versioning
```
V<버전번호>__<설명>.sql
```
- 버전번호: 1,2,1.1 숫자 혹은 점 포함
- 설명: 스크립트에 대한 간단한 설명
- 예시
  - V1_0_0__init_schema.sql
  - V2__add_user_table.sql
- 스프링부트는 자동으로 migration 폴더의 sql 실행
- flyway_schema_history로 마이그레이션 기록 관리
```shell
flyway migration
```
```shell
spring:
  flyway:
    baseline-on-migrate: true
    baseline-version: 1
```
baseline-on-migration: 
- flyway_shema_history 생성
- flyway 스크립트 실행
- 유일한 차이점: flyway가 관리하지 않는 테이블이 있는 경우
  - true: 덮어씌움
  - false: 에러 발생

버전관리
1. 메이저 버전: 호환되지 않는 변경사항
   - 테이블 삭제, 스키마 구조변경
2. 마이너 버전: 하위 호환된느 기능
  - 새로운 테이블 추가
3. 패치 버전: 버그, 성능, 기존 기능 수정
  - 컬럼 기본값 수정,
  - 타입 변경
  - 버그 수정