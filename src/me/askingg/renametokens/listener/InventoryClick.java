package me.askingg.renametokens.listener;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.askingg.renametokens.utils.Format;
import me.askingg.renametokens.utils.Message;

public class InventoryClick implements Listener {
	public static HashMap<Player, ItemStack> renaming = new HashMap<Player, ItemStack>();

	@EventHandler
	public void inventoryClick(InventoryClickEvent e) {
		File file = new File("plugins/RenameTokens", "config.yml");
		FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
		Player p = (Player) e.getWhoClicked();
		ItemStack i = e.getCurrentItem();
		ItemStack c = e.getCursor();
		if (c.getType().equals(Material.AIR) || !c.getItemMeta().isUnbreakable()
				|| (i.getType().equals(Material.getMaterial(conf.getString("TokenItem.Type"))) && i.getItemMeta()
						.getDisplayName().equals(Format.color(conf.getString("TokenItem.Display"))))) {
			return;
		} else {
			if (c.getType().equals(Material.getMaterial(conf.getString("TokenItem.Type")))) {
				if (c.getItemMeta().getDisplayName().equals(Format.color(conf.getString("TokenItem.Display")))) {
					if (!i.getType().equals(Material.AIR)) {
						if (c.getAmount() == 1) {
							if (!conf.getStringList("ItemBlacklist").isEmpty()) {
								for (String str : conf.getStringList("ItemBlacklist")) {
									if (i.getType().equals(Material.getMaterial(str))) {
										Message.player(conf.getString("Messages.CannotRenameThatItem"), p);
										return;
									}
								}
							}
							e.setCancelled(true);
							renaming.put(p, i);
							p.getInventory().removeItem(c);
							p.getInventory().removeItem(i);
							p.updateInventory();
							Message.player(conf.getString("Messages.TypeTheNameYouWant"), p);
						} else {
							Message.player(conf.getString("Messages.UnstackRenameTokens"), p);
							return;
						}
					}
				}
			}
		}
	}
}
