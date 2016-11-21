package moblimiter;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	private final Config config;
	public Commands(Config config) {
		this.config = config;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (isAllowed(sender)) {
			if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
				config.loadConfig();
				sender.sendMessage("Configuration reloaded");
				return true;
			} else if (args.length == 2 && args[0].equalsIgnoreCase("setmonsterspawnlimit")) {
				for (World world : Bukkit.getWorlds()) {
					world.setMonsterSpawnLimit(Integer.valueOf(args[1]));
				}
				sender.sendMessage("Monter spawn limit set");
				return true;
			} else if (args.length == 2 && args[0].equalsIgnoreCase("setanimalspawnlimit")) {
				for (World world : Bukkit.getWorlds()) {
					world.setAnimalSpawnLimit(Integer.valueOf(args[1]));
				}
				sender.sendMessage("Animal spawn limit set");
				return true;
			}
		}
		return false;
	}

	private boolean isAllowed(CommandSender sender) {
		if (sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender) {
			return true;
		} else if (sender instanceof Player && sender.hasPermission("MobLimiter.admin")) {
			return true;
		}
		return false;
	}

}
