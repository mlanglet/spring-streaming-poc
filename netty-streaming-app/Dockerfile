FROM eclipse-temurin:21-jre-alpine
WORKDIR /
RUN apk add curl --no-cache
COPY ./netty-streaming-app/build/libs/netty-streaming-app.jar app.jar
EXPOSE 8080
USER guest
ENTRYPOINT java \
            -Dfile.encoding=UTF-8 \
            -Xms32m -Xmx64m \
            -jar app.jar
