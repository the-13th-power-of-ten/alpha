![10조](https://github.com/user-attachments/assets/14d64ce4-5dfb-46cb-873e-2b3b46bd15b3)

# ✏️ 목차
* [🏷️ 프로젝트 소개](#a)

* [📆 개발 기간](#b)

* [🖇️ 팀 구성](#c)

* [⚙️ 개발 환경](#d)

* [🔑 환경 변수](#e)

* [📑 ERD DIAGRAM](#f)

* [🗂️ API 명세서](#g)

<br>

<div id="a">
  
# 🏷️ 프로젝트 소개
**"tentillion"** 프로젝트는 **A10조**의 팀 프로젝트로, 
1. 사용자 인증
2. 보드 CRUD
3. 컬럼 CRUD
4. 카드 CRUD
5. 댓글 CRUD

등의 기능이 구현된 칸반보드(업무 단계를 나타내는 가상보드) 웹 애플리케이션입니다.

<br>

<div id="b">
  
# 📆 개발 기간
* 2024.07.10 - 2024.07.15
* 2024.07.16 발표

<br>

<div id="c">

# 🖇️ 팀 구성
| 이유환 [팀장]                         | 김민식                         | 한진경                         | 류승범                         | 윤일영                         |
|-------------------------------|-------------------------------|-------------------------------|-------------------------------|-------------------------------|
| [![이유환](https://github.com/Berithx.png)](https://github.com/Berithx) | [![김민식](https://github.com/minsik0.png)](https://github.com/minsik0) | [![한진경](https://github.com/jkhan94.png)](https://github.com/jkhan94) | [![류승범](https://github.com/W-llama.png)](https://github.com/W-llama) | [![윤일영](https://github.com/1004102.png)](https://github.com/1004102) |
|Global : <br> - AOP와 필터를 활용 공통 Response <br> - User 공동 구현|User : <br> - CRUD <br> - 이메일 인증|Board : <br> - CRUD <br> - Member Invite|Stat : <br> - CRUD <br> - Card Comment : CR|Card : <br> - CRUD

<br>

<div id="d">
 
# ⚙️ 개발 환경
* IDE : IntelliJ IDEA
* JDK : 17
* DB : MySQL 8.0

<br>

<div id="e">

# 🔑 환경 변수
```
MYSQL_PORT={}
MYSQL_DATABASE=tentrillion
MYSQL_USER={username}
MYSQL_PASSWORD={password}
JWT_SECRET_KEY={value}
JWT_ACCESS_EXPIRE_TIME=3600000
JWT_REFRESH_EXPIRE_TIME=2592000000
SMTP_EMAIL={EMAIL}
SMTP_APP_PASSWORD={value}
```
---
<br>

<div id="f">

# 📑 ERD DIAGRAM
![image](https://github.com/user-attachments/assets/7ac6f8cc-0d8b-4e05-a4d7-069c11b2ee95)

<br>

<div id="g">

# 🗂️ API 명세서
![API](https://github.com/user-attachments/assets/27804bba-520c-49fa-acfc-470aeb43940a)
