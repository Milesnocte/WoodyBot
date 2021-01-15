package Managers;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.awt.*;
import java.io.IOException;
import Main.*;


public class CommandManager {
    public CommandManager(GuildMessageReceivedEvent event, String[] args) throws Exception {

        String prefix = "$";
        DataBase db = new DataBase();
        try { prefix = db.getPrefix("" + event.getGuild().getIdLong());
        } catch (Exception e) {e.printStackTrace();}
        Permission editChannel = Permission.MANAGE_CHANNEL;

        if(args[0].toLowerCase().equals("prefix")) {
            try {
                event.getChannel().sendMessage("`" + db.getPrefix("" + event.getGuild().getIdLong()) + "` is my prefix!").queue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(args[0].toLowerCase().equals("setprefix") && event.getMember().getPermissions().contains(Permission.MANAGE_ROLES)) {
            try {
                if (args[1].length() <= 2) {
                    try {
                        db.updateGuildPrefix("" + event.getGuild().getIdLong(), "" + args[1]);
                        event.getChannel().sendMessage("`" + args[1] + "` is now my prefix!").queue();
                    } catch (Exception e) {event.getChannel().sendMessage("Please specify a prefix").queue();}
                } else {
                    event.getChannel().sendMessage("`" + args[1] + "` is too long, max is 2 characters!").queue();
                }
            }catch (Exception e){
                event.getChannel().sendMessage("Please specify a prefix").queue();
            }

        }else if(args[0].toLowerCase().equals("setprefix") && !event.getMember().getPermissions().contains(Permission.MANAGE_ROLES)){
            event.getChannel().sendMessage("You do not have enough permission to run this command!").queue();
        }

        if(args[0].toLowerCase().equals("invite")) {

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.cyan);
            embed.addField("**Invite To Server**","[Link](https://discord.com/api/oauth2/authorize?client_id=776643673265012766&permissions=268446736&scope=bot)", false);
            event.getChannel().sendMessage(embed.build()).queue();

        }

        if(args[0].toLowerCase().equals("stats")) {

            long currentTime = System.currentTimeMillis();
            String command ="ping 8.8.8.8";
            Process process = Runtime.getRuntime().exec(command);
            process.getOutputStream().toString();
            currentTime = System.currentTimeMillis() - currentTime;

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.cyan);
            embed.setDescription("**VoiceChannelPlus By MilesNocte**");
            int trim = event.getJDA().getRateLimitPool().toString().indexOf("[");
            embed.addField("**Statistics**","`Ping:` " + currentTime + "ms" +
                    "\n`Guilds:` " + event.getJDA().getGuilds().size() +
                    "\n`Bot ID:` " + event.getJDA().getSelfUser().getId() +
                    "\n`Rate Pool:` \n" + event.getJDA().getRateLimitPool().toString().substring(trim), false);
            event.getChannel().sendMessage(embed.build()).queue();

        }

        if(args[0].toLowerCase().equals("addchannel") && event.getMember().getPermissions().contains(editChannel)) {

            if(!event.getGuild().getSelfMember().getPermissions().contains(Permission.MANAGE_CHANNEL)){
                event.getChannel().sendMessage("I am missing the `MANAGE_CHANNEL` permission! Please add this permission and try again!").queue();
            }

            if(!event.getGuild().getSelfMember().getPermissions().contains(Permission.MANAGE_ROLES)){
                event.getChannel().sendMessage("I am missing the `MANAGE_ROLES` permission! Please add this permission and run the $addrole command!").queue();
            }

            String channelId = args[1].replace("<", "")
                    .replace("#", "").replace(">", "");
            TextChannel channel = event.getGuild().getTextChannelById(channelId);
            Role inVoiceChannel = event.getGuild().getRolesByName("InVoiceChannel", true).get(0);
            event.getGuild().addRoleToMember(event.getGuild().getSelfMember(), inVoiceChannel).queue();

            try{
                channel.createPermissionOverride(inVoiceChannel).setAllow(Permission.MESSAGE_WRITE, Permission.MESSAGE_READ, Permission.MESSAGE_EMBED_LINKS
                        , Permission.MESSAGE_ADD_REACTION, Permission.MESSAGE_EXT_EMOJI, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_HISTORY).queue();
            }catch (Exception e){
                System.out.println("Override already exists, upsert new role");
                channel.upsertPermissionOverride(inVoiceChannel).setAllow().setAllow(Permission.MESSAGE_WRITE, Permission.MESSAGE_READ, Permission.MESSAGE_EMBED_LINKS
                        , Permission.MESSAGE_ADD_REACTION, Permission.MESSAGE_EXT_EMOJI, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_HISTORY).queue();
            }

            event.getChannel().sendMessage(event.getGuild().getTextChannelById(channelId).getAsMention() +
                    " is now a Voice Text Channel!").queue();

            channel.upsertPermissionOverride(event.getGuild().getPublicRole()).deny(Permission.MESSAGE_WRITE, Permission.MESSAGE_READ).queue();

        }else if(args[0].toLowerCase().equals("addchannel") && !event.getMember().getPermissions().contains(editChannel)){
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + " you do not have the MANAGE_CHANNEL permission!").queue();

        }

        if(args[0].toLowerCase().equals("help")) {

            if(!event.getGuild().getSelfMember().getPermissions().contains(Permission.MANAGE_CHANNEL)){
                event.getChannel().sendMessage("I am missing the `MANAGE_CHANNEL` permission! The $addchannel command wont work without this perm!").queue();
            }

            if(!event.getGuild().getSelfMember().getPermissions().contains(Permission.MANAGE_ROLES)){
                event.getChannel().sendMessage("I am missing the `MANAGE_ROLES` permission! Please add this permission and run the $addrole command!").queue();
            }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.cyan);
            embed.setDescription("VoiceTextChannel, use `" + prefix + "help` to show this message again!\n[Bot demonstration video](https://www.youtube.com/watch?v=Y-7tUBZHQFA&feature=youtu.be) ");
            embed.addField("__" + prefix + "prefix or @the bot__", "Will return the prefix the bot is using in this server\n", false);
            embed.addField("__" + prefix + "setprefix__","Will set the prefix the bot uses, requires the manage roles permission\n", false);
            embed.addField("__" + prefix + "addchannel {TextChannelMention}__","Set the channel as a voice text channel, requires the manage channel permission\n", false);
            embed.addField("__" + prefix + "addrole__","Will create the inVoiceChannel role required for the bot to function, requires the manage roles permission", false);
            event.getChannel().sendMessage(embed.build()).queue();

        }

        if(args[0].toLowerCase().equals("reactrole")) {
            ReactRoles.createMenu(event);
        }
        if(args[0].toLowerCase().equals("ccievents")) {
            CCIEvents.createMenu(event);
        }

    }
}