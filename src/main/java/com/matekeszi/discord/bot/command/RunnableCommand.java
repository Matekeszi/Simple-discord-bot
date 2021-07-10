package com.matekeszi.discord.bot.command;

import com.matekeszi.discord.bot.domain.Option;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.util.List;

@Getter
@SuperBuilder
public abstract class RunnableCommand {
    protected String name;
    protected String description;
    protected List<Option> options;

    public abstract void invoke(SlashCommandEvent event);

    public RunnableCommand(String name, String description, List<Option> options) {
        this.name = name;
        this.description = description;
        this.options = options;
    }

    public RunnableCommand(RunnableCommand command) {
        this.name = command.getName();
        this.description = command.getDescription();
        this.options = command.getOptions();
    }
}
