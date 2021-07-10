package com.matekeszi.discord.bot.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matekeszi.discord.bot.command.*;
import com.matekeszi.discord.bot.domain.Permission;
import lombok.experimental.UtilityClass;
import net.dv8tion.jda.api.entities.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class CommandUtil {

    private final Map<String, RunnableCommand> commands = new HashMap<>();

    public static Map<String, RunnableCommand> getCommands() {
        return commands;
    }

    public static void initialize() {
        ObjectMapper objectMapper = new ObjectMapper();

        InfoCommand infoCommand = InfoCommand.infoCommand();
        EchoCommand echoCommand = EchoCommand.echoCommand();
        UserCommand userCommand = UserCommand.userCommand();
        PermissionCommand permissionCommand = PermissionCommand.permissionCommand();

        commands.put(infoCommand.getName(), infoCommand);
        commands.put(echoCommand.getName(), echoCommand);
        commands.put(userCommand.getName(), userCommand);
        commands.put(permissionCommand.getName(), permissionCommand);

        commands.values().forEach(x-> {
            try {
                System.out.println(objectMapper.writeValueAsString(x));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
