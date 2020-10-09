package me.alexprogrammerde.EggWarsReloaded.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class UtilCore {

    public static void sendActionBar(Player player, String title) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(title));
    }

    public static void sendTitle(Player player, String title) {
        player.sendTitle(title, null, 5, 10, 5);
    }

    public static void sendTitle(Player player, String title, String subtitle) {
        player.sendTitle(title, subtitle, 5, 10, 5);
    }

    public static Inventory[] addInventory(Inventory[] arr, Inventory inv) {
        int i;

        Inventory[] newarr = new Inventory[arr.length + 1];

        for (i = 0; i < arr.length; i++)
            newarr[i] = arr[i];

        newarr[arr.length] = inv;

        return newarr;
    }

    public static Location convertLocation(String str) {
        if (str == null) {
            return null;
        }

        String[] arr = str.split(" ");

        return new Location(Bukkit.getWorld(arr[0]), Double.parseDouble(arr[1]), Double.parseDouble(arr[2]), Double.parseDouble(arr[3]), Float.parseFloat(arr[4]), Float.parseFloat(arr[5]));
    }

    public static String convertString(Location loc) {
        if (loc == null) {
            return null;
        }

        return loc.getWorld().getName() + " " + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " " + loc.getYaw() + " " + loc.getPitch();
    }
}
