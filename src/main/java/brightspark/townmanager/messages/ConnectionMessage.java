package brightspark.townmanager.messages;

import brightspark.townmanager.data.AreasData;
import brightspark.townmanager.handlers.NetworkHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class ConnectionMessage implements IMessage
{
    private boolean add;
    private UUID uuid;

    public ConnectionMessage(boolean add, UUID uuid)
    {
        this.add = add;
        this.uuid = uuid;
    }

    public ConnectionMessage() {}

    @Override
    public void fromBytes(ByteBuf buf)
    {
        add = buf.readBoolean();
        long most = buf.readLong();
        long least = buf.readLong();
        uuid = new UUID(most, least);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(add);
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
    }

    public static class Handler implements IMessageHandler<ConnectionMessage, AreaUpdateAllMessage>
    {
        @Override
        public AreaUpdateAllMessage onMessage(ConnectionMessage message, MessageContext ctx)
        {
            EntityPlayerMP player = ctx.getServerHandler().player.getServer().getPlayerList().getPlayerByUUID(message.uuid);
            if(message.add)
            {
                NetworkHandler.addClient(player);
                return new AreaUpdateAllMessage(AreasData.get(player.world));
            }
            else
                NetworkHandler.removeClient(player);
            return null;
        }
    }
}
