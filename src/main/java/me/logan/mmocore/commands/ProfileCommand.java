package me.logan.mmocore.commands;

import lombok.AllArgsConstructor;
import me.logan.mmocore.MMOCore;
import me.logan.mmocore.party.Party;
import me.logan.mmocore.party.PartyGUI;
import me.logan.mmocore.profiles.ProfileGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@AllArgsConstructor
public class ProfileCommand implements CommandExecutor {

    private MMOCore plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("profile")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Must be a player");
                return true;
            }

            Player player = (Player) sender;

            ProfileGUI profileGUI = new ProfileGUI(plugin, player);

            if (args.length == 0) {
                    profileGUI.open(player);
                    player.sendMessage("Opening your character's profile");
                    return true;
            }

            if(args.length == 1) {
                Player targetPlayer = Bukkit.getPlayer(args[0]);

                if (targetPlayer != null) {
                    profileGUI.open(targetPlayer);
                    return true;
                }

                player.sendMessage(targetPlayer.getName() + " is not online or valid");

                return true;
            }

        }

        return false;
    }
}
