package brightspark.townmanager.messages;

import brightspark.townmanager.NetworkManager;
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

    public static class Handler implements IMessageHandler<ConnectionMessage, IMessage>
    {
        @Override
        public IMessage onMessage(ConnectionMessage message, MessageContext ctx)
        {
            EntityPlayerMP player = ctx.getServerHandler().player.getServer().getPlayerList().getPlayerByUUID(message.uuid);
            if(message.add)
                NetworkManager.addClient(player);
            else
                NetworkManager.removeClient(player);
            return null;
        }
    }
}
