package dev.lngnr.arcraiders.discordbot.arc.commands.events.internal.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.lngnr.arcraiders.discordbot.arc.commands.events.internal.data.Event;
import dev.lngnr.arcraiders.discordbot.arc.commands.events.internal.data.EventWebRepository;
import io.micrometer.tracing.annotation.NewSpan;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventsService {

    private final EventWebRepository repository;

    @NewSpan(name = "eventService.findEvents")
    public List<Event> getEvents() {
        return sortByNext(dropExpired(repository.findEvents()));
    }

    @NonNull
    private List<Event> dropExpired(final @NonNull List<Event> events) {
        final Instant now = Instant.now();

        return events.stream()
                .filter(e -> !e.getEnd().isBefore(now))
                .toList();
    }

    @NonNull
    private List<Event> sortByNext(final @NonNull List<Event> events) {
        return events.stream()
                .sorted((a, b) -> {
                    if (a.getStart().equals(b.getStart()))
                        return a.getName().compareTo(b.getName());
                    if (a.getStart().isBefore(b.getStart()))
                        return -1;
                    return 1;
                })
                .toList();
    }

}
