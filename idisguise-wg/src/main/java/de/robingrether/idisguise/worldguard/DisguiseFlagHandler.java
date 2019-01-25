package de.robingrether.idisguise.worldguard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.FlagValueChangeHandler;
import com.sk89q.worldguard.session.handler.Handler;

import de.robingrether.idisguise.iDisguise;
import de.robingrether.idisguise.api.DisguiseAPI;
import de.robingrether.util.StringUtil;

public class DisguiseFlagHandler extends FlagValueChangeHandler<State> {
	
	public static final Factory FACTORY = new Factory();
	
	public static class Factory extends Handler.Factory<DisguiseFlagHandler> {
		public DisguiseFlagHandler create(Session session) {
			return new DisguiseFlagHandler(session);
		}
	}
	private DisguiseAPI api;
	
	public DisguiseFlagHandler(Session session) {
		super(session, iDisguiseWG.IDISGUISE_PLUGIN);
		this.api = iDisguise.getInstance().getAPI();
	}
	
	public void onInitialValue(LocalPlayer localPlayer, ApplicableRegionSet toSet, State value) {
		if(State.DENY.equals(value)) {	
			Player player = Bukkit.getPlayer(localPlayer.getUniqueId());
			if(player != null && api.isDisguised(player)) {
				api.undisguise(player, false);
				if(StringUtil.isNotBlank(iDisguiseWG.getInstance().getLanguage().UNDISGUISE_JOIN_REGION)) {
					player.sendMessage(iDisguiseWG.getInstance().getLanguage().UNDISGUISE_JOIN_REGION);
				}
			}
		}
	}
	
	public boolean onSetValue(LocalPlayer localPlayer, Location from, Location to, ApplicableRegionSet toSet, State currentValue, State lastValue, MoveType moveType) {
		if(State.DENY.equals(currentValue)) {	
			Player player = Bukkit.getPlayer(localPlayer.getUniqueId());
			if(player != null && api.isDisguised(player)) {
				api.undisguise(player, false);
				if(StringUtil.isNotBlank(iDisguiseWG.getInstance().getLanguage().UNDISGUISE_ENTER_REGION)) {
					player.sendMessage(iDisguiseWG.getInstance().getLanguage().UNDISGUISE_ENTER_REGION);
				}
			}
		}
		return true;
	}
	
	public boolean onAbsentValue(LocalPlayer player, Location from, Location to, ApplicableRegionSet toSet, State lastValue, MoveType moveType) {
		return true;
	}
	
}