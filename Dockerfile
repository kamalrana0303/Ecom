FROM openjdk:11
ADD build/libs/ecom-0.0.1-snapshot.jar  ecom-0.0.1-snapshot.jar
EXPOSE  8080
ENTRYPOINT  ["java", "-jar", "ecom-0.0.1-snapshot.jar"]