package dev.lngnr.arcraiders.discordbot.discord.boundary;

import org.springframework.stereotype.Component;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.Tracer.SpanInScope;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * A dispatcher that receives discord commands and forwards them to specific
 * command handlers.
 *
 * @author Jonas Langner
 * @version 0.1.0
 * @since 2026-06-20
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class CommandDispatcher extends ListenerAdapter {

    private final JDA jda;
    private final Tracer tracer;
    private final CommandHandlerContext context;

    @PostConstruct
    private void init() {
        jda.addEventListener(this);
    }

    @Override
    public void onSlashCommandInteraction(final SlashCommandInteractionEvent event) {
        Span span = tracer.nextSpan().name("dispatchCommand").start();

        try (final SpanInScope _ = tracer.withSpan(span)) {
            dispatchCommand(event, span);
        } catch (Throwable e) {
            span.error(e);
            throw e;
        } finally {
            span.end();
        }
    }

    private void dispatchCommand(final SlashCommandInteractionEvent event, final Span workerSpan) {
        context.getHandlerForCommand(event.getName())
                .ifPresentOrElse(
                        handler -> {
                            log.debug(
                                    "Found handler {} for command \"{}\". Forwarding Event...",
                                    handler.getClass().getName(), event.getName());

                            Span span = tracer.nextSpan(workerSpan).name(handler.getClass().getName()).start();

                            try (final SpanInScope _ = tracer.withSpan(span)) {
                                handler.handle(event);
                            } catch (Throwable e) {
                                span.error(e);
                                throw e;
                            } finally {
                                span.end();
                            }
                        }, () -> {
                            log.warn("Handler for command \"{}\" not found", event.getName());
                        });
    }
}
