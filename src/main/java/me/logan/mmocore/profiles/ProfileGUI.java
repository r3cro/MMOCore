package me.logan.mmocore.profiles;

import me.logan.mmocore.MMOCore;
import me.logan.mmocore.utils.ItemBuilder;
import me.logan.mmocore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ProfileGUI {

    private MMOCore plugin;
    private Player player;
    private Inventory inventory;

    public ProfileGUI(MMOCore plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }



    public void open(Player targetPlayer) {
        inventory = Bukkit.getServer().createInventory(player, 54, "Your Character");

        inventory.setItem(10, new ItemBuilder(Material.WOODEN_PICKAXE).name("Mining").lore("Level: " + plugin.getProfile(targetPlayer).getMining()).build());
        inventory.setItem(11, new ItemBuilder(Material.WOODEN_HOE).name("Farming").lore("Level: " + plugin.getProfile(targetPlayer).getFarming()).build());
        inventory.setItem(12, new ItemBuilder(Material.WOODEN_AXE).name("Foraging").lore("Level: " + plugin.getProfile(targetPlayer).getForaging()).build());
        inventory.setItem(13, new ItemBuilder(Material.FISHING_ROD).name("Fishing").lore("Level: " + plugin.getProfile(targetPlayer).getFishing()).build());


        inventory.setItem(16, new ItemBuilder(Material.PLAYER_HEAD).durability(3).skullOwner(targetPlayer.getName()).name(ChatColor.GREEN + "Profile").lore(Utils.color("&7Profile: &f" + plugin.getProfile(targetPlayer).getName())).lore(Utils.color("&7Class: class")).lore(Utils.color("&7Level &6" + plugin.getProfile(targetPlayer).getLevel())).build());

        inventory.setItem(19, new ItemBuilder(Material.WOODEN_SWORD).name("Combat").lore("Level: " + plugin.getProfile(targetPlayer).getCombat()).build());
        inventory.setItem(20, new ItemBuilder(Material.BREWING_STAND).name("Alchemy").lore("Level: " + plugin.getProfile(targetPlayer).getAlchemy()).build());
        inventory.setItem(21, new ItemBuilder(Material.ENCHANTED_BOOK).name("Enchanting").lore("Level: " + plugin.getProfile(targetPlayer).getEnchanting()).build());

        player.openInventory(inventory);
    }

}
