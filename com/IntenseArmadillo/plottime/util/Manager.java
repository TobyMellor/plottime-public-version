package com.IntenseArmadillo.plottime.util;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import com.IntenseArmadillo.plottime.PlotTimePlugin;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;

public class Manager {
	public static ConfigAccessor getAccessor(String fileName) {
		return new ConfigAccessor(PlotTimePlugin.getPlugin(), fileName);
	}
	
	public static void message(String message) {
		Bukkit.getConsoleSender().sendMessage(message);
	}
	
	public static void log(String message) {
		Bukkit.getLogger().info(message);
	}
	
	public static boolean getNumeric(String s) {
		try {
			Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public static void setPlotTime(Plot plot, long ticks) {
		ConfigAccessor data = getAccessor("data.yml");
		FileConfiguration config = data.getConfig();
		String path = plot.getHome().getWorld() + "," + plot.getId().x + "," + plot.getId().y;
		config.set(path, ticks);
		data.saveConfig();
		for(PlotPlayer p : plot.getPlayersInPlot())
			Bukkit.getPlayer(p.getUUID()).setPlayerTime(ticks, false);
	}
	
	public static void resetPlotTime(Plot plot) {
		for(PlotPlayer p : plot.getPlayersInPlot())
			Bukkit.getPlayer(p.getUUID()).resetPlayerTime();
	}
	
	public static String getPlotPath(Plot plot) {
		String path = plot.getHome().getWorld() + "," + plot.getId().x + "," + plot.getId().y;
		return path;
	}
}