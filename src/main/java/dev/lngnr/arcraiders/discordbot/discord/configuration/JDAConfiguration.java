package dev.lngnr.arcraiders.discordbot.discord.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

/**
 * Initializes the discord bot.
 *
 * @author Jonas Langner
 * @version 0.1.0
 * @since 2026-06-20
 */
@Log4j2
@Configuration
public class JDAConfiguration {

    @Value("${discord.bot-token}")
    private String discordBotToken;

    @Bean
    public JDA jda() {
        if (discordBotToken == null) {
            log.error("Dicord bot token is null");
            throw new IllegalArgumentException("Discord bot token is null");
        }

        return JDABuilder.createDefault(discordBotToken)
                .build();
    }

}
