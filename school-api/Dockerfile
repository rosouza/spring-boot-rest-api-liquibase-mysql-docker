FROM openjdk:11
ADD ./target/school-api-1.0.0.jar /usr/src/school-api-1.0.0.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "school-api-1.0.0.jar"]
