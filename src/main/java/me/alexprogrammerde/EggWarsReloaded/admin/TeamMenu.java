package me.alexprogrammerde.EggWarsReloaded.admin;

import me.alexprogrammerde.EggWarsReloaded.EggWarsMain;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamMenu {

    public static void setupTeamMenu(Inventory inventory, String arenaname) {
        FileConfiguration items = EggWarsMain.getEggWarsMain().getItems();
        FileConfiguration arenas = EggWarsMain.getEggWarsMain().getArenas();

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
        ItemStack greenbutton = new ItemStack(Material.LIME_DYE);
        ItemStack graybutton = new ItemStack(Material.GRAY_DYE);
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
        ItemMeta greenbuttonmeta = greenbutton.getItemMeta();
        ItemMeta graybuttonmeta = graybutton.getItemMeta();
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
        greenbutton.setItemMeta(greenbuttonmeta);
        graybutton.setItemMeta(graybuttonmeta);
        back.setItemMeta(backmeta);

        // Assign the items to the inventory
        inventory.setItem(items.getInt("items.editteams.white.slot"), white);
        inventory.setItem(items.getInt("items.editteams.orange.slot"), orange);
        inventory.setItem(items.getInt("items.editteams.magenta.slot"), magenta);
        inventory.setItem(items.getInt("items.editteams.lightblue.slot"), lightblue);
        inventory.setItem(items.getInt("items.editteams.yellow.slot"), yellow);
        inventory.setItem(items.getInt("items.editteams.lime.slot"), lime);
        inventory.setItem(items.getInt("items.editteams.pink.slot"), pink);
        inventory.setItem(items.getInt("items.editteams.gray.slot"), gray);
        inventory.setItem(items.getInt("items.editteams.lightgray.slot"), lightgray);
        inventory.setItem(items.getInt("items.editteams.cyan.slot"), cyan);
        inventory.setItem(items.getInt("items.editteams.purple.slot"), purple);
        inventory.setItem(items.getInt("items.editteams.blue.slot"), blue);
        inventory.setItem(items.getInt("items.editteams.brown.slot"), brown);
        inventory.setItem(items.getInt("items.editteams.green.slot"), green);
        inventory.setItem(items.getInt("items.editteams.red.slot"), red);
        inventory.setItem(items.getInt("items.editteams.black.slot"), black);

        inventory.setItem(items.getInt("items.editteams.back.slot"), back);

        if (arenas.getBoolean("arenas." + arenaname + ".team.white.registered")) {
            inventory.setItem(items.getInt("items.editteams.white.buttonslot"), greenbutton.clone());
        } else {
            inventory.setItem(items.getInt("items.editteams.white.buttonslot"), graybutton.clone());
        }
        if (arenas.getBoolean("arenas." + arenaname + ".team.orange.registered")) {
            inventory.setItem(items.getInt("items.editteams.orange.buttonslot"), greenbutton.clone());
        } else {
            inventory.setItem(items.getInt("items.editteams.orange.buttonslot"), graybutton.clone());
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.magenta.registered")) {
            inventory.setItem(items.getInt("items.editteams.magenta.buttonslot"), greenbutton.clone());
        } else {
            inventory.setItem(items.getInt("items.editteams.magenta.buttonslot"), graybutton.clone());
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.lightblue.registered")) {
            inventory.setItem(items.getInt("items.editteams.lightblue.buttonslot"), greenbutton.clone());
        } else {
            inventory.setItem(items.getInt("items.editteams.lightblue.buttonslot"), graybutton.clone());
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.yellow.registered")) {
            inventory.setItem(items.getInt("items.editteams.yellow.buttonslot"), greenbutton.clone());
        } else {
            inventory.setItem(items.getInt("items.editteams.yellow.buttonslot"), graybutton.clone());
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.lime.registered")) {
            inventory.setItem(items.getInt("items.editteams.lime.buttonslot"), greenbutton.clone());
        } else {
            inventory.setItem(items.getInt("items.editteams.lime.buttonslot"), graybutton.clone());
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.pink.registered")) {
            inventory.setItem(items.getInt("items.editteams.pink.buttonslot"), greenbutton.clone());
        } else {
            inventory.setItem(items.getInt("items.editteams.pink.buttonslot"), graybutton.clone());
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.gray.registered")) {
            inventory.setItem(items.getInt("items.editteams.gray.buttonslot"), greenbutton.clone());
        } else {
            inventory.setItem(items.getInt("items.editteams.gray.buttonslot"), graybutton.clone());
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.lightgray.registered")) {
            inventory.setItem(items.getInt("items.editteams.lightgray.buttonslot"), greenbutton.clone());
        } else {
            inventory.setItem(items.getInt("items.editteams.lightgray.buttonslot"), graybutton.clone());
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.cyan.registered")) {
            inventory.setItem(items.getInt("items.editteams.cyan.buttonslot"), greenbutton.clone());
        } else {
            inventory.setItem(items.getInt("items.editteams.cyan.buttonslot"), graybutton.clone());
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.purple.registered")) {
            inventory.setItem(items.getInt("items.editteams.purple.buttonslot"), greenbutton.clone());
        } else {
            inventory.setItem(items.getInt("items.editteams.purple.buttonslot"), graybutton.clone());
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.blue.registered")) {
            inventory.setItem(items.getInt("items.editteams.blue.buttonslot"), greenbutton.clone());
        } else {
            inventory.setItem(items.getInt("items.editteams.blue.buttonslot"), graybutton.clone());
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.brown.registered")) {
            inventory.setItem(items.getInt("items.editteams.brown.buttonslot"), greenbutton.clone());
        } else {
            inventory.setItem(items.getInt("items.editteams.brown.buttonslot"), graybutton.clone());
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.green.registered")) {
            inventory.setItem(items.getInt("items.editteams.green.buttonslot"), greenbutton.clone());
        } else {
            inventory.setItem(items.getInt("items.editteams.green.buttonslot"), graybutton.clone());
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.red.registered")) {
            inventory.setItem(items.getInt("items.editteams.red.buttonslot"), greenbutton.clone());
        } else {
            inventory.setItem(items.getInt("items.editteams.red.buttonslot"), graybutton.clone());
        }

        if (arenas.getBoolean("arenas." + arenaname + ".team.black.registered")) {
            inventory.setItem(items.getInt("items.editteams.black.buttonslot"), greenbutton.clone());
        } else {
            inventory.setItem(items.getInt("items.editteams.black.buttonslot"), graybutton.clone());
        }

        /*
        items.getInt("items.editteams.white.slot")
        items.getInt("items.editteams.orange.slot")
        items.getInt("items.editteams.magenta.slot")
        items.getInt("items.editteams.lightblue.slot")
        items.getInt("items.editteams.yellow.slot")
        items.getInt("items.editteams.lime.slot")
        items.getInt("items.editteams.pink.slot")
        items.getInt("items.editteams.gray.slot")
        items.getInt("items.editteams.lightgray.slot")
        items.getInt("items.editteams.cyan.slot")
        items.getInt("items.editteams.purple.slot")
        items.getInt("items.editteams.blue.slot")
        items.getInt("items.editteams.brown.slot")
        items.getInt("items.editteams.green.slot")
        items.getInt("items.editteams.red.slot")
        items.getInt("items.editteams.black.slot")
        items.getInt("items.editteams.back.slot")*/
    }
}
