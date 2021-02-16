package net.pistonmaster.eggwarsreloaded.hooks;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import net.pistonmaster.eggwarsreloaded.EggWarsReloaded;
import net.pistonmaster.eggwarsreloaded.api.events.GameJoinEvent;
import net.pistonmaster.eggwarsreloaded.api.events.GameLeaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DiscordSRVHook implements Listener {
    private final EggWarsReloaded plugin;

    public DiscordSRVHook(EggWarsReloaded plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(GameJoinEvent event) {
        DiscordSRV.getPlugin().sendJoinMessage(event.getPlayer(), event.getPlayer().getName() + " joined arena: " + event.getGame().getArenaName());
    }

    @EventHandler
    public void onLeave(GameLeaveEvent event) {
        DiscordSRV.getPlugin().sendJoinMessage(event.getPlayer(), event.getPlayer().getName() + " left arena: " + event.getGame().getArenaName());
    }

    private void sendMessage(String message) {
        for (String str : plugin.getConfig().getStringList("hooks.discordsrv.logchannels")) {
            TextChannel channel = DiscordSRV.getPlugin().getDestinationTextChannelForGameChannelName(str);

            EmbedBuilder builder = new EmbedBuilder();

            channel.sendMessage(builder.build()).queue();
        }
    }

    private boolean isEnabled(String event) {
        return plugin.getConfig().getBoolean("hooks.discordsrv.enabled") && plugin.getConfig().getBoolean("hooks.discordsrv.events." + event);
    }
}
