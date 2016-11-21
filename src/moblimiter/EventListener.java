package moblimiter;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EventListener implements Listener {

	private final Config config;
	public EventListener(Config config) {
		this.config = config;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onMobSpawn(CreatureSpawnEvent e) {
		Entity entity = e.getEntity();
		Chunk chunk = entity.getLocation().getChunk();
		EntityType type = entity.getType();
		if (getCurrentCreaturesCount(chunk, type) > config.getCreatureSpawnLimit(type)) {
			entity.remove();
		}
	}

	private int getCurrentCreaturesCount(Chunk chunk, EntityType type) {
		int count = 0;
		for (Entity entity : chunk.getEntities()) {
			if (entity.getType() == type) {
				count++;
			}
		}
		return count;
	}

}
