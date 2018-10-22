package me.askingg.renametokens.listener;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.askingg.renametokens.utils.Format;
import me.askingg.renametokens.utils.Message;

public class PlayerChat implements Listener {

	@EventHandler
	public void playerChat(AsyncPlayerChatEvent e) {
		File file = new File("plugins/RenameTokens", "config.yml");
		FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
		Player p = e.getPlayer();
		if (InventoryClick.renaming.containsKey(p)) {
			e.setCancelled(true);
			ItemStack i = InventoryClick.renaming.get(p);
			ItemMeta m = i.getItemMeta();
			m.setDisplayName(Format.color("&f" + e.getMessage()));
			i.setItemMeta(m);
			p.getInventory().addItem(i);
			p.updateInventory();
			Message.player(conf.getString("Messages.ItemRenamed").replace("%name%", Format.color(e.getMessage())), p);
			InventoryClick.renaming.remove(p);
		} else {
			return;
		}
	}
}
