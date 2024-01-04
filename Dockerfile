#コンパイル
FROM gradle AS compile

WORKDIR /app
COPY . .
RUN ./gradlew bootJar -i

#Botを起動
FROM amazoncorretto:17 AS bot

WORKDIR /app

COPY --from=compile /app/build/libs .

CMD ["java", "-jar", "SimpleTimerApi-0.0.1-SNAPSHOT.jar"]