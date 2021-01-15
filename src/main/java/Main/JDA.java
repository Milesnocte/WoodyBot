package Main;

import Listener.JoinListener;
import Listeners.BotEventsListener;
import Listeners.CheatManager;
import Listeners.MessageListener;
import Listeners.VoiceChannelListener;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class JDA extends ListenerAdapter implements EventListener {
    public static void main(String[] args) throws Exception {
        DefaultShardManagerBuilder.create(
                GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_VOICE_STATES
        ).setToken("Nzc2NjQzNjczMjY1MDEyNzY2.X633yQ.vm8fuUh4_ZCZqV7trUoHgpEhWIQ")
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .addEventListeners(
                        new VoiceChannelListener(),
                        new BotEventsListener(),
                        new MessageListener(),
                        new CheatManager(),
                        new ReactRoles(),
                        new CCIEvents(),
                        new JoinListener()
                )
                .setActivity(Activity.watching("for Cheaters"))
                .setRawEventsEnabled(true)
                .build();

        System.out.println("https://discord.com/api/oauth2/authorize?client_id=" + "776643673265012766" + "&permissions=268446736&scope=bot");

        DataBase db = new DataBase();
        db.databaseCycle();
    }
}
