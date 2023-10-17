FROM openjdk:17-jdk

WORKDIR /app

COPY target/eurekaServer-0.0.1-SNAPSHOT.jar /app/eureka.jar

EXPOSE 8761

CMD ["java", "-jar", "eureka.jar"]