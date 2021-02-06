package net.pistonmaster.eggwarsreloaded.game;

import net.pistonmaster.eggwarsreloaded.game.collection.TeamColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        Scoreboard playerScoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();

        Objective objective = playerScoreboard.registerNewObjective("scoreboard", "dummy", ChatColor.YELLOW + "" + ChatColor.BOLD + "Egg Wars");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<String> text = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        text.add(ChatColor.GRAY + dtf.format(LocalDateTime.now()) + "");

        text.add("");

        for (TeamColor team : teams) {
            String var1 = game.matchmaker.getTeamOfPlayer(player).equals(team) ? ChatColor.DARK_GRAY + " (You)" : "";
            String var2 = game.gameLogics.isTeamDead(team) ? ChatColor.RED + "✗" : ChatColor.GREEN + "✓";

            text.add(team.getColor() + "" + team.getFirstLetter() + ChatColor.RESET + " " + team.getCapitalized() + ": " + var2 + var1);
        }

        text.add(" ");

        text.add(ChatColor.YELLOW + "pistonmaster.net");

        int i = text.size();

        for (String str : text) {
            objective.getScore(str).setScore(i);
            i--;
        }

        player.setScoreboard(playerScoreboard);
    }
}
