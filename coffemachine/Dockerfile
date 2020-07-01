FROM openjdk:8-jdk-alpine
EXPOSE 8080
RUN apk add --no-cache curl tar bash maven procps
ARG MAVEN_VERSION=3.6.1         

# 6- Define environmental variables required by Maven, like Maven_Home directory and where the maven repo is located
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
ARG JAR_FILE=./target/*.jar
ARG APP_PROPERTIES=./config/application.properties
RUN addgroup -S spring && adduser -S spring -G spring
COPY . /tmp
RUN cd /tmp && mvn package
RUN cd / && find /tmp/target/ -name '*.jar' -exec cp {} /app.jar \; && cp /tmp/config/application.properties /application.properties
RUN rm -r /tmp/* ~/.m2 &
USER spring:spring
ENTRYPOINT ["java","-jar","/app.jar"]
