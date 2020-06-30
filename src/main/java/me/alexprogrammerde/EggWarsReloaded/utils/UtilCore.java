package me.alexprogrammerde.EggWarsReloaded.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class UtilCore {

    public static void sendActionBar(Player player, String title) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(title));
    }

    public static void sendTitle(Player player, String title) {
        player.sendTitle(title, null, 1, 2, 1);
    }

    public static void sendTitle(Player player, String title, String subtitle) {
        player.sendTitle(title, subtitle, 1, 2, 1);
    }

    public static Inventory[] addInventory(Inventory[] arr, Inventory inv)
    {
        int i;

        Inventory[] newarr = new Inventory[arr.length + 1];

        for (i = 0; i < arr.length; i++)
            newarr[i] = arr[i];

        newarr[arr.length] = inv;

        return newarr;
    }
}
