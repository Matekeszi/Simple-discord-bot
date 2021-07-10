package com.matekeszi.discord.bot.command;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

@Getter
@SuperBuilder
public class InfoCommand extends RunnableCommand {

    public static InfoCommand infoCommand() {
        return InfoCommand.builder()
                .name("info")
                .description("Some info about the bot")
                .options(null)
                .build();
    }

    @Override
    public void invoke(SlashCommandEvent event) {
        if (event.getChannelType().isGuild()) {
            event.reply("This bot is created by Matekeszi for testing purposes only.")
                    .setEphemeral(true)
                    .complete();
        }
    }
}
