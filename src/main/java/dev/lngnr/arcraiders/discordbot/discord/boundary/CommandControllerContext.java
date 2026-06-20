package dev.lngnr.arcraiders.discordbot.discord.boundary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

@Log4j2
@Component
public class CommandControllerContext implements ApplicationListener<ContextRefreshedEvent> {

    private final Map<String, Command> commands = new HashMap<>();

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        final ApplicationContext context = event.getApplicationContext();
        final Map<String, Object> beans = context.getBeansWithAnnotation(CommandController.class);

        beans.values().forEach(controller -> {
            Class<?> clazz = controller.getClass();
            log.debug("Parsing command mappings from {}", clazz.getName());

            Arrays.stream(clazz.getMethods()).forEach(method -> {
                if (!method.isAnnotationPresent(CommandMapping.class))
                    return;
                final CommandMapping mapping = method.getAnnotation(CommandMapping.class);
                final String command = mapping.value();

                if (command == null || command.isBlank()) {
                    log.warn(
                            "Cannot handle command mapping for {} due to empty command specification.",
                            method.getName());
                    return;
                }

                final List<Parameter> parameters = new ArrayList<>();

                Arrays.stream(method.getParameters()).forEach(parameter -> {
                    if (parameter.isAnnotationPresent(CommandParameter.class)) {
                        final String name = parameter.getAnnotation(CommandParameter.class).value();
                        if (name == null || name.isBlank()) {
                            log.warn(
                                    "Cannot parse parameter name for parameter {} of method {}", parameter.getName(),
                                    method.getName());
                        } else {
                            parameters.add(new Parameter(name.toLowerCase(), parameter.getType()));
                            return;
                        }
                    }

                    parameters.add(new Parameter(parameter.getName().toLowerCase(), parameter.getType()));
                });

                registerCommand(command, parameters, method, controller);
            });
        });
    }

    private void registerCommand(
            final String name,
            final List<Parameter> parameters,
            final Method handler,
            final Object bean) {
        final Command command = new Command(name, parameters.stream().map(Parameter::name).toArray(String[]::new),
                event -> {
                    if (event.getName() == null || event.getName().isBlank()) {
                        log.warn("Cannot execute command handler for empty command");
                    }

                    final List<Object> args = parameters.stream()
                            .map(parameter -> getCommandOption(event.getOption(parameter.name()), parameter.type()))
                            .toList();

                    try {
                        handler.invoke(bean, args.toArray());
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });

        commands.put(name, command);
    }

    private Object getCommandOption(final OptionMapping option, final Class<?> type) {
        // TODO: other mappings

        return switch (type) {

            default -> option.getAsString();
        };
    }

    private static record Parameter(String name, Class<?> type) {
    }

}
