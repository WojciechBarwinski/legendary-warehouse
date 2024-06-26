FROM openjdk:17-jdk

WORKDIR /app

COPY target/legendary-warehouse-0.0.1-SNAPSHOT.jar /app/legendary-warehouse.jar

EXPOSE 8081

CMD ["java", "-jar", "legendary-warehouse.jar"]


#mvn clean package    <---- do zbudowania aplikacji po zmianach
#docker build -t legendary-warehouse:latest .     <--- do zbudowania obrazu
#docker run -p 8081:8081 legendary-warehouse:latest    <--- do zbudowania kontenera