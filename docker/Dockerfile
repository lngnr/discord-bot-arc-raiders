FROM eclipse-temurin:25 AS build

RUN mkdir -p /opt/lngnr/bot
COPY . /opt/lngnr/bot
RUN cd /opt/lngnr/bot && ./gradlew bootJar
RUN mv "$(find /opt/lngnr/bot/build/libs -name *.jar | head -1)" /opt/lngnr/bot.jar

FROM eclipse-temurin:25 AS run

RUN adduser --disabled-password bot
RUN mkdir -p /opt/lngnr/bot
COPY --from=build /opt/lngnr/bot.jar /opt/lngnr/bot/bot.jar
RUN chown -R bot:bot /opt/lngnr/bot

USER bot:bot

ENTRYPOINT [ "java", "-jar", "/opt/lngnr/bot/bot.jar" ]
