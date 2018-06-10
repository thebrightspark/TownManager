package brightspark.townmanager.handlers;

import brightspark.townmanager.TownManager;
import brightspark.townmanager.messages.CheckForClientModMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = TownManager.MOD_ID, value = Side.SERVER)
public class ServerEventHandler
{
    @SubscribeEvent
    public static void onClientJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
        //Find out if the client has the mod installed
        NetworkHandler.NETWORK.sendTo(new CheckForClientModMessage(), (EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    public static void onClientLeave(PlayerEvent.PlayerLoggedOutEvent event)
    {
        //Remove the client
        NetworkHandler.removeClient((EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    public static void onChangeDim(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        //TODO: Send all data to client for new dimension on change
        TownManager.LOGGER.info("Player changing from dim %s to dim %s -> Player's world dim: %s",
                event.fromDim, event.toDim, event.player.world.provider.getDimension());
        //NetworkHandler.sendToClients(new AreaUpdateAllMessage(AreasData.get(event.)));
    }
}
