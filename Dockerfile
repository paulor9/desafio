FROM openjdk:11
ADD target/desafio-0.0.1-SNAPSHOT.jar desafio-0.0.1-SNAPSHOT.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "desafio-0.0.1-SNAPSHOT.jar"]
