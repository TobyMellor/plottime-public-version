package com.IntenseArmadillo.plottime.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.IntenseArmadillo.plottime.util.ConfigAccessor;
import com.IntenseArmadillo.plottime.util.Manager;
import com.IntenseArmadillo.plottime.util.Messages;
import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;

public class PlotTimeCommand {
	@SuppressWarnings("deprecation")
	public static void run(CommandSender sender, Command cmd, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			switch(args.length) {
			case 1:
				if(Manager.getNumeric(args[0]) 
						|| args[0].equalsIgnoreCase("day") 
						|| args[0].equalsIgnoreCase("night")
						|| args[0].equalsIgnoreCase("reset")) {
					long ticks = 0;
					if(args[0].equalsIgnoreCase("day"))
						ticks = 6000;
					else if(args[0].equalsIgnoreCase("night"))
						ticks = 18000;
					else if(args[0].equalsIgnoreCase("reset"))
						ticks = -1;
					else
						ticks = Math.round((float) Double.parseDouble(args[0])) % 24000;
					PlotAPI api = new PlotAPI();
					Plot atPlayer = api.getPlot(p.getLocation());
					if(ticks != -1) {
						if(api.isInPlot(p)) {
							if(atPlayer.getOwners().contains(p.getUniqueId())) {
								Manager.setPlotTime(atPlayer, ticks);
								p.sendMessage(Messages.PLOT_TIME_SET);
							}
							else
								sender.sendMessage(Messages.ERROR_NOT_OWNER);
						}
						else
							sender.sendMessage(Messages.ERROR_NOT_IN_PLOT);
					}
					else {
						Manager.resetPlotTime(atPlayer);
						ConfigAccessor data = Manager.getAccessor("data.yml");
						data.getConfig().set(Manager.getPlotPath(atPlayer), null);
						data.saveConfig();
						p.sendMessage(Messages.PLOT_TIME_RESET);
					}
				}
				break;
			default:
				sender.sendMessage(Messages.USAGE_PLOTTIME);
				break;
			}
		}
	}
}