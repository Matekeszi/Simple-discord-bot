package com.matekeszi.discord.bot.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.matekeszi.discord.bot.domain.*;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@Getter
@SuperBuilder
public class PermissionCommand extends RunnableCommand {

    @JsonIgnore
    final long USER_COMMAND_ID = 863336955395309598L;

    public static PermissionCommand permissionCommand() {
        return PermissionCommand.builder()
                .name("permission")
                .description("Edit command permissions on your guild")
                .options(Lists.newArrayList(
                        Option.builder()
                                .name("add")
                                .description("Add permission to use \"user\" command for a selected role")
                                .type(OptionTypes.SUB_COMMAND.getValue())
                                .required(false)
                                .options(Lists.newArrayList(
                                        Option.builder()
                                                .name("role")
                                                .type(OptionTypes.ROLE.getValue())
                                                .description("This role will be able to manage users!")
                                                .required(true)
                                                .build()))
                                .choices(null)
                                .build(),
                        Option.builder()
                                .name("remove")
                                .description("Remove permission to use \"user\" command for a selected role")
                                .type(OptionTypes.SUB_COMMAND.getValue())
                                .required(false)
                                .options(Lists.newArrayList(
                                        Option.builder()
                                                .name("role")
                                                .type(OptionTypes.ROLE.getValue())
                                                .description("This role will not be able to manage users!")
                                                .required(true)
                                                .build()))
                                .choices(null)
                                .build()))
                .build();
    }

    @SneakyThrows
    @Override
    public void invoke(SlashCommandEvent event) {
        RestTemplate restTemplate = new RestTemplate();

        ObjectMapper objectMapper = new ObjectMapper();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", System.getenv("token"));

        PermissionRequest permissions = restTemplate.exchange("https://discord.com/api/v8/applications/790496084601602059/guilds/" + Objects.requireNonNull(event.getGuild()).getId() + "/commands/" + USER_COMMAND_ID + "/permissions",
                HttpMethod.GET, new HttpEntity<>(headers), PermissionRequest.class).getBody();
        if(Objects.requireNonNull(permissions).getPermissions().stream().noneMatch(x->x.getId().equals(event.getOptions().get(0).getAsRole().getId()))) {
            Permission permission = Permission.builder()
                    .id(event.getOptions().get(0).getAsRole().getId())
                    .permission(Objects.requireNonNull(event.getSubcommandName()).equals("add"))
                    .type(PermissionTypes.ROLE.getValue())
                    .build();
            permissions.getPermissions().add(permission);
        } else {
            Objects.requireNonNull(permissions).getPermissions().stream().filter(x->x.getId().equals(event.getOptions().get(0).getAsRole().getId())).forEach(x->x.setPermission(Objects.requireNonNull(event.getSubcommandName()).equals("add")));
        }

        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(permissions), headers);

        ResponseEntity<Object> response = restTemplate.exchange("https://discord.com/api/v8/applications/790496084601602059/guilds/" + Objects.requireNonNull(event.getGuild()).getId() + "/commands/" + USER_COMMAND_ID + "/permissions",
                HttpMethod.PUT,entity, Object.class);
        System.out.println(objectMapper.writeValueAsString(response));
        event.reply("Permission set.").setEphemeral(true).complete();
    }


}
