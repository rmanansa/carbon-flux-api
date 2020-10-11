# First-stage: 
FROM adoptopenjdk/openjdk11:alpine as compile
MAINTAINER Ramil Manansala <ramil.manansala@noodle.ai>

# Compile and package the app
RUN apk add maven
WORKDIR /app
COPY . /app/
RUN mvn -f pom.xml clean package -DskipTests


# Second-stage: 
FROM adoptopenjdk/openjdk11:alpine-jre

# Put the application JAR file to a smaller JRE base image
COPY --from=compile "/app/target/weather-api-1.0.0.jar" /usr/share/

ENTRYPOINT ["java", "-jar", "/usr/share/weather-api-1.0.0.jar"]
