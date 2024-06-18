plugins {
    java
    id("org.springframework.boot") version "3.2.1-SNAPSHOT"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.longketdan"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // lombok
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    testImplementation("org.projectlombok:lombok")

    // open api doc
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
//    implementation("org.springdoc:springdoc-openapi-security:1.6.9")

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // oauth
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    // mysql
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java:8.0.23")

    // webclient
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    //health check
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    //redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis:3.1.0")

    //amazon s3
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.10")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
