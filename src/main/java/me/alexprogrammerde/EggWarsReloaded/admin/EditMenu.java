package me.alexprogrammerde.EggWarsReloaded.admin;

import me.alexprogrammerde.EggWarsReloaded.EggWarsMain;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EditMenu {

    public static void setupEditMenu(Inventory inventory, String arenaname) {
        FileConfiguration items = EggWarsMain.getEggWarsMain().getItems();
        FileConfiguration arenas = EggWarsMain.getEggWarsMain().getArenas();

        // Load Data from storage
        ItemStack mainlobby = new ItemStack(Material.GREEN_CONCRETE);
        ItemStack waitinglobby = new ItemStack(Material.RED_CONCRETE);
        ItemStack spectator = new ItemStack(Material.YELLOW_CONCRETE);
        ItemStack register = new ItemStack(Material.END_CRYSTAL);
        ItemStack teams = new ItemStack(Material.WHITE_WOOL);

        ItemMeta mainlobbymeta = mainlobby.getItemMeta();
        ItemMeta waitinglobbymeta = waitinglobby.getItemMeta();
        ItemMeta spectatormeta = spectator.getItemMeta();
        ItemMeta registermeta = spectator.getItemMeta();
        ItemMeta teamsmeta = teams.getItemMeta();

        // Give item names from items.yml
        mainlobbymeta.setDisplayName(items.getString("items.editmain.mainlobby.name"));
        waitinglobbymeta.setDisplayName(items.getString("items.editmain.waitinglobby.name"));
        spectatormeta.setDisplayName(items.getString("items.editmain.spectator.name"));
        registermeta.setDisplayName(items.getString("items.editmain.register.name"));
        teamsmeta.setDisplayName(items.getString("items.editmain.teams.name"));

        if (ArenaManager.getArenas().contains("arenas." + arenaname + ".mainlobby")) {
            mainlobbymeta.addEnchant(Enchantment.DURABILITY, 0, true);
            mainlobbymeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        if (ArenaManager.getArenas().contains("arenas." + arenaname + ".waitinglobby")) {
            waitinglobbymeta.addEnchant(Enchantment.DURABILITY, 0, true);
            waitinglobbymeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        if (ArenaManager.getArenas().contains("arenas." + arenaname + ".spectator")) {
            spectatormeta.addEnchant(Enchantment.DURABILITY, 0, true);
            spectatormeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        if (ArenaManager.getArenas().contains("arenas." + arenaname + ".register")) {
            spectatormeta.addEnchant(Enchantment.DURABILITY, 0, true);
            spectatormeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        List<String> mainlobbylist = new ArrayList<>();
        List<String> waitinglobbylist = new ArrayList<>();
        List<String> spectatorlist = new ArrayList<>();
        List<String> registerlist = new ArrayList<>();
        List<String> teamslist = new ArrayList<>();

        mainlobbylist.add("Left click to set the main lobby.");
        waitinglobbylist.add("Left click to set the waiting lobby.");
        spectatorlist.add("Left click to set the spectator spawn.");
        teamslist.add("Left click to edit all teams.");

        if (arenas.getBoolean("arenas." + arenaname + ".registered")) {
            registerlist.add("Left click to unregister the arena.");
        } else {
            if (isArenaReady(arenaname)) {
                registerlist.add("Left click to register the arena");
            } else {
                registerlist.add("There are still some steps left!");
            }
        }

        if (ArenaManager.getArenas().contains("arenas." + arenaname + ".mainlobby")) {
            Location location = ArenaManager.getArenas().getLocation("arenas." + arenaname + ".mainlobby");

            mainlobbylist.add("Current: " +  location.getWorld().getName() + " "
                    + Math.round(location.getBlockX()) + ":"
                    + Math.round(location.getBlockY()) + ":"
                    + Math.round(location.getBlockZ()) + " "
                    + Math.round(location.getYaw()) + ":"
                    + Math.round(location.getPitch()));
        }

        if (ArenaManager.getArenas().contains("arenas." + arenaname + ".waitinglobby")) {
            Location location = ArenaManager.getArenas().getLocation("arenas." + arenaname + ".waitinglobby");
            waitinglobbylist.add("Current: " +  location.getWorld().getName() + " "
                    + Math.round(location.getBlockX()) + ":"
                    + Math.round(location.getBlockY()) + ":"
                    + Math.round(location.getBlockZ()) + " "
                    + Math.round(location.getYaw()) + ":"
                    + Math.round(location.getPitch()));
        }

        if (ArenaManager.getArenas().contains("arenas." + arenaname + ".spectator")) {
            Location location = ArenaManager.getArenas().getLocation("arenas." + arenaname + ".spectator");
            spectatorlist.add("Current: " +  location.getWorld().getName() + " "
                    + Math.round(location.getBlockX()) + ":"
                    + Math.round(location.getBlockY()) + ":"
                    + Math.round(location.getBlockZ()) + " "
                    + Math.round(location.getYaw()) + ":"
                    + Math.round(location.getPitch()));
        }

        mainlobbylist.add("Use shift + left click to reset it.");
        waitinglobbylist.add("Use shift + left click to reset it.");
        spectatorlist.add("Use shift + left click to reset it.");

        mainlobbymeta.setLore(mainlobbylist);
        waitinglobbymeta.setLore(waitinglobbylist);
        spectatormeta.setLore(spectatorlist);
        registermeta.setLore(registerlist);
        teamsmeta.setLore(teamslist);

        mainlobby.setItemMeta(mainlobbymeta);
        waitinglobby.setItemMeta(waitinglobbymeta);
        spectator.setItemMeta(spectatormeta);
        register.setItemMeta(registermeta);
        teams.setItemMeta(teamsmeta);

        // Assign the items to the inventory
        inventory.setItem(items.getInt("items.editmain.mainlobby.slot"), mainlobby);
        inventory.setItem(items.getInt("items.editmain.waitinglobby.slot"), waitinglobby);
        inventory.setItem(items.getInt("items.editmain.spectator.slot"), spectator);
        inventory.setItem(items.getInt("items.editmain.register.slot"), register);
        inventory.setItem(items.getInt("items.editmain.teams.slot"), teams);
    }

    static boolean isArenaReady(String arenaname) {
        FileConfiguration arenas = EggWarsMain.getEggWarsMain().getArenas();

        if (!arenas.contains("arenas." + arenaname + ".mainlobby")) {
            return false;
        }

        if (!arenas.contains("arenas." + arenaname + ".waitinglobby")) {
            return false;
        }

        if (!arenas.contains("arenas." + arenaname + ".spectator")) {
            return false;
        }

        return true;
    }

    static boolean isTeamReady(String teamname) {
        // TODO
        return false;
    }
}
