package me.alexprogrammerde.EggWarsReloaded.admin.guis;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.admin.assistants.EggAssistant;
import me.alexprogrammerde.EggWarsReloaded.admin.assistants.GeneratorAssistant;
import me.alexprogrammerde.EggWarsReloaded.admin.assistants.ShopAssistant;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.ItemBuilder;
import me.alexprogrammerde.EggWarsReloaded.utils.gui.GUI;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TeamMenu {

    public static void setupTeamMenu(String arenaName, Player player) {
        FileConfiguration items = EggWarsReloaded.get().getItems();
        FileConfiguration arenas = EggWarsReloaded.get().getArenas();

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
        ItemStack purple = new ItemBuilder(Material.PURPLE_WOOL).name(items.getString("items.editteams.purple.name")).build();
        ItemStack blue = new ItemBuilder(Material.BLUE_WOOL).name(items.getString("items.editteams.blue.name")).build();
        ItemStack brown = new ItemBuilder(Material.BROWN_WOOL).name(items.getString("items.editteams.brown.name")).build();
        ItemStack green = new ItemBuilder(Material.GREEN_WOOL).name(items.getString("items.editteams.green.name")).build();
        ItemStack red = new ItemBuilder(Material.RED_WOOL).name(items.getString("items.editteams.red.name")).build();
        ItemStack black = new ItemBuilder(Material.BLACK_WOOL).name(items.getString("items.editteams.black.name")).build();
        ItemStack greenButton = new ItemBuilder(Material.LIME_DYE).name(items.getString("items.editteams.greenbutton.name")).build();
        ItemStack grayButton = new ItemBuilder(Material.GRAY_DYE).name(items.getString("items.editteams.graybutton.name")).build();
        ItemStack back = new ItemBuilder(Material.BARRIER).name(items.getString("items.editteams.back.name")).build();

        GUI gui = new GUI(arenaName, 5, EggWarsReloaded.get(), player);

        gui.addItem(white, items.getInt("items.editteams.white.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "white"));

        gui.addItem(orange, items.getInt("items.editteams.orange.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "orange"));

        gui.addItem(magenta, items.getInt("items.editteams.magenta.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "magenta"));

        gui.addItem(lightBlue, items.getInt("items.editteams.lightblue.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "lightblue"));

        gui.addItem(yellow, items.getInt("items.editteams.yellow.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "yellow"));

        gui.addItem(lime, items.getInt("items.editteams.lime.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "lime"));

        gui.addItem(pink, items.getInt("items.editteams.pink.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "pink"));

        gui.addItem(gray, items.getInt("items.editteams.gray.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "gray"));

        gui.addItem(lightGray, items.getInt("items.editteams.lightgray.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "lightgray"));

        gui.addItem(cyan, items.getInt("items.editteams.cyan.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "cyan"));

        gui.addItem(purple, items.getInt("items.editteams.purple.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "purple"));

        gui.addItem(blue, items.getInt("items.editteams.blue.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "blue"));

        gui.addItem(brown, items.getInt("items.editteams.brown.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "brown"));

        gui.addItem(green, items.getInt("items.editteams.green.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "green"));

        gui.addItem(red, items.getInt("items.editteams.red.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "red"));

        gui.addItem(black, items.getInt("items.editteams.black.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "black"));


        gui.addItem(back, items.getInt("items.editteams.back.slot"))
                .addDefaultEvent(new Runnable() {
                    @Override
                    public void run() {
                        EditMenu.openEditMenu(arenaName, player);
                    }
                });


        if (arenas.getBoolean(arenaName + ".team.white.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.white.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "white"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.white.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "white"));
        }
        if (arenas.getBoolean(arenaName + ".team.orange.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.orange.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "orange"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.orange.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "orange"));
        }

        if (arenas.getBoolean(arenaName + ".team.magenta.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.magenta.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "magenta"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.magenta.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "magenta"));
        }

        if (arenas.getBoolean(arenaName + ".team.lightblue.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.lightblue.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "lightblue"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.lightblue.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "lightblue"));
        }

        if (arenas.getBoolean(arenaName + ".team.yellow.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.yellow.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "yellow"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.yellow.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "yellow"));
        }

        if (arenas.getBoolean(arenaName + ".team.lime.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.lime.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "lime"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.lime.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "lime"));
        }

        if (arenas.getBoolean(arenaName + ".team.pink.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.pink.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "pink"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.pink.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "pink"));
        }

        if (arenas.getBoolean(arenaName + ".team.gray.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.gray.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "gray"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.gray.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "gray"));
        }

        if (arenas.getBoolean(arenaName + ".team.lightgray.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.lightgray.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "lightgray"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.lightgray.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "lightgray"));
        }

        if (arenas.getBoolean(arenaName + ".team.cyan.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.cyan.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "cyan"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.cyan.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "cyan"));
        }

        if (arenas.getBoolean(arenaName + ".team.purple.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.purple.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "purple"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.purple.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "purple"));
        }

        if (arenas.getBoolean(arenaName + ".team.blue.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.blue.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "blue"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.blue.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "blue"));
        }

        if (arenas.getBoolean(arenaName + ".team.brown.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.brown.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "brown"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.brown.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "brown"));
        }

        if (arenas.getBoolean(arenaName + ".team.green.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.green.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "green"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.green.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "green"));
        }

        if (arenas.getBoolean(arenaName + ".team.red.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.red.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "red"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.red.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "red"));
        }

        if (arenas.getBoolean(arenaName + ".team.black.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.black.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "black"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.black.buttonslot"))
                    .addDefaultEvent(clickButton(player, arenaName, "black"));
        }

        gui.openGUI();
    }

    private static Runnable openTeam(Player player, String arenaName, String teamName) {
        return () -> {
            TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player);

            EggAssistant.removePlayer(player);
            GeneratorAssistant.removePlayer(player);
            ShopAssistant.removePlayer(player);
        };
    }

    private static Runnable clickButton(Player player, String arenaName, String teamnName) {
        return () -> {
            if (ArenaManager.isTeamReady(arenaName, teamnName)) {
                if (ArenaManager.isTeamRegistered(arenaName, teamnName)) {
                    ArenaManager.setTeamRegistered(arenaName, teamnName, false);
                    ArenaManager.setArenaRegistered(arenaName, false, null);

                    player.sendMessage("Unregistered team: " + teamnName);
                } else {
                    ArenaManager.setTeamRegistered(arenaName, teamnName, true);

                    player.sendMessage("Registered team: " + teamnName);
                }
            } else {
                player.sendMessage("This team is not ready! Please finish the team setup. (Click the wool)");
            }

            TeamMenu.setupTeamMenu(arenaName, player);
        };
    }
}
