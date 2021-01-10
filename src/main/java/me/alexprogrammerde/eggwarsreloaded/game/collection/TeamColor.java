package me.alexprogrammerde.eggwarsreloaded.game.collection;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public enum TeamColor {
    WHITE(Material.WHITE_WOOL),
    ORANGE(Material.ORANGE_WOOL),
    MAGENTA(Material.MAGENTA_WOOL),
    LIGHT_BLUE(Material.LIGHT_BLUE_WOOL),
    YELLOW(Material.YELLOW_WOOL),
    LIME(Material.LIME_WOOL),
    PINK(Material.PINK_WOOL),
    GRAY(Material.GRAY_WOOL),
    LIGHT_GRAY(Material.LIGHT_GRAY_WOOL),
    CYAN(Material.CYAN_WOOL),
    PURPLE(Material.PURPLE_WOOL),
    BLUE(Material.BLUE_WOOL),
    BROWN(Material.BROWN_WOOL),
    GREEN(Material.GREEN_WOOL),
    RED(Material.RED_WOOL),
    BLACK(Material.BLACK_WOOL);
    
    Material material;
    
    TeamColor(Material material) {
        this.material = material;
    }
    
    public Material getMaterial() {
        return material;
    }

    public ChatColor getColor() {
        return ChatColor.of(name());
    }

    @Override
    public String toString() {
        return name().toLowerCase().replaceAll("_", "");
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
            if (str.toLowerCase().equals(color.toString())) {
                return color;
            }
        }

        return TeamColor.WHITE;
    }

    public static List<String> toStringList(List<TeamColor> list) {
        if (list == null) {
            return null;
        } else {
            return new ArrayList<String>() {
                {
                    for (TeamColor color : list) {
                        add(color.toString());
                    }
                }
            };
        }
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
