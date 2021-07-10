package com.matekeszi.discord.bot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Option {

    private final long type;
    private final String name;
    private final String description;
    private final boolean required;
    private final List<Choice> choices;
    private final List<Option> options;
}
