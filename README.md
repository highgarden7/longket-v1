# LongKet #

Longket Server

### 개발환경 ###
* OS : MacOS 13.3.1
* IDE : intellij community 2023.01
* JAVA 17 (corretto 17)
* spring boot 3.2.1
* gradle 8.5
* mysql 8.0
* redis 8.0

### JWT TOKEN 방식 ###

1. Oauth 로그인 요청
2. 로그인 성공시 jwt 토큰 발급
3. 토큰 만료시 refresh 토큰으로 jwt 토큰을 발급

### Oauth ###
* google

### SSL ###
* letsencrypt 사용중
* Docker 컨테이너 실행 시 -v /etc/letsencrypt:/etc/letsencrypt:ro letsencrypt 인증서 volume mount
* 인증서 갱신시 -> docker run -it --rm --name certbot -v "/etc/letsencrypt:/etc/letsencrypt" certbot/certbot certonly --webroot -w /etc/letsencrypt/www -d api.longboard.monster
