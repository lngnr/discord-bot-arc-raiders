package dev.lngnr.arcraiders.discordbot.discord.internal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import dev.lngnr.arcraiders.discordbot.discord.CommandHandler;
import dev.lngnr.arcraiders.discordbot.discord.CommandMapping;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

/**
 * A context that loads all command handlers from th classpath and registers the
 * corresponding discord commands.
 *
 * @author Jonas Langner
 * @version 0.1.0
 * @since 2026-06-20
 */
@Log4j2
@Component
@RequiredArgsConstructor
class CommandHandlerContext implements ApplicationListener<ContextRefreshedEvent> {

    private final JDA jda;
    private final Map<String, CommandHandler> commands = new HashMap<>();

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        final ApplicationContext context = event.getSource();
        log.debug("Scanning application context for discord command handlers.");

        final Map<String, CommandHandler> handlers = context.getBeansOfType(CommandHandler.class);
        log.debug("Found {} possible command handlers", handlers.size());
        handlers.values().forEach(commandHandler -> {
            final CommandMapping mapping = AnnotationUtils.findAnnotation(commandHandler.getClass(),
                    CommandMapping.class);

            if (mapping == null) {
                log.warn(
                        "{} has no command metadata (commandmapping). Therefore skipping discord command initialization.",
                        commandHandler.getClass().getName());
                return;
            }

            final String commandName = mapping.name().toLowerCase();
            if (commandName.isBlank()) {
                log.warn(
                        "{} has no command name. Therefore skipping discord command initialization.",
                        commandHandler.getClass().getName());

                return;
            }

            if (commands.containsKey(commandName)) {
                log.warn("Command \"{}\" does already exist. Skipping initialization", commandName);
            }

            final SlashCommandData command = Commands.slash(commandName, mapping.description());

            Arrays.stream(mapping.options())
                    .filter(option -> !option.name().isBlank())
                    .forEach(option -> command.addOption(
                            option.type(),
                            option.name().toLowerCase(),
                            option.description(),
                            option.required()));

            log.debug("Build new command \"{}\" from classpath", commandName);
            jda.updateCommands()
                    .addCommands(command)
                    .queue(success -> {
                        log.info("Added new comand \"{}\"", command.getName());
                    }, failure -> log.error("Could not add command \"{}\"", command.getName(), failure));
            commands.put(commandName, commandHandler);

        });
    }

    /**
     * Looks for a command handler.
     *
     * @param command The command to be handled.
     * @return The command handler for the specified command.
     */
    @NonNull
    public Optional<CommandHandler> getHandlerForCommand(@NonNull final String command) {
        return Optional.ofNullable(commands.get(command.toLowerCase()));
    }

}
