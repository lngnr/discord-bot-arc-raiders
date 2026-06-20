package dev.lngnr.arcraiders.discordbot.discord;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import dev.lngnr.arcraiders.discordbot.discord.boundary.CommandDispatcher;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

@Log4j2
@Component
@RequiredArgsConstructor
public class DiscordBotWorker implements CommandLineRunner {

    private final CommandDispatcher commandDispatcher;
    private final Tracer tracer;

    @Value("${discord.bot-token}")
    private String discordBotToken;

    @Override
    public void run(String... args) throws Exception {
        if (discordBotToken == null) {
            log.error("Dicord bot token is null");
            return;
        }

        JDA jda = JDABuilder.createDefault(discordBotToken)
                .addEventListeners(commandDispatcher)
                .build();

        jda.awaitReady();
        log.info("Bot is online and ready");
    }

}
