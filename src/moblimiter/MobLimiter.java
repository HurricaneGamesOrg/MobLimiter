package moblimiter;

import org.bukkit.plugin.java.JavaPlugin;

public class MobLimiter extends JavaPlugin {

	private Config config;

	@Override
	public void onEnable() {
		config = new Config(this);
		config.loadConfig();
		getServer().getPluginManager().registerEvents(new EventListener(config), this);
		getCommand("moblimiter").setExecutor(new Commands(config));
	}

	@Override
	public void onDisable() {
		config = null;
	}

}
