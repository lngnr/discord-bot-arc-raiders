package dev.lngnr.arcraiders.discordbot.discord.boundary;

import java.time.Instant;

import org.springframework.stereotype.Component;

import lombok.NonNull;

@Component
public class DiscordTimestampFactory {

    @NonNull
    public String fromInstant(final @NonNull Instant instant) {
        return "<t:" + instant.getEpochSecond() + ">";
    }

}
