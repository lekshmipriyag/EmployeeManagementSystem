#define base docker image
# we run the spring boot application by using the JAR file of java
FROM openjdk:8
LABEL maintainer="Lekshmi Geetha"
#employee-salary.jar is the source jar file located in target folder
# employee-salary (docker image name)
COPY ./target/employee-salary.jar employee-salary.jar
#entrypoint would tell the docker how to execute the application
ENTRYPOINT ["java","-jar", "employee-salary.jar"]