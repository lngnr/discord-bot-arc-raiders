package dev.lngnr.arcraiders.discordbot.discord.internal;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import dev.lngnr.arcraiders.discordbot.boundary.DemoCommandHandler;
import dev.lngnr.arcraiders.discordbot.configuration.JDATestConfiguration;
import net.dv8tion.jda.api.JDA;

@SpringBootTest(classes = JDATestConfiguration.class)
public class CommandHandlerContextTests {

    @MockitoBean(name = "jda")
    JDA jda;
    @Autowired
    CommandHandlerContext context;

    @Test
    void contextLoads() {
        when(jda.updateCommands()).thenThrow(new IllegalStateException());

        final var handler = context.getHandlerForCommand("demo");
        assertInstanceOf(DemoCommandHandler.class, handler.get());

    }
}
