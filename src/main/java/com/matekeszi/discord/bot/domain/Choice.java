package com.matekeszi.discord.bot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@Builder
public class Choice {

    private final String name;
    private final String value;
}
