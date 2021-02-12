package net.pistonmaster.eggwarsreloaded.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"unused"})
public class ScoreboardBuilder {
    private final List<String> texts = new LinkedList<>();
    private final Player player;
    private DisplaySlot slot = DisplaySlot.SIDEBAR;
    private String displayName = ChatColor.YELLOW + "" + ChatColor.BOLD + "Egg Wars";

    public ScoreboardBuilder(Player player) {
        this.player = player;
    }

    /**
     * Add lines from the top to the bottom.
     *
     * @param text Line to add.
     */
    public ScoreboardBuilder addLine(String text) {
        texts.add(PlaceholderParser.parse(text, player));
        return this;
    }

    /**
     * Set where it should be displayed.
     *
     * @param slot Where to display it.
     */
    public ScoreboardBuilder slot(DisplaySlot slot) {
        this.slot = slot;
        return this;
    }

    /**
     * Set the line at the top.
     *
     * @param displayName Line to set at the top.
     */
    public ScoreboardBuilder displayName(String displayName) {
        this.displayName = PlaceholderParser.parse(displayName, player);
        return this;
    }

    /**
     * Create the scoreboard you described!
     *
     * @return The created scoreboard.
     */
    public Scoreboard build() {
        Scoreboard playerScoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();

        Objective objective = playerScoreboard.registerNewObjective("scoreboard", "dummy", displayName);

        objective.setDisplaySlot(slot);

        int i = texts.size();

        for (String str : texts) {
            objective.getScore(str).setScore(i);
            i--;
        }

        return playerScoreboard;
    }
}
