package de.robingrether.idisguise.worldguard;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.EnumFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.SetFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;

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
			Flag<?>[] flags = Arrays.copyOf(DefaultFlag.flagsList, DefaultFlag.flagsList.length + 2);
			flags[flags.length - 2] = IDISGUISE_PLUGIN;
			flags[flags.length - 1] = IDISGUISE_BLOCKED_TYPES;
			Field flagsList = DefaultFlag.class.getDeclaredField("flagsList");
			Field modifier = Field.class.getDeclaredField("modifiers");
			int modifiers = flagsList.getModifiers();
			modifier.setAccessible(true);
			modifier.setInt(flagsList, modifiers & ~Modifier.FINAL);
			flagsList.setAccessible(true);
			flagsList.set(null, flags);
			modifier.setInt(flagsList, modifiers);
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