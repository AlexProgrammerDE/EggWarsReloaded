package me.alexprogrammerde.eggwarsreloaded.utils;

import me.alexprogrammerde.eggwarsreloaded.game.collection.TeamColor;
import me.alexprogrammerde.eggwarsreloaded.game.shop.ItemPrice;
import me.alexprogrammerde.eggwarsreloaded.game.shop.ShopItems;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Only recommended for use in guis. Not giving those items to actual players.
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class ItemBuilder {
    private final Material material;
    boolean enchant = false;
    private String name = null;
    private List<String> lore = new ArrayList<>();
    private int amount = 1;

    /**
     * Build a brand new item!
     *
     * @param material The material to use
     */
    public ItemBuilder(Material material) {
        this.material = material;
    }

    /**
     * Build a brand new shop item!
     *
     * @param item The shop item to produce
     */
    public ItemBuilder(ShopItems item, TeamColor color) {
        this.material = item.getMaterial(color);
        lore(item);
        amount(item.getAmount());
    }

    /**
     * Create a modified version of an iem!
     *
     * @param item The item to modify
     */
    public ItemBuilder(ItemStack item) {
        this.material = item.getType();
        this.amount = item.getAmount();

        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();

            assert meta != null;
            if (meta.hasDisplayName()) {
                name = meta.getDisplayName();
            }

            if (meta.hasLore()) {
                lore = meta.getLore();
            }
        }
    }

    public ItemBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder lore(String lore) {
        this.lore.add(lore);
        return this;
    }

    public ItemBuilder lore(ShopItems sItem) {
        ItemPrice price = sItem.getPrice();

        if (price.getDiamonds() > 0) {
            this.lore.add(ChatColor.AQUA + "Diamonds: " + price.getDiamonds());
        }

        if (price.getGold() > 0) {
            this.lore.add(ChatColor.GOLD + "Gold: " + price.getGold());
        }

        if (price.getIron() > 0) {
            this.lore.add(ChatColor.GRAY + "Iron: " + price.getIron());
        }

        if (price.getIron() == 0 && price.getGold() == 0 && price.getDiamonds() == 0) {
            this.lore.add(ChatColor.BLUE + "Get it free today!");
        }

        return this;
    }

    public ItemBuilder enchant() {
        this.enchant = true;
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(material);

        item.setAmount(amount);

        ItemMeta meta = item.getItemMeta();

        assert meta != null;

        if (name != null) {
            meta.setDisplayName(name);
        }

        if (!lore.isEmpty()) {
            meta.setLore(lore);
        }

        if (enchant) {
            meta.addEnchant(Enchantment.DURABILITY, 0, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        item.setItemMeta(meta);

        return item;
    }
}
