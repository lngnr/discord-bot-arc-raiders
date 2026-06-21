package dev.lngnr.arcraiders.discordbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ArcRaidersDiscordBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArcRaidersDiscordBotApplication.class, args);
    }

}
