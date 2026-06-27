package dev.lngnr.arcraiders.discordbot.arc.events.internal.data;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class Event implements Serializable {

    private final String name;
    private final String mapName;
    private final String iconUrl;
    private final Instant start;
    private final Instant end;

    @JsonCreator
    public Event(
            final @JsonProperty("name") String name,
            final @JsonProperty("map") String mapName,
            final @JsonProperty("icon") String iconUrl,
            final @JsonProperty("startTime") long start,
            final @JsonProperty("endTime") long end) {
        this.name = name;
        this.mapName = mapName;
        this.iconUrl = iconUrl;
        this.start = Instant.ofEpochMilli(start);
        this.end = Instant.ofEpochMilli(end);
    }

    public boolean isNow() {
        final Instant now = Instant.now();
        if (start.isAfter(now))
            return false;
        if (end.isBefore(now))
            return false;

        return true;
    }

}
