package dev.lngnr.arcraiders.discordbot.boundary;

import dev.lngnr.arcraiders.discordbot.discord.boundary.CommandHandler;
import dev.lngnr.arcraiders.discordbot.discord.boundary.CommandMapping;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@CommandMapping(name = "demo")
public class DemoCommandHandler implements CommandHandler {

    private int calls = 0;

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        ++calls;
    }

    public int getCalls() {
        return calls;
    }

}
