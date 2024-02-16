package me.logan.mmocore.commands;

import lombok.AllArgsConstructor;
import me.logan.mmocore.MMOCore;
import me.logan.mmocore.profiles.Profile;
import me.logan.mmocore.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class DevCommand implements CommandExecutor {

    public MMOCore plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        player.sendMessage("Debug");
        player.sendActionBar("test" + " dadf");


        return true;
    }

}
