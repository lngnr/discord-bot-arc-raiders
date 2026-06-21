package dev.lngnr.arcraiders.discordbot.discord.boundary;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

import lombok.NonNull;
import net.dv8tion.jda.api.interactions.commands.OptionType;

/**
 * This annotation specifies the command that will be handled
 * by a CommandHandler.
 *
 * @author Jonas Langner
 * @version 0.1.0
 * @since 2026-06-20
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface CommandMapping {

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface CommandOptionSpecification {

        @NonNull
        String name();

        @NonNull
        OptionType type();

        @NonNull
        String description() default "No further information";

        boolean required() default true;
    }

    @NonNull
    String name();

    @NonNull
    String description() default "No further information";

    @NonNull
    CommandOptionSpecification[] options() default {};

}
