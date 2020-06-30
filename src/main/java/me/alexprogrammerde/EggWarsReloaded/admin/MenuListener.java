package me.alexprogrammerde.EggWarsReloaded.admin;

import me.alexprogrammerde.EggWarsReloaded.EggWarsMain;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.Objects;

public class MenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (EditManager.inventorymap.containsKey(player)) {
                boolean isedit = false;
                int index = 0;
                Inventory inventory = event.getClickedInventory();
                Inventory[] foundarr = new Inventory[0];

                for (Inventory[] arr : EditManager.menus.keySet()) {
                    for (Inventory inv : arr) {
                        if (Objects.equals(inventory, inv)) {
                            isedit = true;
                        }
                    }

                    if (isedit) {
                        index = Arrays.asList(arr).indexOf(inventory);
                        foundarr = arr;
                        break;
                    }
                }

                if (isedit) {
                    int slot = event.getSlot();
                    FileConfiguration items = EggWarsMain.getEggWarsMain().getItems();

                    String arenaname = EditManager.menus.get(foundarr);

                    event.setCancelled(true);

                    if (index == 0) {
                        if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                            if (items.getInt("items.editmain.mainlobby.slot") == slot) {
                                ArenaManager.setMainLobby(arenaname, null);
                                EditMenu.setupEditMenu(inventory, arenaname);
                                player.sendMessage("Reseted main lobby.");
                            } else if (items.getInt("items.editmain.waitinglobby.slot") == slot) {
                                ArenaManager.setWaitingLobby(arenaname, null);
                                EditMenu.setupEditMenu(inventory, arenaname);
                                player.sendMessage("Reseted waiting lobby.");
                            } else if (items.getInt("items.editmain.spectator.slot") == slot) {
                                ArenaManager.setSpectator(arenaname, null);
                                EditMenu.setupEditMenu(inventory, arenaname);
                                player.sendMessage("Reseted spectator spawn.");
                            }
                        } else {
                            if (items.getInt("items.editmain.mainlobby.slot") == slot) {
                                if (ArenaManager.getArenas().contains("arenas." + arenaname + "mainlobby")) {
                                    player.sendMessage("Sorry this is already set. Please reset it with: shift + click");
                                } else {
                                    ArenaManager.setMainLobby(arenaname, player.getLocation());
                                    EditMenu.setupEditMenu(inventory, arenaname);
                                    player.sendMessage("Set main lobby.");
                                }
                            } else if (items.getInt("items.editmain.waitinglobby.slot") == slot) {
                                if (ArenaManager.getArenas().contains("arenas." + arenaname + "waitinglobby")) {
                                    player.sendMessage("Sorry this is already set. Please reset it with: shift + click");
                                } else {
                                    ArenaManager.setWaitingLobby(arenaname, player.getLocation());
                                    EditMenu.setupEditMenu(inventory, arenaname);
                                    player.sendMessage("Set waiting lobby.");
                                }
                            } else if (items.getInt("items.editmain.spectator.slot") == slot) {
                                if (ArenaManager.getArenas().contains("arenas." + arenaname + "spectator")) {
                                    player.sendMessage("Sorry this is already set. Please reset it with: shift + click");
                                } else {
                                    ArenaManager.setSpectator(arenaname, player.getLocation());
                                    EditMenu.setupEditMenu(inventory, arenaname);
                                    player.sendMessage("Set spectator spawn.");
                                }
                            } else if (items.getInt("items.editmain.teams.slot") == slot) {
                                // Open the inv
                                TeamMenu.setupTeamMenu(foundarr[1], arenaname);
                                player.openInventory(foundarr[1]);
                            }
                        }
                    } else if (index == 1) {
                        boolean isButton = false;
                        boolean isTeam = false;
                        String name = "";

                        for (String key : items.getConfigurationSection("items.editteams").getKeys(false)) {
                            if (items.getInt("items.editteams." + key + ".buttonslot") == slot) {
                                name = key;
                                isButton = true;
                                break;
                            }

                            if (items.getInt("items.editteams." + key + ".slot") == slot) {
                                if (key.equals("back")) {
                                    player.openInventory(foundarr[0]);
                                } else {
                                    name = key;
                                    isTeam = true;
                                }

                                break;
                            }
                        }

                        if (isTeam) {
                            Inventory undermenu = Bukkit.createInventory(player, 9 * 3, name);
                            TeamUnderMenu.setupTeamUnderMenu(undermenu, name);

                            EditManager.inventorymap.put(player, UtilCore.addInventory(foundarr, undermenu));
                            EditManager.menus.put(UtilCore.addInventory(foundarr, undermenu), arenaname);

                            EditManager.playerteam.put(player, name);

                            player.openInventory(undermenu);
                        }

                        if (isButton) {
                            if (EditMenu.isTeamReady(name)) {
                                if (ArenaManager.isTeamRegistered(arenaname, name)) {
                                    ArenaManager.setTeamRegistered(arenaname, name, false);
                                } else {
                                    ArenaManager.setTeamRegistered(arenaname, name, true);
                                }
                            } else {
                                player.sendMessage("This team is not ready! Please finish the team setup. (Click the wool)");
                            }
                        }
                    }
                }
            }
        }
    }

    // For later use
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player= event.getPlayer();
    }
}
