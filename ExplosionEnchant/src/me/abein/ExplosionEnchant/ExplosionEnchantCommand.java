package me.abein.ExplosionEnchant;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ExplosionEnchantCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("explosion")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				ItemStack item = player.getInventory().getItemInMainHand();
				Material mat = item.getType();
				
				if (args.length == 0) {
					player.sendMessage("Invalid enchant level");
					return true;
				}
				if (mat.equals(Material.BOW) && item != null) {
					ItemMeta itemMeta = item.getItemMeta();
					ArrayList<String> newLores = new ArrayList<String>();
					int level = Integer.parseInt(args[0]);
					newLores.add(ChatColor.translateAlternateColorCodes('&', "Explosion") + " " + Util.numeral(level));
					itemMeta.setLore(newLores);
					
					NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "1000");
					Glow glow = new Glow(key);
					itemMeta.addEnchant(glow, 1, true);
					item.setItemMeta(itemMeta);
					return true;
				}
				else {
					player.sendMessage("Explosion cannot be added to this item");
					return true;
				}	
			}
			else {
				return true;
			}
		}
		return false;
	}
}
