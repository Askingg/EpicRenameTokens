package me.askingg.renametokens.main;

import java.io.File;
import java.util.Arrays;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.askingg.renametokens.commands.RenameTokenCommands;
import me.askingg.renametokens.listener.InventoryClick;
import me.askingg.renametokens.listener.PlayerChat;
import me.askingg.renametokens.utils.Message;

public class Main extends JavaPlugin {
	@SuppressWarnings("unused")
	private RenameTokenCommands rtcmd;

	public void onEnable() {
		createFolders();
		Message.console("&fPlugin successfully loaded");
		getServer().getPluginManager().registerEvents(new InventoryClick(), this);
		getServer().getPluginManager().registerEvents(new PlayerChat(), this);
		rtcmd = new RenameTokenCommands(this);
	}

	private void createFolders() {
		File folder = new File("plugins/RenameTokens");
		if (!folder.exists()) {
			folder.mkdirs();
			Message.console("&fThe '&bRenameTokens&f' folder was successfully created");
		}
		File file = new File("plugins/RenameTokens", "config.yml");
		FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
		if (!(file.exists())) {
			try {
				file.createNewFile();
				conf.set("Prefix", "&8(&aRenameTokens&8) &l»");
				conf.set("Messages.NoPermission", "%prefix% &fSorry, but you don't have permission to do this");
				conf.set("Messages.ReceivedToken", "%prefix% &fYou received %amount% %renametoken%");
				conf.set("Messages.GaveToken", "%prefix% &fYou gave &b%amount% %renametoken%&fs to &b%player%");
				conf.set("Messages.InvalidPlayer", "%prefix% &fSorry, but &c%target%&f is an invalid player");
				conf.set("Messages.InvalidInteger", "%prefix% &fSorry, but &c%invalid%&f is an invalid integer");
				conf.set("Messages.GiveUsage", "%prefix% &e/RenameTokens Give Player Amount");
				conf.set("Messages.CannotRenameThatItem", "%prefix% &fSorry, but this item can't be renamed");
				conf.set("Messages.UnstackRenameTokens", "%prefix% &fSorry, but you must unstack your tokens in order to use them");
				conf.set("Messages.TypeTheNameYouWant", "%prefix% &fPlease type the name you wish to rename your item to in chat");
				conf.set("Messages.ItemRenamed", "%prefix% &fYou renamed your item to &f'%name%&f'");
				conf.set("Permissions.Commands.Give", "renametokens.tokenitem.give");
				conf.set("TokenItem.Display", "&8&l« &aRename Token &8&l»");
				conf.set("TokenItem.Type", "NAME_TAG");
				conf.set("TokenItem.TypeId", 0);
				conf.set("TokenItem.Glow", true);
				conf.set("TokenItem.Lore",
						Arrays.asList("", "&8&l» &fDrag and drop onto the item", "&8&l» &fyou want to rename",
								"&8&l» &fThen type the name you wish", "&8&l» &fTo rename it to in chat"));
				conf.set("ItemBlacklist",
						Arrays.asList("TRIPWIRE_HOOK"));
				conf.save(file);
				Message.console("&fThe '&bconfig.yml&f' file was successfully created");
			} catch (Exception e) {
			}
		}
	}
}
