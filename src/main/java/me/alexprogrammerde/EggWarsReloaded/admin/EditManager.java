package me.alexprogrammerde.EggWarsReloaded.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class EditManager {
    public static HashMap<Player, Inventory[]> inventorymap = new HashMap<>();
    public static HashMap<Inventory[], String> menus = new HashMap<>();
    public static HashMap<Player, String> playerteam = new HashMap<>();

    Player player;

    public EditManager(Player player) {
        this.player = player;
    }

    public void openMenu(String arenaname) {
        // Setup Inventory and Items
        Inventory main = Bukkit.createInventory(player, 9 * 3, arenaname);
        Inventory team = Bukkit.createInventory(player, 9 * 5, arenaname);
        Inventory teamundermenu = Bukkit.createInventory(player, 9 * 3, arenaname);

        EditMenu.setupEditMenu(main, arenaname, player);
        TeamMenu.setupTeamMenu(team, arenaname);

        // Add items and player to the HashMap
        Inventory[] arr = new Inventory[3];
        arr[0] = main;
        arr[1] = team;
        arr[2] = teamundermenu;

        inventorymap.put(player, arr);
        menus.put(arr, arenaname);

        // Open the inventory
        player.openInventory(main);
    }
}
