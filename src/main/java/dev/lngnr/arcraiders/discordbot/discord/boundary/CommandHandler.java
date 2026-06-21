package dev.lngnr.arcraiders.discordbot.discord.boundary;

import lombok.NonNull;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * A command handler will handle specific discord commands.
 * Needs a CommandMapping annotation to know which commands should be handled.
 *
 * @author Jonas Langner
 * @version 0.1.0
 * @since 2026-06-21
 */
public interface CommandHandler {

    void handle(@NonNull SlashCommandInteractionEvent event);

}
