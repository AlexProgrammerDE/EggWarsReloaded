package me.alexprogrammerde.EggWarsReloaded.game.collection;

import org.bukkit.Material;

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

    @Override
    public String toString() {
        return name().toLowerCase().replaceAll("_", "");
    }

    public static TeamColor fromString(String str) {
        for (TeamColor color : TeamColor.values()) {
            if (str.toLowerCase().equals(color.toString())) {
                return color;
            }
        }

        return TeamColor.WHITE;
    }
}
