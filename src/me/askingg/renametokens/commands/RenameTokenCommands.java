package me.askingg.renametokens.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.askingg.renametokens.main.Main;
import me.askingg.renametokens.utils.Message;
import me.askingg.renametokens.utils.Misc;

public class RenameTokenCommands implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main plugin;

	public RenameTokenCommands(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("renametokens").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			Message.sender("&8(&e/RenameTokens&8) &l» &fView the help list", sender);
			Message.sender("&8(&e/RenameTokens Give&8) &l» &fGive a player a rename token or two", sender);
			Message.sender("&8(&e/RenameTokens Dev&8) &l» &fView the developer", sender);
			return true;
		} else {
			if (args[0].equalsIgnoreCase("dev")) {
				Message.sender("&8(&fDeveloper&8) &3&l» &8&l&oAsk&7&l&oin&f&l&ogg", sender);
				return true;
			}
			if (args[0].equalsIgnoreCase("give")) { // rt give player amount
				File file = new File("plugins/RenameTokens", "config.yml");
				FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
				if (sender instanceof ConsoleCommandSender
						|| sender.hasPermission(conf.getString("Permissions.Commands.Give"))) {
					if (args.length == 3) {
						Player p = Bukkit.getPlayer(args[1]);
						if (p == null) {
							Message.sender(conf.getString("Messages.InvalidPlayer").replaceAll("%target%", args[1]),
									sender);
							return false;
						} else {
							Integer x = null;
							try {
								x = Integer.parseInt(args[2]);
							} catch (Exception ex) {
								Message.sender(
										conf.getString("Messages.InvalidInteger").replaceAll("%invalid%", args[2]),
										sender);
								return false;
							}
							try {
								Message.player(
										conf.getString("Messages.ReceivedToken").replace("%amount%", x.toString())
												.replace("%renametoken%", conf.getString("TokenItem.Display")),
										p);
								Message.sender(conf.getString("Messages.GaveToken").replace("%player%", p.getName())
										.replace("%amount%", x.toString())
										.replace("%renametoken%", conf.getString("TokenItem.Display")), sender);
								p.getInventory().addItem(Misc.TokenItem(x));
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					} else {
						Message.sender(conf.getString("Messages.GiveUsage"), sender);
					}
				} else {
					Message.sender(conf.getString("Messages.NoPermission"), sender);
				}
			}
		}

		return false;
	}
}
