package moblimiter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EventListener implements Listener {

	private Config config;
	private MobLimiter plugin;
	public EventListener(MobLimiter plugin, Config config)
	{
		this.plugin = plugin;
		this.config = config;
	}
	
	private List<Entity> entityToRemove = new ArrayList<Entity>();
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onMobSpawn(CreatureSpawnEvent e)
	{
		Entity entity = e.getEntity();
		if (getCurrentCreaturesCount(entity)>getCreaturesSpawnLimit(entity))
		{
			entityToRemove.add(entity);
		}
	}
	
	public void initEntityRemoveTask()
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run() {
				for (Entity erm : entityToRemove)
				{
					if (erm.isValid())
					{
						erm.remove();
					}
				}
				entityToRemove.clear();
			}
		}, 0, 1);
	}
	
	
	private int getCurrentCreaturesCount(Entity ent)
	{
		return ent.getWorld().getEntitiesByClass(ent.getType().getEntityClass()).size();
	}
	
	private int getCreaturesSpawnLimit(Entity ent)
	{
		int limit = config.getCreatureSpawnLimit(ent.getType());
		if (limit != -1)
		{
			return limit;
		} else
		{
			return Integer.MAX_VALUE;
		}
	}
	
}
