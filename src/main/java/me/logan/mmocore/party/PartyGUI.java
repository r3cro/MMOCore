package me.logan.mmocore.party;

import lombok.Getter;
import me.logan.mmocore.MMOCore;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.UUID;

@Getter
public class PartyGUI {

    private MMOCore plugin;
    private Inventory inventory;
    private Player player;
    private Party party;

    public PartyGUI(MMOCore plugin, Player player, Party party) {
        this.plugin = plugin;
        this.player = player;
        this.party = party;
    }

    public void open() {
        inventory = Bukkit.getServer().createInventory(player, 9, party.getPartyName());

        for (int i = 0; i < party.getPartySize(); i++) {
            UUID uuid = party.getMember(i);
            Player player = Bukkit.getServer().getPlayer(uuid);

            ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();

            if (party.getPartyLeaderUUID().equals(player.getUniqueId())) {
                meta.setDisplayName("" + ChatColor.WHITE + NamedTextColor.DARK_RED + player.getName());
            } else {
                meta.setDisplayName("" + NamedTextColor.WHITE + player.getName());
            }

            meta.setOwningPlayer(player);

            lore.add(NamedTextColor.RED + "" + (int) player.getHealthScale() + "♥");

            if (player != this.player && party.isLeader(player)) {
                lore.add(" ");
                lore.add("Click to promote");
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
            getInventory().setItem(i, item);
        }

        player.openInventory(inventory);
    }

}
