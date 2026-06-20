package dev.lngnr.arcraiders.discordbot.discord.boundary;

import java.util.function.Consumer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Command {

    private final String name;
    private final String[] parameters;
    private final Consumer<SlashCommandInteractionEvent> handle;

}
