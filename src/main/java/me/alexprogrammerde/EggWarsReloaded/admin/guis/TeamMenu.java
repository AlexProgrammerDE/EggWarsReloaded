package me.alexprogrammerde.EggWarsReloaded.admin.guis;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.admin.EggAssistant;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamMenu {

    public static void setupTeamMenu(String arenaname, Player player) {
        FileConfiguration items = EggWarsReloaded.getEggWarsMain().getItems();
        FileConfiguration arenas = EggWarsReloaded.getEggWarsMain().getArenas();

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

        GUI gui = new GUI(arenaname, 5, EggWarsReloaded.getEggWarsMain(), player);

        gui.addItem(white, items.getInt("items.editteams.white.slot"))
                .addDefaultEvent(openTeam(player, arenaname, "white"));

        gui.addItem(orange, items.getInt("items.editteams.orange.slot"))
                .addDefaultEvent(openTeam(player, arenaname, "orange"));

        gui.addItem(magenta, items.getInt("items.editteams.magenta.slot"))
                .addDefaultEvent(openTeam(player, arenaname, "magenta"));

        gui.addItem(lightblue, items.getInt("items.editteams.lightblue.slot"))
                .addDefaultEvent(openTeam(player, arenaname, "lightblue"));

        gui.addItem(yellow, items.getInt("items.editteams.yellow.slot"))
                .addDefaultEvent(openTeam(player, arenaname, "yellow"));

        gui.addItem(lime, items.getInt("items.editteams.lime.slot"))
                .addDefaultEvent(openTeam(player, arenaname, "lime"));

        gui.addItem(pink, items.getInt("items.editteams.pink.slot"))
                .addDefaultEvent(openTeam(player, arenaname, "pink"));

        gui.addItem(gray, items.getInt("items.editteams.gray.slot"))
                .addDefaultEvent(openTeam(player, arenaname, "gray"));

        gui.addItem(lightgray, items.getInt("items.editteams.lightgray.slot"))
                .addDefaultEvent(openTeam(player, arenaname, "lightgray"));

        gui.addItem(cyan, items.getInt("items.editteams.cyan.slot"))
                .addDefaultEvent(openTeam(player, arenaname, "cyan"));

        gui.addItem(purple, items.getInt("items.editteams.purple.slot"))
                .addDefaultEvent(openTeam(player, arenaname, "purple"));

        gui.addItem(blue, items.getInt("items.editteams.blue.slot"))
                .addDefaultEvent(openTeam(player, arenaname, "blue"));

        gui.addItem(brown, items.getInt("items.editteams.brown.slot"))
                .addDefaultEvent(openTeam(player, arenaname, "brown"));

        gui.addItem(green, items.getInt("items.editteams.green.slot"))
                .addDefaultEvent(openTeam(player, arenaname, "green"));

        gui.addItem(red, items.getInt("items.editteams.red.slot"))
                .addDefaultEvent(openTeam(player, arenaname, "red"));

        gui.addItem(black, items.getInt("items.editteams.black.slot"))
                .addDefaultEvent(openTeam(player, arenaname, "black"));


        gui.addItem(back, items.getInt("items.editteams.back.slot"))
                .addDefaultEvent(new Runnable() {
                    @Override
                    public void run() {
                        EditMenu.openEditMenu(arenaname, player);
                    }
                });


        // TODO: Add actions to team buttons
        if (arenas.getBoolean("arenas." + arenaname + ".team.white.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.white.buttonslot"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.white.buttonslot"));
        }
        if (arenas.getBoolean("arenas." + arenaname + ".team.orange.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.orange.buttonslot"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.orange.buttonslot"));
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.magenta.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.magenta.buttonslot"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.magenta.buttonslot"));
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.lightblue.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.lightblue.buttonslot"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.lightblue.buttonslot"));
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.yellow.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.yellow.buttonslot"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.yellow.buttonslot"));
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.lime.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.lime.buttonslot"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.lime.buttonslot"));
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.pink.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.pink.buttonslot"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.pink.buttonslot"));
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.gray.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.gray.buttonslot"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.gray.buttonslot"));
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.lightgray.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.lightgray.buttonslot"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.lightgray.buttonslot"));
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.cyan.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.cyan.buttonslot"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.cyan.buttonslot"));
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.purple.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.purple.buttonslot"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.purple.buttonslot"));
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.blue.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.blue.buttonslot"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.blue.buttonslot"));
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.brown.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.brown.buttonslot"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.brown.buttonslot"));
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.green.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.green.buttonslot"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.green.buttonslot"));
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.red.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.red.buttonslot"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.red.buttonslot"));
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.black.registered")) {
            gui.addItem(greenButton.clone(), items.getInt("items.editteams.black.buttonslot"));
        } else {
            gui.addItem(grayButton.clone(), items.getInt("items.editteams.black.buttonslot"));
        }

        gui.openGUI();
    }

    private static Runnable openTeam(Player player, String arenaname, String teamname) {
        return () -> {
            Inventory undermenu = Bukkit.createInventory(player, 9 * 3, teamname);
            TeamUnderMenu.setupTeamUnderMenu(arenaname, teamname, player);

            EggAssistant.setPlayer(player, arenaname, teamname, "none");

            player.openInventory(undermenu);
        };
    }

    private static Runnable clickButton(Player player, String arenaname, String teamname) {
        return () -> {
            if (ArenaManager.isTeamReady(arenaname, teamname)) {
                if (ArenaManager.isTeamRegistered(arenaname, teamname)) {
                    ArenaManager.setTeamRegistered(arenaname, teamname, false);
                    ArenaManager.setArenaRegistered(arenaname, false, null);
                } else {
                    ArenaManager.setTeamRegistered(arenaname, teamname, true);
                }
                TeamMenu.setupTeamMenu(arenaname, player);
                player.sendMessage("Registered team: " + teamname);
            } else {
                player.sendMessage("This team is not ready! Please finish the team setup. (Click the wool)");
            }
        };
    }
}
