package dev.lngnr.arcraiders.discordbot.configuration.internal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
class WebClientConfiguration {

    @Bean
    WebClient webClient() {
        return WebClient.create();
    }

}
