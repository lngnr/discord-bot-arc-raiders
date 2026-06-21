package dev.lngnr.arcraiders.discordbot.discord;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDA;

/**
 * The discord worker.
 *
 * @author Jonas Langner
 * @version 0.1.0
 * @since 2026-06-20
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class DiscordBotWorker implements CommandLineRunner {

    private final JDA jda;

    @Override
    public void run(String... args) {
        try {
            jda.awaitReady();
            log.info("Bot is online and ready");
        } catch (InterruptedException e) {
            log.error("Error starting bot", e);
        }
    }

}
