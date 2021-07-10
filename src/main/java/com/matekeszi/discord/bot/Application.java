package com.matekeszi.discord.bot;

import javax.security.auth.login.LoginException;

import com.matekeszi.discord.bot.listener.MateBotSlashCommandListener;
import com.matekeszi.discord.bot.util.CommandUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Application {

  public static void main(String[] args) {
    JDA jda;
    try {
      jda = JDABuilder.createDefault(System.getenv("token"))
          .setMemberCachePolicy(MemberCachePolicy.ALL)
          .enableIntents(GatewayIntent.GUILD_MEMBERS)
          .setChunkingFilter(ChunkingFilter.ALL)
          .addEventListeners(new MateBotSlashCommandListener())
          .setActivity(Activity.playing("Testing stuff here and there"))
          .build();
      jda.awaitReady();
      CommandUtil.initialize();
    } catch (LoginException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
