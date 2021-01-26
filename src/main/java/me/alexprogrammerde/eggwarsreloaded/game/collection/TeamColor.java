package me.alexprogrammerde.eggwarsreloaded.game.collection;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public enum TeamColor {
    WHITE(Material.WHITE_WOOL, ChatColor.WHITE),
    ORANGE(Material.ORANGE_WOOL, ChatColor.GOLD),
    MAGENTA(Material.MAGENTA_WOOL, ChatColor.DARK_PURPLE),
    LIGHT_BLUE(Material.LIGHT_BLUE_WOOL, ChatColor.BLUE),
    YELLOW(Material.YELLOW_WOOL, ChatColor.YELLOW),
    LIME(Material.LIME_WOOL, ChatColor.GREEN),
    PINK(Material.PINK_WOOL, ChatColor.LIGHT_PURPLE),
    GRAY(Material.GRAY_WOOL, ChatColor.DARK_GRAY),
    LIGHT_GRAY(Material.LIGHT_GRAY_WOOL, ChatColor.GRAY),
    CYAN(Material.CYAN_WOOL, ChatColor.AQUA),
    BLUE(Material.BLUE_WOOL, ChatColor.DARK_BLUE),
    GREEN(Material.GREEN_WOOL, ChatColor.DARK_GREEN),
    RED(Material.RED_WOOL, ChatColor.RED),
    BLACK(Material.BLACK_WOOL, ChatColor.BLACK);
    
    Material material;
    ChatColor color;
    
    TeamColor(Material material, ChatColor color) {
        this.material = material;
        this.color = color;
    }
    
    public Material getMaterial() {
        return material;
    }

    public ChatColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        return name().toLowerCase().replace("_", "");
    }

    public String getCapitalized() {
        StringBuilder builder = new StringBuilder();

        for (String str : toString().split(" ")) {
            builder.append(capitalize(str));
            builder.append(" ");
        }

        builder.deleteCharAt(builder.lastIndexOf(" "));

        return builder.toString();
    }

    public char getFirstLetter() {
        return name().charAt(0);
    }

    public static TeamColor fromString(String str) {
        for (TeamColor color : TeamColor.values()) {
            if (str.equalsIgnoreCase(color.toString())) {
                return color;
            }
        }

        return TeamColor.WHITE;
    }

    public static List<String> toStringList(List<TeamColor> list) {
        if (list == null) {
            return null;
        } else {
            List<String> l = new ArrayList<>();

            for (TeamColor color : list) {
                l.add(color.toString());
            }

            return l;
        }
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
