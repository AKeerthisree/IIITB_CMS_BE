FROM openjdk:8
ADD target/IIITB_CMS_BE-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8030
ENTRYPOINT ["java","-jar","/app.jar"]