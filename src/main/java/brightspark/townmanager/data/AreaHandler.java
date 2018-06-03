package brightspark.townmanager.data;

import brightspark.townmanager.TownManager;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = TownManager.MOD_ID)
public class AreaHandler
{
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
