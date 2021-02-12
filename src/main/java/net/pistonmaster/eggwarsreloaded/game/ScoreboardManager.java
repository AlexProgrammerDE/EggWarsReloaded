package net.pistonmaster.eggwarsreloaded.game;

import net.md_5.bungee.api.ChatColor;
import net.pistonmaster.eggwarsreloaded.game.collection.TeamColor;
import net.pistonmaster.eggwarsreloaded.utils.ScoreboardBuilder;
import net.pistonmaster.eggwarsreloaded.utils.ScoreboardConfig;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardManager {
    private final List<TeamColor> teams = new ArrayList<>();
    private final Game game;

    public ScoreboardManager(Game game) {
        this.game = game;
    }

    public void generateTemplate() {
        teams.addAll(game.matchmaker.getTeams());
    }

    public void setScoreboard(Player player) {
        ScoreboardBuilder builder = new ScoreboardBuilder(player);
        ScoreboardConfig config = new ScoreboardConfig(game.plugin.getScoreboards(), "game");

        builder.displayName(config.getHeader());

        for (String str : config.getTop()) {
            builder.addLine(str);
        }

        for (TeamColor team : teams) {
            String var1 = game.matchmaker.getTeamOfPlayer(player).equals(team) ? ChatColor.DARK_GRAY + " (You)" : "";
            String var2 = game.gameLogics.isTeamDead(team) ? ChatColor.RED + "✗" : ChatColor.GREEN + "✓";

            builder.addLine(team.getColor().toString() + team.getFirstLetter() + ChatColor.RESET + " " + team.getCapitalized() + ": " + var2 + var1);
        }

        for (String str : config.getBottom()) {
            builder.addLine(str);
        }

        player.setScoreboard(builder.build());
    }
}
