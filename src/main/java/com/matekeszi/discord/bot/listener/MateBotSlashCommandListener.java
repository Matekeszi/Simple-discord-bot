package com.matekeszi.discord.bot.listener;

import com.matekeszi.discord.bot.command.RunnableCommand;
import com.matekeszi.discord.bot.util.CommandUtil;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Map;

public class MateBotSlashCommandListener extends ListenerAdapter {

    private int usageCount = 0;

    @Override
    public void onSlashCommand(@Nonnull SlashCommandEvent event) {
        Map<String, RunnableCommand> commands = CommandUtil.getCommands();
        String commandName = event.getName();
        if (!commands.containsKey(commandName)) {
            event.reply("No such command. This should not have happened!").setEphemeral(true).complete();
        } else {
            RunnableCommand command = commands.get(commandName);
            usageCount++;
            command.invoke(event);
        }
    }
}