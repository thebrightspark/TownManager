package brightspark.townmanager.handlers.action;

import brightspark.townmanager.TownManager;
import brightspark.townmanager.data.AreasData;
import brightspark.townmanager.data.Plot;
import brightspark.townmanager.data.Town;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber(modid = TownManager.MOD_ID)
public class AreaHandler
{
    private static Cache<UUID, AreaAction> actionCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

    public static AreaAction getAction(EntityPlayerMP player)
    {
        return actionCache.getIfPresent(player.getUniqueID());
    }

    public static void putAction(AreaAction action)
    {
        actionCache.put(action.getPlayer().getUniqueID(), action);
    }

    public static void removeAction(UUID uuid)
    {
        actionCache.invalidate(uuid);
    }

    private static void sendPosToActions(EntityPlayerMP player, BlockPos pos)
    {
        actionCache.asMap().values().stream()
                .filter(action -> action.isPlayer(player) && action.hasAction())
                .forEach(action -> action.setNextPos(pos));
    }

    private static boolean isProtected(EntityPlayer player, BlockPos pos)
    {
        AreasData data = AreasData.get(player.world);
        Set<Town> townsWithPos = data.getTownsContainingPos(pos);
        if(townsWithPos.isEmpty()) return false;
        UUID playerUuid = player.getUniqueID();
        for(Town town : townsWithPos)
            if(!town.canPlayerEdit(playerUuid))
                for(Plot plot : town.getPlots())
                    if(!plot.canPlayerEdit(playerUuid))
                        return true;
        return false;
    }

    @SubscribeEvent
    public static void onBlockStartBreak(PlayerEvent.BreakSpeed event)
    {
        EntityPlayer player = event.getEntityPlayer();

        //Stop players from breaking blocks in protected areas
        if(isProtected(player, event.getPos()))
        {
            event.setNewSpeed(0F);
            event.setCanceled(true);
            if(!player.world.isRemote)
            {
                //TODO: Notify player that block is protected
            }
        }

        if(player instanceof EntityPlayerMP)
            //Progress actions
            sendPosToActions((EntityPlayerMP) player, event.getPos());
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.PlaceEvent event)
    {
        EntityPlayer player = event.getPlayer();

        //Stop players from placing block in procteted areas
        if(isProtected(player, event.getPos()))
        {
            event.setCanceled(true);
            if(!player.world.isRemote)
            {
                //TODO: Notify player that block is protected
            }
        }
    }

    @SubscribeEvent
    public static void onEntitySpawn(LivingSpawnEvent.CheckSpawn event)
    {
        //TODO: Stop entity spawning if it's within an area that's preventing the spawning
    }
}
