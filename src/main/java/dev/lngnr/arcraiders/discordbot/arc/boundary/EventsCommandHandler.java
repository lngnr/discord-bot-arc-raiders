package dev.lngnr.arcraiders.discordbot.arc.boundary;

import java.awt.Color;
import java.util.List;
import java.util.Optional;

import dev.lngnr.arcraiders.discordbot.arc.data.Event;
import dev.lngnr.arcraiders.discordbot.arc.service.EventsService;
import dev.lngnr.arcraiders.discordbot.discord.boundary.CommandHandler;
import dev.lngnr.arcraiders.discordbot.discord.boundary.CommandMapping;
import dev.lngnr.arcraiders.discordbot.discord.boundary.CommandMapping.CommandOptionSpecification;
import dev.lngnr.arcraiders.discordbot.discord.boundary.DiscordTimestampFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

@CommandMapping(name = "events", options = {
        @CommandOptionSpecification(name = "next", type = OptionType.INTEGER, required = false) })
@RequiredArgsConstructor
public class EventsCommandHandler implements CommandHandler {

    private static final int DEFAULT_MAX_RESPONSES = 10;

    private final EventsService service;
    private final DiscordTimestampFactory timestampFactory;

    @Override
    public void handle(final @NonNull SlashCommandInteractionEvent event) {
        final List<Event> events = service.getEvents();

        if (events.isEmpty()) {
            event.reply("It seems like there are no events scheduled.");

            return;
        }

        final Optional<Integer> maxResponses = Optional.ofNullable(event.getOption("next"))
                .map(OptionMapping::getAsInt);
        final int nmbEvents = Math.min(maxResponses.orElse(DEFAULT_MAX_RESPONSES), events.size());

        event.reply("**This are the next " + nmbEvents + " events:**").setEphemeral(true).queue();

        final List<MessageEmbed> responses = events.subList(0, nmbEvents).stream()
                .map(this::buildEventResponse)
                .map(EmbedBuilder::build)
                .toList();

        event.getHook().sendMessageEmbeds(responses).queue();
    }

    @NonNull
    private EmbedBuilder buildEventResponse(final @NonNull Event event) {
        final Color color = event.isNow() ? Color.GREEN : Color.YELLOW;

        final EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(event.getName());
        builder.setColor(color);
        builder.setDescription(
                "From " + timestampFactory.fromInstant(event.getStart()) + " to "
                        + timestampFactory.fromInstant(event.getEnd()) + " on " + event.getMapName() + ".");
        builder.setThumbnail(event.getIconUrl());

        return builder;
    }

}
