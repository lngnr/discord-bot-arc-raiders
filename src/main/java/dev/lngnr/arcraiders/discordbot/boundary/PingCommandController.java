package dev.lngnr.arcraiders.discordbot.boundary;

import dev.lngnr.arcraiders.discordbot.discord.boundary.CommandHandler;
import dev.lngnr.arcraiders.discordbot.discord.boundary.CommandMapping;
import dev.lngnr.arcraiders.discordbot.discord.boundary.CommandMapping.CommandOptionSpecification;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

@CommandMapping(name = "ping", options = {
        @CommandOptionSpecification(name = "test", type = OptionType.STRING) })
public class PingCommandController implements CommandHandler {

    @Override
    public void handle(final SlashCommandInteractionEvent event) {
        event.reply("Pong! " + event.getOption("test").getAsString()).queue();
    }

}
