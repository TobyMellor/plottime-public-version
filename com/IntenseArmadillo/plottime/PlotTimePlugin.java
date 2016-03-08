package com.IntenseArmadillo.plottime;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.IntenseArmadillo.plottime.command.MainExecutor;
import com.IntenseArmadillo.plottime.listener.PlayerPlotListener;
import com.IntenseArmadillo.plottime.listener.PlotEditListener;
import com.IntenseArmadillo.plottime.util.Manager;
import com.intellectualcrafters.plot.api.PlotAPI;

public class PlotTimePlugin extends JavaPlugin {
	private static Plugin instance;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		instance = this;
		Manager.getAccessor("data.yml").saveDefaultConfig();
		registerListeners();
		registerCommands();
		PlotAPI api = new PlotAPI();
		for(String s : api.getPlotWorlds())
			Bukkit.getWorld(s).setGameRuleValue("doDaylightCycle", "false");
		Manager.message("§a" + getTag() + "§ahas been Enabled!");
	}
	
	@Override
	public void onDisable() {
		Manager.message("§a" + getTag() + "§ahas been Disabled!");
		instance = null;
	}
	
	public static Plugin getPlugin() {
		return instance;
	}
	
	public static String getTag() {
		String r = getPlugin().getName() + " v" + getPlugin().getDescription().getVersion();
		return r;
	}
	
	private void registerCommands() {
		List<String> list = new ArrayList<String>();
		list.add("plottime");
		for(String s : list)
			getCommand(s).setExecutor(new MainExecutor());
	}
	
	private void registerListeners() {
		List<Listener> list = new ArrayList<Listener>();
		list.add(new PlayerPlotListener());
		list.add(new PlotEditListener());
		for(Listener l : list)
			Bukkit.getPluginManager().registerEvents(l, this);
	}
	
}
