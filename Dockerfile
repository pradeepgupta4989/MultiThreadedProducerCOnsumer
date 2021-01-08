FROM openjdk:8-jdk-alpine
COPY ./target/multithreaded-producer-consumer-0.0.1.jar /usr/app
WORKDIR /usr/app
EXPOSE 8888
ENTRYPOINT ["java","-jar","/multithreaded-producer-consumer-0.0.1.jar"]