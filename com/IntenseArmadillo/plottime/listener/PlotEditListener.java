package com.IntenseArmadillo.plottime.listener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.IntenseArmadillo.plottime.util.ConfigAccessor;
import com.IntenseArmadillo.plottime.util.Manager;
import com.plotsquared.bukkit.events.PlotDeleteEvent;

public class PlotEditListener implements Listener {
	@EventHandler
	public void onDelete(PlotDeleteEvent e) {
		ConfigAccessor data = Manager.getAccessor("data.yml");
		FileConfiguration dataConfig = data.getConfig();
		String path = e.getWorld() + "," + e.getPlotId().x + "," + e.getPlotId().y;
		if(dataConfig.contains(path)) {
			dataConfig.set(path, null);
			data.saveConfig();
		}
	}
}