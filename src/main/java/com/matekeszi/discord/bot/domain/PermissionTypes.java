package com.matekeszi.discord.bot.domain;


public enum PermissionTypes {

    ROLE(1),
    USER(2);

    private final long value;

    private PermissionTypes(long value) {
        this.value = value;
    }

    public static PermissionTypes getByName(String typeName) {
        for (PermissionTypes type : values()) {
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
