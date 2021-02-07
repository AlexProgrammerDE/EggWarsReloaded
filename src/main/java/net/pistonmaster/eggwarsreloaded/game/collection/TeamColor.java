package net.pistonmaster.eggwarsreloaded.game.collection;

import net.md_5.bungee.api.ChatColor;
import com.cryptomorin.xseries.XMaterial;

import java.util.ArrayList;
import java.util.List;

public enum TeamColor {
    WHITE(XMaterial.WHITE_WOOL, ChatColor.WHITE),
    ORANGE(XMaterial.ORANGE_WOOL, ChatColor.GOLD),
    MAGENTA(XMaterial.MAGENTA_WOOL, ChatColor.DARK_PURPLE),
    LIGHT_BLUE(XMaterial.LIGHT_BLUE_WOOL, ChatColor.BLUE),
    YELLOW(XMaterial.YELLOW_WOOL, ChatColor.YELLOW),
    LIME(XMaterial.LIME_WOOL, ChatColor.GREEN),
    PINK(XMaterial.PINK_WOOL, ChatColor.LIGHT_PURPLE),
    GRAY(XMaterial.GRAY_WOOL, ChatColor.DARK_GRAY),
    LIGHT_GRAY(XMaterial.LIGHT_GRAY_WOOL, ChatColor.GRAY),
    CYAN(XMaterial.CYAN_WOOL, ChatColor.AQUA),
    BLUE(XMaterial.BLUE_WOOL, ChatColor.DARK_BLUE),
    GREEN(XMaterial.GREEN_WOOL, ChatColor.DARK_GREEN),
    RED(XMaterial.RED_WOOL, ChatColor.RED),
    BLACK(XMaterial.BLACK_WOOL, ChatColor.BLACK);

    XMaterial material;
    ChatColor color;

    TeamColor(XMaterial material, ChatColor color) {
        this.material = material;
        this.color = color;
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

    public XMaterial getMaterial() {
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
}
