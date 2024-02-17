package me.logan.mmocore.commands;

import lombok.AllArgsConstructor;
import me.logan.mmocore.MMOCore;
import me.logan.mmocore.party.Party;
import me.logan.mmocore.party.PartyGUI;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class PartyCommand implements CommandExecutor {

    private final MMOCore plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("party")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Must be a player");
                return true;
            }

            Player player = (Player) sender;
            Party party = plugin.getPartyManager().getParty(player.getUniqueId());

            if (args.length == 0) {
                if (party != null) {
                    PartyGUI partyGUI = new PartyGUI(this.plugin, player, party);
                    partyGUI.openPartyGUI();
                    player.sendMessage("opening party gui");
                    return true;
                }
                player.sendMessage("Not in a group /party invite <player>");
                return true;
            }

            switch (args[0].toLowerCase()) {
                case "help":
                    player.sendMessage("asdf");
                    return true;
                case "leave":
                    if (party != null) {
                        party.removePartyMember(player);
                    } else {
                        player.sendMessage("not in a party");
                    }
                    return true;
                case "invite":
                    if (args.length == 1) {
                        player.sendMessage("errorr");
                        return true;
                    }

                    Player invitedPlayer = Bukkit.getServer().getPlayer(args[1]);
                    if (invitedPlayer == null) {
                        player.sendMessage("error");
                        return true;
                    }

                    if (invitedPlayer.isOnline() && invitedPlayer != player) {
                        Party otherParty = plugin.getPartyManager().getParty(invitedPlayer.getUniqueId());

                        if (otherParty != null && plugin.getPartyManager().isInvited(invitedPlayer.getUniqueId())) {
                            player.sendMessage("error");
                            return true;
                        }

                        if (party != null) {
                            if (party.isPartyFull()) {
                                player.sendMessage("full");
                                return true;
                            }
                            invite(party, player, invitedPlayer);
                        } else {
                            party = new Party(player);
                            plugin.getPartyManager().addParty(party);
                            invite(party, player, invitedPlayer);
                        }
                    } else {
                        player.sendMessage("error");
                    }
                    return true;
                case "accept":
                    if (this.plugin.getPartyManager().isInvited(player.getUniqueId())) {
                        Party p = this.plugin.getPartyManager().getInvitationParty(player.getUniqueId());

                        if(p != null) {
                            plugin.getPartyManager().removeInvite(player.getUniqueId());
                            p.addPartyMember(player);
                            player.sendMessage("player ing roup");
                        } else {
                            player.sendMessage("error");
                        }
                    }
                    return true;
                case "decline":
                    if (plugin.getPartyManager().isInvited(player.getUniqueId())) {
                        Party p = plugin.getPartyManager().getInvitationParty(player.getUniqueId());

                        plugin.getPartyManager().removeInvite(player.getUniqueId());

                        if (p.getPartySize() == 1) {
                            Player leader = p.getLeader();
                            Party.dissolve(p);
                        }
                    } else {
                        player.sendMessage("error");
                    }
                    return true;
                case "kick":
                    if (party != null) {
                        if (args.length == 1) {
                            player.sendMessage("error");
                            return true;
                        }

                        Player kickedPartyMember = Bukkit.getServer().getPlayer(args[1]);

                        if (kickedPartyMember != null && kickedPartyMember.isOnline()) {
                            if (party != null) {
                                if (party.isLeader(player)) {
                                    party.removePartyMember(kickedPartyMember);
                                    player.sendMessage("kicked");
                                } else {
                                    player.sendMessage("error");
                                }
                            }
                        } else {
                            player.sendMessage("error");
                        }
                    } else {
                        player.sendMessage("error");
                    }
                    return true;
                case "leader":
                    if (party != null) {
                        if (args.length == 1) {
                            player.sendMessage("error");
                            return true;
                        }

                        if (!party.isLeader(player)) {
                            player.sendMessage("error");
                            return true;
                        }

                        Player newPartyLeader = Bukkit.getServer().getPlayer(args[1]);

                        if (newPartyLeader == null) {
                            player.sendMessage("error");
                            return true;
                        }

                        if(newPartyLeader.isOnline()) {
                            if (party.isPartyMember(newPartyLeader.getUniqueId())) {
                                party.setPartyLeader(newPartyLeader);
                            } else {
                                player.sendMessage("error");
                            }
                        } else {
                            player.sendMessage("error");
                        }
                    } else {
                        player.sendMessage("error");
                    }
                    return true;
                default:
                    player.sendMessage("/party help");
            }
        }

        return false;
    }

    public void invite(final Party party, final Player partyLeader, final Player player) {
        if (!party.isPartyMember(player.getUniqueId())) {
            if (!plugin.getPartyManager().isInvited(player.getUniqueId())) {
                player.sendMessage(NamedTextColor.YELLOW + "has invite");
                plugin.getPartyManager().addInvite(player.getUniqueId(), party);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (plugin.getPartyManager().isInvited(player.getUniqueId())) {
                            plugin.getPartyManager().removeInvite(player.getUniqueId());
                            player.sendMessage("ivnite old");

                            if (party.getPartySize() == 1) {
                                Party.dissolve(party);
                            }
                        }
                    }
                }.runTaskLater(plugin, 20L * 30);
            } else {
                partyLeader.sendMessage("Invite expired");
            }
        } else {
            partyLeader.sendMessage("already in party");
        }
    }
}
