package de.robingrether.idisguise.worldguard;

import java.util.Set;
import java.util.logging.Level;

import org.bstats.bukkit.Metrics;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.EnumFlag;
import com.sk89q.worldguard.protection.flags.SetFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;

import de.robingrether.idisguise.api.DisguiseEvent;
import de.robingrether.idisguise.disguise.DisguiseType;
import de.robingrether.util.StringUtil;

public class iDisguiseWG extends JavaPlugin implements Listener {
	
	public static final StateFlag IDISGUISE_PLUGIN = new StateFlag("idisguise-plugin", true);
	public static final SetFlag<DisguiseType> IDISGUISE_BLOCKED_TYPES = new SetFlag<DisguiseType>("idisguise-blocked-types", new EnumFlag<DisguiseType>(null, DisguiseType.class));
	
	private static iDisguiseWG instance;
	
	private Language language;
	private Metrics metrics;
	
	public iDisguiseWG() { instance = this;	}
	
	public void onLoad() {
		try {
			FlagRegistry flagRegistry = WorldGuard.getInstance().getFlagRegistry();
			flagRegistry.register(IDISGUISE_PLUGIN);
			flagRegistry.register(IDISGUISE_BLOCKED_TYPES);
		} catch(Exception e) {
		}
	}
	
	public void onEnable() {
		checkDirectory();
		language = new Language(this);
		language.loadData();
		language.saveData();
		metrics = new Metrics(this);
		getServer().getPluginManager().registerEvents(this, this);
		WorldGuard.getInstance().getPlatform().getSessionManager().registerHandler(DisguiseFlagHandler.FACTORY, null);
		WorldGuard.getInstance().getPlatform().getSessionManager().registerHandler(DisguiseTypeFlagHandler.FACTORY, null);
		getLogger().log(Level.INFO, String.format("%s enabled!", getFullName()));
	}
	
	public void onDisable() {
		getLogger().log(Level.INFO, String.format("%s disabled!", getFullName()));
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
	
	public static iDisguiseWG getInstance() {
		return instance;
	}
	
	/* event listener start */
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onDisguise(DisguiseEvent event) {
		Player player = event.getPlayer();
		LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
		ApplicableRegionSet regionSet = WorldGuard.getInstance().getPlatform().getRegionContainer().get(localPlayer.getWorld()).getApplicableRegions(localPlayer.getLocation().toVector().toBlockPoint());
		if(State.DENY.equals(regionSet.queryState(localPlayer, IDISGUISE_PLUGIN))) {
			event.setCancelled(true);
			if(StringUtil.isNotBlank(getLanguage().CANCEL_DISGUISE)) {
				player.sendMessage(getLanguage().CANCEL_DISGUISE);
			}
		} else {
			Set<DisguiseType> value = regionSet.queryValue(localPlayer, IDISGUISE_BLOCKED_TYPES);
			if(value != null && value.contains(event.getDisguise().getType())) {
				event.setCancelled(true);
				if(StringUtil.isNotBlank(getLanguage().CANCEL_DISGUISE)) {
					player.sendMessage(getLanguage().CANCEL_DISGUISE);
				}
			}
		}
	}
	
	/* event listener end */
	
}