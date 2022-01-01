package me.abein.ExplosionEnchant;

import java.lang.reflect.Field;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private static Main instance;
	
	public static Main getInstance() {
		return instance;
	}
	
	
	@Override
	public void onEnable() {
		this.getCommand("explosion").setExecutor(new ExplosionEnchantCommand());
		
		registerGlow();
		
		instance = this;
		
		getServer().getPluginManager().registerEvents(new Listeners(), this);
	
	}
	
	@Override
	public void onDisable() {
		instance = null;
	}

	public void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
        	NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "1000");
            Glow glow = new Glow(key);
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException e){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
