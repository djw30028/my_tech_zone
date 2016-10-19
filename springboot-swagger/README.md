# Tech Zone


## What you'll need
- JDK 1.8 or later
- Maven 3 or later

## Stack
- Spring Boot
- Java

## Run
`mvn spring-boot:run`
mvn clean package
java -jar target/springboot-swagger-1.3.5.RELEASE.jar

download https://github.com/swagger-api/swagger-ui dist

http://localhost:8080/ --> to view swagger from swagger.json

http://localhost:8080/bradways/greeting

index.html loads swagger css and js 
`url = "http://localhost:8080/swagger.json";`

### integrate Elastics
-- Elastics Search: https://www.elastic.co/guide/index.html, http://www.elastic.co/products/elasticsearch
cd /Users/michaelwang/myprogs/elasticsearch-2.4.0/bin
####  start elasticsearch
sh /Users/michaelwang/myprogs/elasticsearch-2.4.0/bin/elasticsearch

https://www.javacodegeeks.com/2015/03/head-first-elastic-search-on-java-with-spring-boot-and-data-features.html

