package com.IntenseArmadillo.plottime.listener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.IntenseArmadillo.plottime.util.ConfigAccessor;
import com.IntenseArmadillo.plottime.util.Manager;
import com.intellectualcrafters.plot.object.Plot;
import com.plotsquared.bukkit.events.PlayerEnterPlotEvent;
import com.plotsquared.bukkit.events.PlayerLeavePlotEvent;

public class PlayerPlotListener implements Listener {
	@EventHandler
	public void onEnterPlot(PlayerEnterPlotEvent e) {
		Plot plot = e.getPlot();
		String path = Manager.getPlotPath(plot);
		ConfigAccessor data = Manager.getAccessor("data.yml"), user = Manager.getAccessor("user.yml");
		FileConfiguration plots = data.getConfig(), users = user.getConfig();
		if(plots.contains(path)) {
			Player p = e.getPlayer();
			if(p.getPlayerTimeOffset() != 0) {
				users.set(p.getUniqueId().toString(), p.getPlayerTime());
				user.saveConfig();
			}
			p.setPlayerTime(plots.getLong(path), false);
		}
	}
	
	@EventHandler
	public void onLeavePlot(PlayerLeavePlotEvent e) {
		Player p = e.getPlayer();
		ConfigAccessor user = Manager.getAccessor("user.yml");
		FileConfiguration users = user.getConfig();
		if(users.contains(p.getUniqueId().toString()))
			p.setPlayerTime(users.getLong(p.getUniqueId().toString()), false);
		else
			p.resetPlayerTime();
		users.set(p.getUniqueId().toString(), null);
		user.saveConfig();
	}
}