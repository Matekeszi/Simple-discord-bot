package com.matekeszi.discord.bot.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Builder
@Jacksonized
public class Permission {
    private boolean permission;
    private final long type;
    private final String id;
}
