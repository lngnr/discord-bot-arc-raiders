package dev.lngnr.arcraiders.discordbot;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ArcRaidersDiscordBotApplicationTests {

    @Test
    void modules() {
        ApplicationModules.of(ArcRaidersDiscordBotApplication.class).verify();
    }

}
