package brightspark.townmanager.handlers;

import brightspark.townmanager.TownManager;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber(modid = TownManager.MOD_ID)
public class AreaHandler
{
    private static Cache<UUID, AreaAction> actionCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

    public static AreaAction getAction(UUID uuid)
    {
        try
        {
            return actionCache.get(uuid, () -> new AreaAction(uuid));
        }
        catch(ExecutionException e)
        {
            TownManager.LOGGER.error("Error getting Action from cache", e);
        }
        return null;
    }

    public static void removeAction(UUID uuid)
    {
        actionCache.invalidate(uuid);
    }

    @SubscribeEvent
    public static void onBlockStartBreak(PlayerEvent.BreakSpeed event)
    {
        //TODO: Stop players from breaking blocks in protected areas
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.PlaceEvent event)
    {
        //TODO: Stop players from placing block in procteted areas
    }

    @SubscribeEvent
    public static void onPlayerJoin(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event)
    {
        //TODO: Send data to clients with the client mod installed
    }

    @SubscribeEvent
    public static void onPlayerChangeDimension(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent event)
    {
        //TODO: Send data to clients with the client mod installed
    }

    @SubscribeEvent
    public static void onEntitySpawn(LivingSpawnEvent.CheckSpawn event)
    {
        //TODO: Stop entity spawning if it's within an area that's preventing the spawning
    }
}
