package dev.lngnr.arcraiders.discordbot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class ArcRaidersDiscordBotApplicationTests {

    @Test
    void modules() {
        ApplicationModules.of(ArcRaidersDiscordBotApplication.class).verify();
    }

}
