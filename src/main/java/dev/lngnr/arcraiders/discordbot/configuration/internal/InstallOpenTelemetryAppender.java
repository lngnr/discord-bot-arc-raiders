package dev.lngnr.arcraiders.discordbot.configuration.internal;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
class InstallOpenTelemetryAppender implements InitializingBean {

    private final OpenTelemetry openTelemetry;

    @Override
    public void afterPropertiesSet() throws Exception {
        OpenTelemetryAppender.install(openTelemetry);
    }

}
