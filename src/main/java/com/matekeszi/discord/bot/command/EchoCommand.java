package com.matekeszi.discord.bot.command;

import com.matekeszi.discord.bot.domain.Option;
import com.matekeszi.discord.bot.domain.OptionTypes;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.util.Collections;

@Getter
@SuperBuilder
public class EchoCommand extends RunnableCommand {

    public static EchoCommand echoCommand() {
        return EchoCommand.builder()
                .name("echo")
                .description("Echoes your message")
                .options(Collections.singletonList(
                        Option.builder()
                                .type(OptionTypes.STRING.getValue())
                                .name("message")
                                .description("This is your message that will be echoed")
                                .required(false)
                                .choices(null)
                                .options(null)
                                .build()))
                .build();
    }

    @Override
    public void invoke(SlashCommandEvent event) {
        event.reply(event.getOptions().get(0).getAsString()).setEphemeral(true).complete();
    }
}
