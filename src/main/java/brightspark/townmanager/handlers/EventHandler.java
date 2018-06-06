package brightspark.townmanager.handlers;

import brightspark.townmanager.TownManager;
import brightspark.townmanager.messages.CheckForClientModMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = TownManager.MOD_ID)
public class EventHandler
{
    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public static void onClientJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
        //Find out if the client has the mod installed
        NetworkHandler.NETWORK.sendTo(new CheckForClientModMessage(), (EntityPlayerMP) event.player);
    }

    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public static void onClientLeave(PlayerEvent.PlayerLoggedOutEvent event)
    {
        //Remove the client
        NetworkHandler.removeClient((EntityPlayerMP) event.player);
    }
}
