FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=CFGames_bot
ENV BOT_TOKEN=1887804860:AAFAI8p1-t6WWwKeyJdHNXa_8du2rRx32Js
ENV BOT_DB_USERNAME=postgres
ENV BOT_DB_PASSWORD=postgres
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.datasource.password=${BOT_DB_PASSWORD}", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-Dspring.datasource.username=${BOT_DB_USERNAME}", "-jar", "app.jar"]