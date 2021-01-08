package me.alexprogrammerde.EggWarsReloaded.admin.guis;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.admin.assistants.EggAssistant;
import me.alexprogrammerde.EggWarsReloaded.admin.assistants.GeneratorAssistant;
import me.alexprogrammerde.EggWarsReloaded.admin.assistants.ShopAssistant;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.gui.GUI;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamMenu {

    public static void setupTeamMenu(String arenaName, Player player) {
        FileConfiguration items = EggWarsReloaded.get().getItems();
        FileConfiguration arenas = EggWarsReloaded.get().getArenas();

        // Load Data from storage
        ItemStack white = new ItemStack(Material.WHITE_WOOL);
        ItemStack orange = new ItemStack(Material.ORANGE_WOOL);
        ItemStack magenta = new ItemStack(Material.MAGENTA_WOOL);
        ItemStack lightblue = new ItemStack(Material.LIGHT_BLUE_WOOL);
        ItemStack yellow = new ItemStack(Material.YELLOW_WOOL);
        ItemStack lime = new ItemStack(Material.LIME_WOOL);
        ItemStack pink = new ItemStack(Material.PINK_WOOL);
        ItemStack gray = new ItemStack(Material.GRAY_WOOL);
        ItemStack lightgray = new ItemStack(Material.LIGHT_GRAY_WOOL);
        ItemStack cyan = new ItemStack(Material.CYAN_WOOL);
        ItemStack purple = new ItemStack(Material.PURPLE_WOOL);
        ItemStack blue = new ItemStack(Material.BLUE_WOOL);
        ItemStack brown = new ItemStack(Material.BROWN_WOOL);
        ItemStack green = new ItemStack(Material.GREEN_WOOL);
        ItemStack red = new ItemStack(Material.RED_WOOL);
        ItemStack black = new ItemStack(Material.BLACK_WOOL);
        ItemStack greenButton = new ItemStack(Material.LIME_DYE);
        ItemStack grayButton = new ItemStack(Material.GRAY_DYE);
        ItemStack back = new ItemStack(Material.BARRIER);

        ItemMeta whitemeta = white.getItemMeta();
        ItemMeta orangemeta = orange.getItemMeta();
        ItemMeta magentameta = magenta.getItemMeta();
        ItemMeta lightbluemeta = lightblue.getItemMeta();
        ItemMeta yellowmeta = yellow.getItemMeta();
        ItemMeta limemeta = lime.getItemMeta();
        ItemMeta pinkmeta = pink.getItemMeta();
        ItemMeta graymeta = gray.getItemMeta();
        ItemMeta lightgraymeta = lightgray.getItemMeta();
        ItemMeta cyanmeta = cyan.getItemMeta();
        ItemMeta purplemeta = purple.getItemMeta();
        ItemMeta bluemeta = blue.getItemMeta();
        ItemMeta brownmeta = brown.getItemMeta();
        ItemMeta greenmeta = green.getItemMeta();
        ItemMeta redmeta = red.getItemMeta();
        ItemMeta blackmeta = black.getItemMeta();
        ItemMeta greenbuttonmeta = greenButton.getItemMeta();
        ItemMeta graybuttonmeta = grayButton.getItemMeta();
        ItemMeta backmeta = back.getItemMeta();

        // Get item names from items.yml
        whitemeta.setDisplayName(items.getString("items.editteams.white.name"));
        orangemeta.setDisplayName(items.getString("items.editteams.orange.name"));
        magentameta.setDisplayName(items.getString("items.editteams.magenta.name"));
        lightbluemeta.setDisplayName(items.getString("items.editteams.lightblue.name"));
        yellowmeta.setDisplayName(items.getString("items.editteams.yellow.name"));
        limemeta.setDisplayName(items.getString("items.editteams.lime.name"));
        pinkmeta.setDisplayName(items.getString("items.editteams.pink.name"));
        graymeta.setDisplayName(items.getString("items.editteams.gray.name"));
        lightgraymeta.setDisplayName(items.getString("items.editteams.lightgray.name"));
        cyanmeta.setDisplayName(items.getString("items.editteams.cyan.name"));
        purplemeta.setDisplayName(items.getString("items.editteams.purple.name"));
        bluemeta.setDisplayName(items.getString("items.editteams.blue.name"));
        brownmeta.setDisplayName(items.getString("items.editteams.brown.name"));
        greenmeta.setDisplayName(items.getString("items.editteams.green.name"));
        redmeta.setDisplayName(items.getString("items.editteams.red.name"));
        blackmeta.setDisplayName(items.getString("items.editteams.black.name"));
        greenbuttonmeta.setDisplayName(items.getString("items.editteams.greenbutton.name"));
        graybuttonmeta.setDisplayName(items.getString("items.editteams.graybutton.name"));
        backmeta.setDisplayName(items.getString("items.editteams.back.name"));

        white.setItemMeta(whitemeta);
        orange.setItemMeta(orangemeta);
        magenta.setItemMeta(magentameta);
        lightblue.setItemMeta(lightbluemeta);
        yellow.setItemMeta(yellowmeta);
        lime.setItemMeta(limemeta);
        pink.setItemMeta(pinkmeta);
        gray.setItemMeta(graymeta);
        lightgray.setItemMeta(lightgraymeta);
        cyan.setItemMeta(cyanmeta);
        purple.setItemMeta(purplemeta);
        blue.setItemMeta(bluemeta);
        brown.setItemMeta(brownmeta);
        green.setItemMeta(greenmeta);
        red.setItemMeta(redmeta);
        black.setItemMeta(blackmeta);
        greenButton.setItemMeta(greenbuttonmeta);
        grayButton.setItemMeta(graybuttonmeta);
        back.setItemMeta(backmeta);

        GUI gui = new GUI(arenaName, 5, EggWarsReloaded.get(), player);

        gui.addItem(white, items.getInt("items.editteams.white.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "white"));

        gui.addItem(orange, items.getInt("items.editteams.orange.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "orange"));

        gui.addItem(magenta, items.getInt("items.editteams.magenta.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "magenta"));

        gui.addItem(lightblue, items.getInt("items.editteams.lightblue.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "lightblue"));

        gui.addItem(yellow, items.getInt("items.editteams.yellow.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "yellow"));

        gui.addItem(lime, items.getInt("items.editteams.lime.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "lime"));

        gui.addItem(pink, items.getInt("items.editteams.pink.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "pink"));

        gui.addItem(gray, items.getInt("items.editteams.gray.slot"))
                .addDefaultEvent(openTeam(player, arenaName, "gray"));

        gui.addItem(lightgray, items.getInt("items.editteams.lightgray.slot"))
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
