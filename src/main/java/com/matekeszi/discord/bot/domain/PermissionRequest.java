package com.matekeszi.discord.bot.domain;

import com.matekeszi.discord.bot.domain.Permission;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder
@Getter
@Jacksonized
public class PermissionRequest {
    List<Permission> permissions;
}
