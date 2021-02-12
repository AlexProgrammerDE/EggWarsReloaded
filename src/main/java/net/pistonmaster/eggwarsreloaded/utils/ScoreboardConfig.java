package net.pistonmaster.eggwarsreloaded.utils;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ScoreboardConfig {
    @Getter
    private final String header;
    @Getter
    private final List<String> top;
    @Getter
    private final List<String> bottom;

    public ScoreboardConfig(FileConfiguration config, String str) {
        header = config.getString(str + ".header");
        top = config.getStringList(str + ".top");
        bottom = config.getStringList(str + ".bottom");
    }
}
