package me.abein.ExplosionEnchant;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import net.md_5.bungee.api.ChatColor;

public class Listeners implements Listener {
		
	@EventHandler
	public void event(EntityShootBowEvent event) {
		ItemStack bow = event.getBow();
		if (bow.hasItemMeta() && bow.getItemMeta().hasLore()) {
			for (int i = 0; i < bow.getItemMeta().getLore().size(); i++) {
				String[] fLore = ChatColor.stripColor(bow.getItemMeta().getLore().get(i)).split(" ");
				String testLore = fLore[0];
				if (testLore.equals("Explosion")) {
					int level = Util.unnumeral(fLore[1]);
					Arrow arrow = (Arrow) event.getProjectile();
					arrow.setMetadata("enchant", new FixedMetadataValue(Main.getPlugin(Main.class), "explosion"));
					arrow.setMetadata("powerlevel", new FixedMetadataValue(Main.getPlugin(Main.class), level));
				}
			}
		}
	}
	
	@EventHandler
	public void event(ProjectileHitEvent event) {
		if (event.getEntityType() == EntityType.ARROW) {
			Arrow arrow = (Arrow) event.getEntity();	
			try {
				if (arrow.getMetadata("enchant").get(0).value().equals("explosion")) {
					Location loc = arrow.getLocation();
					Player player = (Player) arrow.getShooter();
					int powerlevel = (int) arrow.getMetadata("powerlevel").get(0).value();
					player.getWorld().createExplosion(loc, powerlevel * 2);
				}
			}
			catch(Exception e) {
			}
		}
	}
}