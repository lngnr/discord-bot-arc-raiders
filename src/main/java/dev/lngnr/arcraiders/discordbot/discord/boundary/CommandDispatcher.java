package dev.lngnr.arcraiders.discordbot.discord.boundary;

import org.springframework.stereotype.Component;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.Tracer.SpanInScope;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Log4j2
@Component
@RequiredArgsConstructor
public class CommandDispatcher extends ListenerAdapter {

    private final Tracer tracer;

    @Override
    public void onSlashCommandInteraction(final SlashCommandInteractionEvent event) {
        Span span = tracer.nextSpan().name("Dispatch command").start();

        try (SpanInScope worker = tracer.withSpan(span)) {
            dispatchCommand(event);
        } catch (Exception e) {
            span.error(e);
            throw e;
        } finally {
            span.end();
        }
    }

    private void dispatchCommand(final SlashCommandInteractionEvent event) {

    }
}
