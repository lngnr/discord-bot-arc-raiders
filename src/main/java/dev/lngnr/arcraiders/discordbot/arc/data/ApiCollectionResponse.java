package dev.lngnr.arcraiders.discordbot.arc.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

public class ApiCollectionResponse<T> {

    @Getter
    private final List<T> data;

    @JsonCreator
    public ApiCollectionResponse(final @JsonProperty("data") List<T> data) {
        this.data = data;
    }

}
