package me.askingg.renametokens.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Misc {

	public static ItemStack TokenItem(Integer amount) {
		File file = new File("plugins/RenameTokens", "config.yml");
		FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
		ItemStack i = new ItemStack(Material.getMaterial(conf.getString("TokenItem.Type")), amount);
		ItemMeta m = i.getItemMeta();
		List<String> l = new ArrayList<String>();
		m.setDisplayName(Format.color(conf.getString("TokenItem.Display")));
		if (conf.getBoolean("TokenItem.Glow") == true) {
			m.addEnchant(Enchantment.LUCK, 0, true);
			m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		if (conf.getStringList("TokenItem.Lore").size() > 0) {
			for (String st : conf.getStringList("TokenItem.Lore")) {
				l.add(Format.color(st));
			}
		}
		i.setDurability((byte) conf.getInt("TokenItem.TypeId"));
		m.setUnbreakable(true);
		m.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		m.setLore(l);
		i.setItemMeta(m);
		return i;
	}
}
