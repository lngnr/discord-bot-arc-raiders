package dev.lngnr.arcraiders.discordbot.arc.events.internal.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.micrometer.tracing.annotation.NewSpan;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class EventWebRepository {

    private final WebClient client;

    @Value("${arc-raiders.api.url.events}")
    private String url;

    @NewSpan("eventWebRepositoty.fetchEvents")
    @Cacheable(value = "eventsCache")
    public List<Event> findEvents() {
        final EventsResponse response = client.get()
                .uri(url)
                .retrieve()
                .bodyToMono(EventsResponse.class)
                .block();

        log.debug("Found {} events", response.getData().size());

        return response.getData();
    }

    private static class EventsResponse extends ApiCollectionResponse<Event> {

        @JsonCreator
        public EventsResponse(@JsonProperty("data") List<Event> data) {
            super(data);
        }
    }
}
