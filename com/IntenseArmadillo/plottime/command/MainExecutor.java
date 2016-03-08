package com.IntenseArmadillo.plottime.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainExecutor implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("plottime"))
			PlotTimeCommand.run(sender, cmd, args);
		return false;
	}

}