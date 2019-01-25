package de.robingrether.idisguise.worldguard;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.FlagValueChangeHandler;
import com.sk89q.worldguard.session.handler.Handler;

import de.robingrether.idisguise.iDisguise;
import de.robingrether.idisguise.api.DisguiseAPI;
import de.robingrether.idisguise.disguise.DisguiseType;
import de.robingrether.util.StringUtil;

public class DisguiseTypeFlagHandler extends FlagValueChangeHandler<Set<DisguiseType>> {
	
	public static final Factory FACTORY = new Factory();
	
	public static class Factory extends Handler.Factory<DisguiseTypeFlagHandler> {
		public DisguiseTypeFlagHandler create(Session session) {
			return new DisguiseTypeFlagHandler(session);
		}
	}
	
	private DisguiseAPI api;
	
	public DisguiseTypeFlagHandler(Session session) {
		super(session, iDisguiseWG.IDISGUISE_BLOCKED_TYPES);
		this.api = iDisguise.getInstance().getAPI();
	}
	
	public void onInitialValue(LocalPlayer localPlayer, ApplicableRegionSet toSet, Set<DisguiseType> value) {
		if(value != null && !value.isEmpty()) {
			Player player = Bukkit.getPlayer(localPlayer.getUniqueId());
			if(player != null && api.isDisguised(player) && value.contains(api.getDisguise(player).getType())) {
				api.undisguise(player, false);
				if(StringUtil.isNotBlank(iDisguiseWG.getInstance().getLanguage().UNDISGUISE_JOIN_REGION)) {
					player.sendMessage(iDisguiseWG.getInstance().getLanguage().UNDISGUISE_JOIN_REGION);
				}
			}
		}
	}
	
	public boolean onSetValue(LocalPlayer localPlayer, Location from, Location to, ApplicableRegionSet toSet, Set<DisguiseType> currentValue, Set<DisguiseType> lastValue, MoveType moveType) {
		if(currentValue != null && !currentValue.isEmpty()) {
			Player player = Bukkit.getPlayer(localPlayer.getUniqueId());
			if(player != null && api.isDisguised(player) && currentValue.contains(api.getDisguise(player).getType())) {
				api.undisguise(player, false);
				if(StringUtil.isNotBlank(iDisguiseWG.getInstance().getLanguage().UNDISGUISE_ENTER_REGION)) {
					player.sendMessage(iDisguiseWG.getInstance().getLanguage().UNDISGUISE_ENTER_REGION);
				}
			}
		}
		return true;
	}
	
	public boolean onAbsentValue(LocalPlayer player, Location from, Location to, ApplicableRegionSet toSet, Set<DisguiseType> lastValue, MoveType moveType) {
		return true;
	}
	
}