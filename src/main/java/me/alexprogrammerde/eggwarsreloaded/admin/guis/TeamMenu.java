package me.alexprogrammerde.eggwarsreloaded.admin.guis;

import me.alexprogrammerde.eggwarsreloaded.EggWarsReloaded;
import me.alexprogrammerde.eggwarsreloaded.admin.assistants.EggAssistant;
import me.alexprogrammerde.eggwarsreloaded.admin.assistants.GeneratorAssistant;
import me.alexprogrammerde.eggwarsreloaded.admin.assistants.ShopAssistant;
import me.alexprogrammerde.eggwarsreloaded.game.collection.TeamColor;
import me.alexprogrammerde.eggwarsreloaded.utils.ArenaManager;
import me.alexprogrammerde.eggwarsreloaded.utils.ItemBuilder;
import me.alexprogrammerde.eggwarsreloaded.utils.gui.GUI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TeamMenu {
    private static final String PREFIX = ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "SetupAssistant" + ChatColor.GOLD + "] ";

    private TeamMenu() {
    }

    public static void setupTeamMenu(String arenaName, Player player, EggWarsReloaded plugin) {
        FileConfiguration items = plugin.getItems();
        FileConfiguration arenas = plugin.getArenaConfig();

        // Load Data from storage
        ItemStack white = new ItemBuilder(Material.WHITE_WOOL).name(items.getString("items.editteams.white.name")).build();
        ItemStack orange = new ItemBuilder(Material.ORANGE_WOOL).name(items.getString("items.editteams.orange.name")).build();
        ItemStack magenta = new ItemBuilder(Material.MAGENTA_WOOL).name(items.getString("items.editteams.magenta.name")).build();
        ItemStack lightBlue = new ItemBuilder(Material.LIGHT_BLUE_WOOL).name(items.getString("items.editteams.lightblue.name")).build();
        ItemStack yellow = new ItemBuilder(Material.YELLOW_WOOL).name(items.getString("items.editteams.yellow.name")).build();
        ItemStack lime = new ItemBuilder(Material.LIME_WOOL).name(items.getString("items.editteams.lime.name")).build();
        ItemStack pink = new ItemBuilder(Material.PINK_WOOL).name(items.getString("items.editteams.pink.name")).build();
        ItemStack gray = new ItemBuilder(Material.GRAY_WOOL).name(items.getString("items.editteams.gray.name")).build();
        ItemStack lightGray = new ItemBuilder(Material.LIGHT_GRAY_WOOL).name(items.getString("items.editteams.lightgray.name")).build();
        ItemStack cyan = new ItemBuilder(Material.CYAN_WOOL).name(items.getString("items.editteams.cyan.name")).build();
        ItemStack blue = new ItemBuilder(Material.BLUE_WOOL).name(items.getString("items.editteams.blue.name")).build();
        ItemStack green = new ItemBuilder(Material.GREEN_WOOL).name(items.getString("items.editteams.green.name")).build();
        ItemStack red = new ItemBuilder(Material.RED_WOOL).name(items.getString("items.editteams.red.name")).build();
        ItemStack black = new ItemBuilder(Material.BLACK_WOOL).name(items.getString("items.editteams.black.name")).build();
        ItemStack greenButton = new ItemBuilder(Material.LIME_DYE).name(items.getString("items.editteams.greenbutton.name")).build();
        ItemStack grayButton = new ItemBuilder(Material.GRAY_DYE).name(items.getString("items.editteams.graybutton.name")).build();
        ItemStack back = new ItemBuilder(Material.BARRIER).name(items.getString("items.editteams.back.name")).build();

        GUI gui = new GUI(arenaName, 5, plugin, player);

        gui.addItem(white, items.getInt("items.editteams.white.slot"))
                .addDefaultEvent(openTeam(player, arenaName, TeamColor.WHITE, plugin));

        gui.addItem(orange, items.getInt("items.editteams.orange.slot"))
                .addDefaultEvent(openTeam(player, arenaName, TeamColor.ORANGE, plugin));

        gui.addItem(magenta, items.getInt("items.editteams.magenta.slot"))
                .addDefaultEvent(openTeam(player, arenaName, TeamColor.MAGENTA, plugin));

        gui.addItem(lightBlue, items.getInt("items.editteams.lightblue.slot"))
                .addDefaultEvent(openTeam(player, arenaName, TeamColor.LIGHT_BLUE, plugin));

        gui.addItem(yellow, items.getInt("items.editteams.yellow.slot"))
                .addDefaultEvent(openTeam(player, arenaName, TeamColor.YELLOW, plugin));

        gui.addItem(lime, items.getInt("items.editteams.lime.slot"))
                .addDefaultEvent(openTeam(player, arenaName, TeamColor.LIME, plugin));

        gui.addItem(pink, items.getInt("items.editteams.pink.slot"))
                .addDefaultEvent(openTeam(player, arenaName, TeamColor.PINK, plugin));

        gui.addItem(gray, items.getInt("items.editteams.gray.slot"))
                .addDefaultEvent(openTeam(player, arenaName, TeamColor.GRAY, plugin));

        gui.addItem(lightGray, items.getInt("items.editteams.lightgray.slot"))
                .addDefaultEvent(openTeam(player, arenaName, TeamColor.LIGHT_GRAY, plugin));

        gui.addItem(cyan, items.getInt("items.editteams.cyan.slot"))
                .addDefaultEvent(openTeam(player, arenaName, TeamColor.CYAN, plugin));

        gui.addItem(blue, items.getInt("items.editteams.blue.slot"))
                .addDefaultEvent(openTeam(player, arenaName, TeamColor.BLUE, plugin));

        gui.addItem(green, items.getInt("items.editteams.green.slot"))
                .addDefaultEvent(openTeam(player, arenaName, TeamColor.GREEN, plugin));

        gui.addItem(red, items.getInt("items.editteams.red.slot"))
                .addDefaultEvent(openTeam(player, arenaName, TeamColor.RED, plugin));

        gui.addItem(black, items.getInt("items.editteams.black.slot"))
                .addDefaultEvent(openTeam(player, arenaName, TeamColor.BLACK, plugin));


        gui.addItem(back, items.getInt("items.editteams.back.slot"))
                .addDefaultEvent(() -> EditMenu.openEditMenu(arenaName, player, plugin));


        if (arenas.getBoolean(arenaName + ".team.white.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.white.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.WHITE, plugin));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.white.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.WHITE, plugin));
        }
        if (arenas.getBoolean(arenaName + ".team.orange.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.orange.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.ORANGE, plugin));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.orange.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.ORANGE, plugin));
        }

        if (arenas.getBoolean(arenaName + ".team.magenta.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.magenta.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.MAGENTA, plugin));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.magenta.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.MAGENTA, plugin));
        }

        if (arenas.getBoolean(arenaName + ".team.lightblue.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.lightblue.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.LIGHT_BLUE, plugin));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.lightblue.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.LIGHT_BLUE, plugin));
        }

        if (arenas.getBoolean(arenaName + ".team.yellow.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.yellow.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.YELLOW, plugin));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.yellow.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.YELLOW, plugin));
        }

        if (arenas.getBoolean(arenaName + ".team.lime.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.lime.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.LIME, plugin));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.lime.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.LIME, plugin));
        }

        if (arenas.getBoolean(arenaName + ".team.pink.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.pink.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.PINK, plugin));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.pink.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.PINK, plugin));
        }

        if (arenas.getBoolean(arenaName + ".team.gray.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.gray.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.GRAY, plugin));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.gray.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.GRAY, plugin));
        }

        if (arenas.getBoolean(arenaName + ".team.lightgray.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.lightgray.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.LIGHT_GRAY, plugin));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.lightgray.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.LIGHT_GRAY, plugin));
        }

        if (arenas.getBoolean(arenaName + ".team.cyan.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.cyan.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.CYAN, plugin));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.cyan.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.CYAN, plugin));
        }

        if (arenas.getBoolean(arenaName + ".team.blue.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.blue.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.BLUE, plugin));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.blue.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.BLUE, plugin));
        }
        if (arenas.getBoolean(arenaName + ".team.green.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.green.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.GREEN, plugin));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.green.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.GREEN, plugin));
        }

        if (arenas.getBoolean(arenaName + ".team.red.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.red.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.RED, plugin));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.red.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.RED, plugin));
        }

        if (arenas.getBoolean(arenaName + ".team.black.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.black.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.BLACK, plugin));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.black.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, TeamColor.BLACK, plugin));
        }

        gui.openGUI();
    }

    private static Runnable openTeam(Player player, String arenaName, TeamColor teamName, EggWarsReloaded plugin) {
        return () -> {
            TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player, plugin);

            EggAssistant.removePlayer(player);
            GeneratorAssistant.removePlayer(player);
            ShopAssistant.removePlayer(player);
        };
    }

    private static Runnable clickButton(Player player, String arenaName, TeamColor teamName, EggWarsReloaded plugin) {
        return () -> {
            if (ArenaManager.isTeamReady(arenaName, teamName)) {
                if (ArenaManager.isTeamRegistered(arenaName, teamName)) {
                    ArenaManager.setTeamRegistered(arenaName, teamName, false);
                    ArenaManager.setArenaRegistered(arenaName, false, null);

                    player.sendMessage(PREFIX + "Unregistered team: " + teamName);
                } else {
                    ArenaManager.setTeamRegistered(arenaName, teamName, true);

                    player.sendMessage(PREFIX + "Registered team: " + teamName);
                }
            } else {
                player.sendMessage(PREFIX + "This team is not ready! Please finish the team setup. (Click the wool)");
            }

            TeamMenu.setupTeamMenu(arenaName, player, plugin);
        };
    }
}
