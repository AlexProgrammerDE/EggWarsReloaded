package me.alexprogrammerde.eggwarsreloaded.hooks;

import me.alexprogrammerde.eggwarsreloaded.EggWarsReloaded;
import me.alexprogrammerde.eggwarsreloaded.utils.LeaderboardManager;
import me.alexprogrammerde.eggwarsreloaded.utils.StatsManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class EggwarsExpansion extends PlaceholderExpansion {
    private final EggWarsReloaded plugin;

    public EggwarsExpansion(EggWarsReloaded plugin) {
        this.plugin = plugin;
    }

    /**
     * This method should always return true unless we
     * have a dependency we need to make sure is on the server
     * for our placeholders to work!
     *
     * @return always true since we do not have any dependencies.
     */
    @Override
    public boolean canRegister() {
        return true;
    }

    /**
     * The name of the person who created this expansion should go here.
     *
     * @return The name of the author as a String.
     */
    @Override
    public @NotNull String getAuthor() {
        return "AlexProgrammerDE";
    }

    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest
     * method to obtain a value if a placeholder starts with our
     * identifier.
     * <br>The identifier has to be lowercase and can't contain _ or %
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public @NotNull String getIdentifier() {
        return "eggwarsreloaded";
    }

    /**
     * This is the version of this expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     *
     * @return The version as a String.
     */
    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    /**
     * This is the method called when a placeholder with our identifier
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
     *
     * @param player     A {@link org.bukkit.OfflinePlayer OfflinePlayer}.
     * @param identifier A String containing the identifier/value.
     * @return Possibly-null String of the requested identifier.
     */
    @Override
    public String onRequest(OfflinePlayer player, @NotNull String identifier) {
        if (player != null && StatsManager.isValidStat(identifier)) {
            return String.valueOf(StatsManager.getStat(player, identifier));
        }

        String[] split = identifier.split("_");

        if (split.length > 2
                && (split[2].equalsIgnoreCase("name") || split[2].equalsIgnoreCase("value"))
                && StatsManager.isValidStat(split[1])) {
            if (split[0].equalsIgnoreCase("top1")) {
                return getProperThing(LeaderboardManager.getTop1(split[1]), split[2]);
            } else if (split[0].equalsIgnoreCase("top2")) {
                return getProperThing(LeaderboardManager.getTop2(split[1]), split[2]);
            } else if (split[0].equalsIgnoreCase("top3")) {
                return getProperThing(LeaderboardManager.getTop3(split[1]), split[2]);
            }
        }

        // We return null if an invalid placeholder (f.e. %example_placeholder3%)
        // was provided
        return null;
    }

    private String getProperThing(Map.Entry<OfflinePlayer, Integer> entry, String str) {
        if (str.equalsIgnoreCase("name")) {
            if (entry == null)
                return "None";

            return entry.getKey().getName();
        } else {
            if (entry == null)
                return "0";

            return String.valueOf(entry.getValue());
        }
    }
}
