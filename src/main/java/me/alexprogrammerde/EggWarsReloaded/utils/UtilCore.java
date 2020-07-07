package me.alexprogrammerde.EggWarsReloaded.utils;

import javafx.beans.property.Property;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

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
}
