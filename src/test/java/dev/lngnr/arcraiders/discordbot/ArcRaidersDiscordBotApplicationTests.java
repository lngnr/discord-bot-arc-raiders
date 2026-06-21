package dev.lngnr.arcraiders.discordbot;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.lngnr.arcraiders.discordbot.discord.boundary.CommandHandlerContext;

@SpringBootTest
class ArcRaidersDiscordBotApplicationTests {

    @Autowired
    CommandHandlerContext context;

    @Test
    void contextLoads() {
        final var handler = context.getHandlerForCommand("demo");
        assertInstanceOf(DemoCommandHandler.class, handler.get());

    }

}
