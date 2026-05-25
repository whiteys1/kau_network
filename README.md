# KauNetwork (2025 kau_network)

Spring Boot와 JWT(JSON Web Token)를 활용하여 구현한 회원 인증/인가 및 프론트엔드 연동 프로젝트입니다.

## 🛠 기술 스택 (Tech Stack)

**Backend**
* Java 17
* Spring Boot 3.4.5
* Spring Security
* JWT (io.jsonwebtoken 0.11.5)
* Spring Data JPA
* MySQL (mysql-connector-j)
* Lombok
* Swagger / Springdoc-openapi 2.7.0

**Frontend**
* HTML5, CSS3, Vanilla JavaScript

**CI/CD**
* GitHub Actions
* AWS EC2

---

## ✨ 주요 기능 (Features)

1. **사용자 인증 및 인가 (Authentication & Authorization)**
   * **JWT 기반 인증**: Access Token 및 Refresh Token 발급 및 검증 로직 구현.
   * **Spring Security 연동**: `JwtAuthenticationFilter`를 통한 요청 헤더 검증 및 접근 제어.
   * **비밀번호 암호화**: `BCryptPasswordEncoder`를 적용한 안전한 사용자 정보 저장.

2. **사용자 도메인 (User Domain)**
   * **회원가입 (`POST /auth/signup`)**: 이메일, 비밀번호, 이름, 전화번호, 주소 정보를 입력받아 계정 생성 (이메일 중복 체크).
   * **로그인 (`POST /auth/login`)**: 이메일 및 비밀번호 검증 후 JWT 토큰 반환.
   * **내 정보 조회 (`GET /auth/me`)**: 토큰 인증을 통과한 사용자의 정보 조회.
   * **로그아웃 (`POST /auth/logout`)**: 발급된 Refresh Token 무효화.

3. **프론트엔드 뷰 템플릿 제공**
   * 정적 리소스(`src/main/resources/static/`)를 통해 회원가입(`signup.html`), 로그인(`login.html`), 마이페이지(`mypage.html`) UI 기본 제공.
   * Fetch API를 사용한 비동기 백엔드 서버(`52.79.104.211:8080`) 통신 구현.

4. **자동 배포 파이프라인 (CI/CD)**
   * `dev_deploy.yml`을 통한 GitHub Actions 워크플로우 구성.
   * Main 브랜치 푸시 시 자동 빌드 후 AWS EC2 인스턴스로 `.jar` 파일 배포 및 무중단 실행(`nohup`).

---

## 🚀 API 명세서 (API Specification)

Swagger UI를 통해 상세한 API 명세 확인 및 테스트가 가능합니다. 애플리케이션 실행 후 아래 URL에 접속하십시오.
* **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
* **API Docs**: `http://localhost:8080/v3/api-docs`

| HTTP Method | Endpoint | Description | Auth Required |
| --- | --- | --- | --- |
| `POST` | `/auth/signup` | 회원가입 | ❌ |
| `POST` | `/auth/login` | 로그인 | ❌ |
| `GET` | `/auth/me` | 로그인한 사용자 정보 조회 | ⭕️ (Bearer Token) |
| `POST` | `/auth/logout` | 로그아웃 | ⭕️ (Bearer Token) |

---

## ⚙️ 실행 방법 (Getting Started)

### 1. 환경 변수 설정
프로젝트 실행 전 `application.yml`에 주입될 다음 환경 변수(또는 IDE의 Environment Variables)를 설정해야 합니다.
* `DB_URL`: MySQL 데이터베이스 URL (예: `jdbc:mysql://localhost:3306/kaunetwork`)
* `DB_PASSWORD`: 데이터베이스 비밀번호
* `jwt_secret`: JWT 암호화를 위한 Secret Key (충분한 길이의 임의 문자열)

### 2. 프로젝트 빌드 및 실행
```bash
# Repository 클론
git clone
