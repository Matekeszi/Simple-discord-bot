package com.matekeszi.discord.bot.command;

import com.google.common.collect.Lists;
import com.matekeszi.discord.bot.domain.Option;
import com.matekeszi.discord.bot.domain.OptionTypes;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@SuperBuilder
public class UserCommand extends RunnableCommand {
    public static UserCommand userCommand() {
        return UserCommand.builder()
                .name("user")
                .description("User management command")
                .options(Lists.newArrayList(
                        Option.builder()
                                .name("kick")
                                .description("Kick user")
                                .required(false)
                                .type(OptionTypes.SUB_COMMAND.getValue())
                                .choices(null)
                                .options(Lists.newArrayList(
                                        Option.builder()
                                                .name("user")
                                                .type(OptionTypes.USER.getValue())
                                                .description("User to kick")
                                                .required(true)
                                                .choices(null)
                                                .options(null)
                                                .build(),
                                        Option.builder()
                                                .name("reason")
                                                .type(OptionTypes.STRING.getValue())
                                                .description("Reason of kick")
                                                .required(true)
                                                .choices(null)
                                                .options(null)
                                                .build())
                                ).build(),
                        Option.builder()
                                .name("mute")
                                .description("Mute user")
                                .required(false)
                                .type(OptionTypes.SUB_COMMAND.getValue())
                                .choices(null)
                                .options(Lists.newArrayList(
                                        Option.builder()
                                                .name("user")
                                                .type(OptionTypes.USER.getValue())
                                                .description("User to mute")
                                                .required(true)
                                                .choices(null)
                                                .options(null)
                                                .build(),
                                        Option.builder()
                                                .name("time")
                                                .type(OptionTypes.INTEGER.getValue())
                                                .description("Time to mute the user in milliseconds")
                                                .required(true)
                                                .choices(null)
                                                .options(null)
                                                .build()))
                                .build()))
                .build();
    }

    @Override
    public void invoke(SlashCommandEvent event) {
        switch (Objects.requireNonNull(event.getSubcommandName())) {
            case "kick":
                try {
                    Objects.requireNonNull(event.getOptions().get(0).getAsMember()).kick(event.getOptions().get(1).getAsString()).complete();
                } catch (Exception e) {
                    event.reply(e.getMessage()).setEphemeral(true).complete();
                }
                break;
            case "mute":
                Role mutedRole = Objects.requireNonNull(event.getGuild()).getRolesByName("muted", true).get(0);
                Member member = event.getOptions().get(0).getAsMember();
                if(member == null) {
                    event.reply("No such user. This should not have happened!").setEphemeral(true).complete();
                    break;
                }
                List<Role> roleList = new ArrayList<>(List.copyOf(member.getRoles()));
                if(!roleList.contains(mutedRole)) {
                    roleList.add(mutedRole);
                }
                event.getGuild().modifyMemberRoles(member, roleList).submit();
                event.reply(member.getEffectiveName() + " is muted.").setEphemeral(true).complete();
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                List<Role> roleList = new ArrayList<>(List.copyOf(member.getRoles()));
                                roleList.remove(mutedRole);
                                event.getGuild().modifyMemberRoles(member, roleList).submit();
                            }
                        },
                        event.getOptions().get(1).getAsLong()
                );
                break;
        }
    }
}
