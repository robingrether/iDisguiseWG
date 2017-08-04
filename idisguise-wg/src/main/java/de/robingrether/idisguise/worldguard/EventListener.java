package de.robingrether.idisguise.worldguard;

import java.util.Set;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import de.robingrether.idisguise.iDisguise;
import de.robingrether.idisguise.api.DisguiseAPI;
import de.robingrether.idisguise.api.DisguiseEvent;
import de.robingrether.idisguise.disguise.DisguiseType;
import de.robingrether.util.StringUtil;

public class EventListener implements Listener {
	
	private iDisguiseWG plugin;
	private DisguiseAPI api;
	
	public EventListener(iDisguiseWG plugin) {
		this.plugin = plugin;
		this.api = iDisguise.getInstance().getAPI();
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(api.isDisguised((OfflinePlayer)player)) {
			if(WorldGuardPlugin.inst().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).queryState(null, iDisguiseWG.IDISGUISE_PLUGIN).equals(State.DENY)) {
				api.undisguise(player, false);
				if(StringUtil.isNotBlank(plugin.getLanguage().UNDISGUISE_ENTER_REGION)) {
					player.sendMessage(plugin.getLanguage().UNDISGUISE_ENTER_REGION);
				}
			} else {
				Set<DisguiseType> blockedDisguiseTypes = WorldGuardPlugin.inst().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).queryValue(null, iDisguiseWG.IDISGUISE_BLOCKED_TYPES);
				if(blockedDisguiseTypes != null && blockedDisguiseTypes.contains(api.getDisguise((OfflinePlayer)player).getType())) {
					api.undisguise(player, false);
					if(StringUtil.isNotBlank(plugin.getLanguage().UNDISGUISE_ENTER_REGION)) {
						player.sendMessage(plugin.getLanguage().UNDISGUISE_ENTER_REGION);
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		if(api.isDisguised((OfflinePlayer)player)) {
			if(WorldGuardPlugin.inst().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).queryState(null, iDisguiseWG.IDISGUISE_PLUGIN).equals(StateFlag.State.DENY)) {
				api.undisguise(player, false);
				if(StringUtil.isNotBlank(plugin.getLanguage().UNDISGUISE_ENTER_REGION)) {
					player.sendMessage(plugin.getLanguage().UNDISGUISE_ENTER_REGION);
				}
			} else {
				Set<DisguiseType> blockedDisguiseTypes = WorldGuardPlugin.inst().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).queryValue(null, iDisguiseWG.IDISGUISE_BLOCKED_TYPES);
				if(blockedDisguiseTypes != null && blockedDisguiseTypes.contains(api.getDisguise((OfflinePlayer)player).getType())) {
					api.undisguise(player, false);
					if(StringUtil.isNotBlank(plugin.getLanguage().UNDISGUISE_ENTER_REGION)) {
						player.sendMessage(plugin.getLanguage().UNDISGUISE_ENTER_REGION);
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		Player player = event.getPlayer();
		if(api.isDisguised((OfflinePlayer)player)) {
			if(WorldGuardPlugin.inst().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).queryState(null, iDisguiseWG.IDISGUISE_PLUGIN).equals(StateFlag.State.DENY)) {
				api.undisguise(player, false);
				if(StringUtil.isNotBlank(plugin.getLanguage().UNDISGUISE_ENTER_REGION)) {
					player.sendMessage(plugin.getLanguage().UNDISGUISE_ENTER_REGION);
				}
			} else {
				Set<DisguiseType> blockedDisguiseTypes = WorldGuardPlugin.inst().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).queryValue(null, iDisguiseWG.IDISGUISE_BLOCKED_TYPES);
				if(blockedDisguiseTypes != null && blockedDisguiseTypes.contains(api.getDisguise((OfflinePlayer)player).getType())) {
					api.undisguise(player, false);
					if(StringUtil.isNotBlank(plugin.getLanguage().UNDISGUISE_ENTER_REGION)) {
						player.sendMessage(plugin.getLanguage().UNDISGUISE_ENTER_REGION);
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(api.isDisguised((OfflinePlayer)player)) {
			if(WorldGuardPlugin.inst().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).queryState(null, iDisguiseWG.IDISGUISE_PLUGIN).equals(StateFlag.State.DENY)) {
				api.undisguise(player, false);
				if(StringUtil.isNotBlank(plugin.getLanguage().UNDISGUISE_JOIN_REGION)) {
					player.sendMessage(plugin.getLanguage().UNDISGUISE_JOIN_REGION);
				}
			} else {
				Set<DisguiseType> blockedDisguiseTypes = WorldGuardPlugin.inst().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).queryValue(null, iDisguiseWG.IDISGUISE_BLOCKED_TYPES);
				if(blockedDisguiseTypes != null && blockedDisguiseTypes.contains(api.getDisguise((OfflinePlayer)player).getType())) {
					api.undisguise(player, false);
					if(StringUtil.isNotBlank(plugin.getLanguage().UNDISGUISE_JOIN_REGION)) {
						player.sendMessage(plugin.getLanguage().UNDISGUISE_JOIN_REGION);
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDisguise(DisguiseEvent event) {
		Player player = event.getPlayer();
		if(WorldGuardPlugin.inst().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).queryState(null, iDisguiseWG.IDISGUISE_PLUGIN).equals(StateFlag.State.DENY)) {
			event.setCancelled(true);
			if(StringUtil.isNotBlank(plugin.getLanguage().CANCEL_DISGUISE)) {
				player.sendMessage(plugin.getLanguage().CANCEL_DISGUISE);
			}
		} else {
			Set<DisguiseType> blockedDisguiseTypes = WorldGuardPlugin.inst().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).queryValue(null, iDisguiseWG.IDISGUISE_BLOCKED_TYPES);
			if(blockedDisguiseTypes != null && blockedDisguiseTypes.contains(event.getDisguise().getType())) {
				event.setCancelled(true);
				if(StringUtil.isNotBlank(plugin.getLanguage().CANCEL_DISGUISE)) {
					player.sendMessage(plugin.getLanguage().CANCEL_DISGUISE);
				}
			}
		}
	}
	
}