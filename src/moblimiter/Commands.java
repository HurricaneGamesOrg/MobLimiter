package moblimiter;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;


public class Commands implements CommandExecutor {

	private Config config;
	public Commands(Config config)
	{
		this.config = config;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if (isAllowed(sender))
		{
			if (args.length == 1 && args[0].equalsIgnoreCase("reload"))
			{
				config.loadConfig();
				sender.sendMessage("Configuration reloaded");
				return true;
			} else
			if (args.length == 1 && args[0].equalsIgnoreCase("count"))
			{
				if (sender instanceof Player)
				{
					Player player = (Player) sender;
					for (EntityType etype : EntityType.values())
					{
						if (etype.isAlive())
						{
							int ccount = player.getWorld().getEntitiesByClass(etype.getEntityClass()).size();
							int limit = config.getCreatureSpawnLimit(etype);
							StringBuilder message = new StringBuilder(50);
							message.append(ChatColor.BLUE+etype.getName());
							message.append(": ");
							message.append(ChatColor.RED+String.valueOf(ccount));
							if (limit != -1)
							{
								message.append(ChatColor.GREEN+" / ");
								message.append(ChatColor.GOLD+String.valueOf(limit));
							}					
							player.sendMessage(message.toString());
						}
					}
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isAllowed(CommandSender sender)
	{
		if (sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender)
		{
			return true;
		} else
		if (sender instanceof Player && sender.hasPermission("MobLimiter.admin"))
		{
			return true;
		}
		return false;
	}
	
}
