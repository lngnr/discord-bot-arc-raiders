package dev.lngnr.arcraiders.discordbot.configuration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

@TestConfiguration
public class JDATestConfiguration {

    @Bean(name = "jdaTestBean")
    @Primary
    JDA jda() {
        final JDA jda = mock(JDA.class);
        final CommandListUpdateAction updateAction = mock(CommandListUpdateAction.class);
        when(updateAction.addCommands(any(SlashCommandData.class))).thenReturn(updateAction);
        when(jda.updateCommands()).thenReturn(updateAction);
        return jda;
    }

}
