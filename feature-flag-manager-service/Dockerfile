#
# Build stage
#
FROM amazoncorretto:17-alpine-jdk AS build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN --mount=type=cache,target=/root/.m2 ./mvnw -f $HOME/pom.xml clean package -DskipTests

#
# Package stage
#
FROM amazoncorretto:17-alpine-jdk
ARG JAR_FILE=/usr/app/target/*.jar
COPY --from=build $JAR_FILE /app/runner.jar
EXPOSE 80
ENTRYPOINT java -jar /app/runner.jar