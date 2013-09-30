package moblimiter;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class MobLimiter extends JavaPlugin {

	private Config config;
	private EventListener listener;
	private Commands commands;
	
	@Override
	public void onEnable()
	{
		config = new Config(this);
		config.loadConfig();
		listener = new EventListener(this, config);
		listener.initEntityRemoveTask();
		getServer().getPluginManager().registerEvents(listener, this);
		commands = new Commands(config);
		getCommand("moblimiter").setExecutor(commands);
	}
	
	@Override
	public void onDisable()
	{
		HandlerList.unregisterAll(this);
		listener = null;
		commands = null;
		config = null;
	}
	
	
}
