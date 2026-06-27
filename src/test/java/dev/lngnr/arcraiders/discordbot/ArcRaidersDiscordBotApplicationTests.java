package dev.lngnr.arcraiders.discordbot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

import dev.lngnr.arcraiders.discordbot.configuration.JDATestConfiguration;

@SpringBootTest(classes = JDATestConfiguration.class)
class ArcRaidersDiscordBotApplicationTests {

    @Test
    void modules() {
        ApplicationModules.of(ArcRaidersDiscordBotApplication.class).verify();
    }

}
