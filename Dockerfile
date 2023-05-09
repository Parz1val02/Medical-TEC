FROM openjdk:17.0.2-jdk
VOLUME /tmp
EXPOSE 8080
LABEL authors="Usuario"
ADD .target/medical-tec-SNAPSHOT.jar medical-tec.jar

ENTRYPOINT ["java", "-jar","medical-tec.jar"]