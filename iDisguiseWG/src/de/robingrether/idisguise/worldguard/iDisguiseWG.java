package de.robingrether.idisguise.worldguard;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.EnumFlag;
import com.sk89q.worldguard.protection.flags.SetFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;

import de.robingrether.idisguise.disguise.DisguiseType;

public class iDisguiseWG extends JavaPlugin {
	
	public static final StateFlag IDISGUISE_PLUGIN = new StateFlag("idisguise-plugin", true);
	public static final SetFlag<DisguiseType> IDISGUISE_BLOCKED_TYPES = new SetFlag<DisguiseType>("idisguise-blocked-types", new EnumFlag<DisguiseType>(null, DisguiseType.class));
	
	private EventListener listener;
	private Language language;
	
	public void onEnable() {
		checkDirectory();
		registerFlag();
		listener = new EventListener(this);
		language = new Language(this);
		language.loadData();
		language.saveData();
		getServer().getPluginManager().registerEvents(listener, this);
		getLogger().log(Level.INFO, String.format("%s enabled!", getFullName()));
	}
	
	public void onDisable() {
		getLogger().log(Level.INFO, String.format("%s disabled!", getFullName()));
	}
	
	private void registerFlag() {
		try {
			FlagRegistry flagRegistry = WorldGuardPlugin.inst().getFlagRegistry();
			flagRegistry.register(IDISGUISE_PLUGIN);
			flagRegistry.register(IDISGUISE_BLOCKED_TYPES);
		} catch(Exception e) {
		}
	}
	
	private void checkDirectory() {
		if(!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
	}
	
	public String getVersion() {
		return getDescription().getVersion();
	}
	
	public String getFullName() {
		return "iDisguiseWG " + getVersion();
	}
	
	public Language getLanguage() {
		return language;
	}
	
}