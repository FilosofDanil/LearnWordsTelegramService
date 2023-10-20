FROM openjdk:17-jdk

WORKDIR /app

COPY target/dictionaryApi-0.0.1-SNAPSHOT.jar /app/dictionary.jar

EXPOSE 8777

CMD ["java", "-jar", "dictionary.jar"]