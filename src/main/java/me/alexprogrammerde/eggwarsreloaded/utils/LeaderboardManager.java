package me.alexprogrammerde.eggwarsreloaded.utils;

import me.alexprogrammerde.eggwarsreloaded.EggWarsReloaded;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class LeaderboardManager {
    private static EggWarsReloaded plugin;

    public static Map<OfflinePlayer, Integer> getTop10(String stat) {
        FileConfiguration config = plugin.getStats();
        Map<OfflinePlayer, Integer> map = new HashMap<>();

        for (String str : config.getKeys(false)) {
            map.put(Bukkit.getOfflinePlayer(UUID.fromString(str)), config.getInt(str + "." + stat));
        }

        Map<OfflinePlayer, Integer> returnedMap = new LinkedHashMap<>();

        for (Map.Entry<OfflinePlayer, Integer> entry : sortByValue(map).entrySet()) {
            returnedMap.put(entry.getKey(), entry.getValue());
        }

        return returnedMap;
    }

    public static @Nullable Map.Entry<OfflinePlayer, Integer> getTop1(String stat) {
        Map<OfflinePlayer, Integer> map = getTop10(stat);

        if (!map.isEmpty()) {
            return (new ArrayList<>(map.entrySet())).get(0);
        } else {
            return null;
        }
    }

    public static @Nullable Map.Entry<OfflinePlayer, Integer> getTop2(String stat) {
        Map<OfflinePlayer, Integer> map = getTop10(stat);

        if (map.size() >= 2) {
            return (new ArrayList<>(map.entrySet())).get(1);
        } else {
            return null;
        }
    }

    public static @Nullable Map.Entry<OfflinePlayer, Integer> getTop3(String stat) {
        Map<OfflinePlayer, Integer> map = getTop10(stat);

        if (map.size() >= 3) {
            return (new ArrayList<>(map.entrySet())).get(2);
        } else {
            return null;
        }
    }

    public void setInstance(EggWarsReloaded plugin) {
        LeaderboardManager.plugin = plugin;
    }

    // function to sort hashmap by values
    private static Map<OfflinePlayer, Integer> sortByValue(Map<OfflinePlayer, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<OfflinePlayer, Integer>> list = new LinkedList<>(hm.entrySet());

        // Sort the list
        list.sort(Map.Entry.comparingByValue());

        Collections.reverse(list);

        // put data from sorted list to hashmap
        Map<OfflinePlayer, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<OfflinePlayer, Integer> aa : list)
            temp.put(aa.getKey(), aa.getValue());

        return temp;
    }
}
