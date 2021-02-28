FROM openjdk:17-jdk-alpine3.12 as build
WORKDIR /workspace/app

RUN apk update && apk add maven

COPY pom.xml .
COPY src src

RUN mvn install -DskipTests

FROM openjdk:14.0.2-jdk
ARG TARGET=/workspace/app/target
COPY --from=build ${TARGET}/app.jar ${TARGET}/app.jar
COPY examples ${TARGET}

ENV MYSQL_USER root
ENV MYSQL_PASSWORD 123
ENV MYSQL_PORT 3306
ENV MYSQL_URL localhost
ENV MYSQL_DATABASE inventory
ENV INVENTORY_FILE ./inv/inventory.json
ENV PRODUCTS_FILE ./inv/products.json

WORKDIR /workspace/app/target
ENTRYPOINT ["sh", "-c", "java -jar -Dspring.datasource.url=jdbc:mysql://${MYSQL_URL}:${MYSQL_PORT}/${MYSQL_DATABASE} -Dspring.datasource.username=${MYSQL_USER} -Dspring.datasource.password=${MYSQL_PASSWORD} -Dinventory.inventory-file=${INVENTORY_FILE} -Dinventory.products-file=${PRODUCTS_FILE} -Dspring.jpa.hibernate.ddl-auto=none app.jar"]
