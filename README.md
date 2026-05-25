# Network Security Analysis & Authentication System (KAU Network)

본 프로젝트는 Spring Boot 기반의 사용자 인증 시스템과 웹 프론트엔드, 그리고 해당 네트워크 환경에서 발생하는 평문 통신 및 프로토콜 패킷을 캡처하고 분석하기 위한 C++ 기반 네트워크 에이전트로 구성된 통합 보안 연구 프로젝트입니다. 

애플리케이션 계층의 인증 메커니즘과 네트워크 계층의 취약점(ARP Spoofing 및 패킷 복호화)을 유기적으로 연결하여 종단 간(End-to-End) 데이터 흐름과 보안 검증을 수행합니다.

⚠️ **주의 사항 (Disclaimer)**
본 저장소에 포함된 네트워크 분석 및 스푸핑 코드는 대학교 교육 과정 및 학술적 목적의 네트워크 보안 취약점 실험을 위해 작성되었습니다. 승인되지 않은 외부 네트워크 환경에서의 실행을 엄격히 금지하며, 이를 악용하여 발생하는 모든 법적 책임은 실행자에게 있습니다.

---

## 🏗️ 시스템 아키텍처 (System Architecture)

프로젝트는 크게 두 가지 서브 시스템으로 분리되어 구동됩니다.

1. **Target Web Server (`/server`)**
   * 사용자 회원가입, 로그인, 마이페이지 조회를 제공하는 웹 서비스입니다.
   * Spring Security와 JWT를 통해 인증을 수행하며, 프론트엔드 비동기 통신을 통해 백엔드 API와 데이터를 주고받습니다.
2. **Network Analysis Agent (`/network_agent`)**
   * `libpcap` 라이브러리를 활용하여 동일 네트워크 세그먼트 내의 패킷을 가로챕니다.
   * 타겟의 ARP 테이블을 변조하여 통신을 중재(Relay)하고, HTTP 평문 및 WebSocket 마스킹 패킷의 페이로드를 실시간으로 추출·복호화하여 모니터링합니다.

---

## 🛠️ 컴포넌트별 상세 안내

### 1. Web Server (`/server`)
* **기술 스택**: Java 17, Spring Boot 3.4.5, Spring Security, JWT, JPA, MySQL, HTML5/CSS3/JS
* **주요 기능**:
  * `POST /auth/signup` : 이메일 중복 체크를 포함한 회원 정보 등록
  * `POST /auth/login` : 사용자 인증 및 JWT (Access/Refresh Token) 발급
  * `GET /auth/me` : Authorization 헤더(Bearer) 검증을 통한 내 정보 조회
  * 정적 페이지 UI 기본 내장 (`signup.html`, `login.html`, `mypage.html`)

### 2. Network Agent (`/network_agent`)
* **기술 스택**: C++, libpcap, zlib, POSIX Threads
* **주요 기능**:
  * **ARP Table Infection & Cure**: 타겟의 ARP 캐시를 게이트웨이 주소로 지속 감염시키고, 프로그램 종료 시(SIGINT) 정상 복구 수행
  * **Packet Relay**: 가로챈 패킷을 목적지(Gateway)로 재전송하여 타겟의 네트워크 단절을 방지
  * **Payload Decryption**: HTTP(GET/POST) 요청 데이터 출력 및 WebSocket 데이터의 Unmasking 및 zlib 압축 해제(`inflate`) 처리

---

## 🚀 실행 및 빌드 방법 (Getting Started)

### Prerequisites
* Backend: Java 17, MySQL 데이터베이스
* Network Agent: Linux 환경 (인터페이스 조작 권한 필요), `libpcap-dev`, `zlib1g-dev` 라이브러리 설치

### 1. Web Server 실행
`/server` 폴더 내에서 실행 환경 변수(`DB_URL`, `DB_PASSWORD`, `jwt_secret`)를 설정한 후 빌드를 진행합니다.
```bash
cd server
./gradlew build
./gradlew bootRun