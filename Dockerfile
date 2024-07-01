FROM openjdk:17-jdk

WORKDIR /app

COPY target/legendary-warehouse-0.0.1-SNAPSHOT.jar /app/legendary-warehouse.jar

EXPOSE 8081

CMD ["java", "-jar", "legendary-warehouse.jar"]


