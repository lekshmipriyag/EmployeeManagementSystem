#define base docker image
# we run the spring boot application by using the JAR file of java
FROM openjdk:8
LABEL maintainer="Lekshmi Geetha"
#EmployeeManagementSystem-0.0.1-SNAPSHOT.jar is the source jar file located in target folder
# employeemanagementsystem.jar (docker image name)
COPY ./target/EmployeeManagementSystem-0.0.1-SNAPSHOT.jar employeemanagementsystem.jar
#entrypoint would tell the docker how to execute the application
#employeemanagementsystem.jar is the docker image name
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=sql", "employeemanagementsystem.jar"]
