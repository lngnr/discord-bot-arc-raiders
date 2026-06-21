package dev.lngnr.arcraiders.discordbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableCaching
@EnableAspectJAutoProxy
@SpringBootApplication
public class ArcRaidersDiscordBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArcRaidersDiscordBotApplication.class, args);
    }

}
