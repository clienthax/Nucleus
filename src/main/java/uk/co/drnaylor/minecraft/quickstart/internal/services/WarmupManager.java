package uk.co.drnaylor.minecraft.quickstart.internal.services;

import com.google.common.collect.Maps;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;
import uk.co.drnaylor.minecraft.quickstart.api.service.QuickStartWarmupManagerService;

import java.util.Map;
import java.util.UUID;

public class WarmupManager implements QuickStartWarmupManagerService {

    private final Map<UUID, Task> warmupTasks = Maps.newHashMap();

    @Override
    public void addWarmup(UUID player, Task task) {
        Task t = warmupTasks.put(player, task);
        if (t != null) {
            t.cancel();
        }
    }

    @Override
    public boolean removeWarmup(UUID player) {
        Task t = warmupTasks.remove(player);
        return t != null && t.cancel();
    }

    @Override
    public void cleanup() {
        warmupTasks.entrySet().stream().filter(v -> !Sponge.getGame().getScheduler().getScheduledTasks().contains(v.getValue()))
                .forEach(v -> warmupTasks.remove(v.getKey()));
    }
}
