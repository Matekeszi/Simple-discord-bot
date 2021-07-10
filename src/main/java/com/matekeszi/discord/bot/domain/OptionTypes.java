package com.matekeszi.discord.bot.domain;

public enum OptionTypes {

    SUB_COMMAND(1),
    SUB_COMMAND_GROUP(2),
    STRING(3),
    INTEGER(4),
    BOOLEAN(5),
    USER(6),
    CHANNEL(7),
    ROLE(8),
    MENTIONABLE(9);

    private final long value;

    private OptionTypes(long value) {
        this.value = value;
    }

    public static OptionTypes getByName(String typeName) {
        for (OptionTypes type : values()) {
            if (type.name().toLowerCase().equals(typeName.toLowerCase())) {
                return type;
            }
        }
        return null;
    }

    public long getValue() {
        return this.value;
    }

}
