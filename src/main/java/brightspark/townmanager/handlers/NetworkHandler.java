package brightspark.townmanager.handlers;

import brightspark.townmanager.TownManager;
import brightspark.townmanager.messages.CheckForClientModMessage;
import brightspark.townmanager.messages.ConnectionMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.Set;

public class NetworkHandler
{
    public static SimpleNetworkWrapper NETWORK;
    private static int discriminator = 0;

    private static <REQ extends IMessage, REPLY extends IMessage> void regMessage(Class<? extends IMessageHandler<REQ, REPLY>> handler, Class<REQ> message, Side receiver)
    {
        NETWORK.registerMessage(handler, message, discriminator++, receiver);
    }

    public static void init()
    {
        NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(TownManager.MOD_ID);
        regMessage(CheckForClientModMessage.Handler.class, CheckForClientModMessage.class, Side.CLIENT);
        regMessage(ConnectionMessage.Handler.class, ConnectionMessage.class, Side.SERVER);
    }

    // +-------------+
    // | SERVER SIDE |
    // +-------------+

    //These are the clients which have the Town Manager Client mod installed
    private static Set<EntityPlayerMP> CLIENTS = new HashSet<>();

    @SideOnly(Side.SERVER)
    public static boolean hasClient(EntityPlayerMP client)
    {
        return CLIENTS.contains(client);
    }

    @SideOnly(Side.SERVER)
    public static void addClient(EntityPlayerMP client)
    {
        if(CLIENTS.add(client)) TownManager.LOGGER.info("Added client " + client.getName());
    }

    @SideOnly(Side.SERVER)
    public static void removeClient(EntityPlayerMP client)
    {
        if(CLIENTS.remove(client)) TownManager.LOGGER.info("Removed client " + client.getName());
    }

    @SideOnly(Side.SERVER)
    public static void sendToClients(IMessage message)
    {
        CLIENTS.forEach(client -> NETWORK.sendTo(message, client));
    }

    // +-------------+
    // | CLIENT SIDE |
    // +-------------+

    public static boolean canSendToServer;

    @SideOnly(Side.CLIENT)
    public static void sendToServer(IMessage message)
    {
        if(canSendToServer) NETWORK.sendToServer(message);
    }
}
