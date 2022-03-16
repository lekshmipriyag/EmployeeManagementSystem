#define base docker image
# we run the spring boot application by using the JAR file of java
FROM openjdk:8
LABEL maintainer="Lekshmi Geetha"
#EmployeeManagementSystem-0.0.1-SNAPSHOT.jar is the source jar file located in target folder
# employee-version3 (docker image name)
COPY ./target/EmployeeManagementSystem.jar employee-version3.jar
#entrypoint would tell the docker how to execute the application
ENTRYPOINT ["java","-jar", "employee-version3.jar"]
