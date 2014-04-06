package moblimiter;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EventListener implements Listener {

	private Config config;

	public EventListener(Config config) {
		this.config = config;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onMobSpawn(CreatureSpawnEvent e) {
		Entity entity = e.getEntity();
		if (getCurrentCreaturesCount(entity) > getCreaturesSpawnLimit(entity)) {
			entity.remove();
		}
	}

	private int getCurrentCreaturesCount(Entity ent) {
		return ent.getWorld()
				.getEntitiesByClass(ent.getType().getEntityClass()).size();
	}

	private int getCreaturesSpawnLimit(Entity ent) {
		int limit = config.getCreatureSpawnLimit(ent.getType());
		if (limit != -1) {
			return limit;
		} else {
			return Integer.MAX_VALUE;
		}
	}

}
