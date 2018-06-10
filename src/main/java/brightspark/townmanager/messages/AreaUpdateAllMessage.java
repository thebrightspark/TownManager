package brightspark.townmanager.messages;

import brightspark.townmanager.data.AreasData;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//TODO: AreaUpdateAllMessage
public class AreaUpdateAllMessage implements IMessage
{
    public AreaUpdateAllMessage() {}

    public AreaUpdateAllMessage(AreasData data)
    {

    }

    @Override
    public void fromBytes(ByteBuf buf)
    {

    }

    @Override
    public void toBytes(ByteBuf buf)
    {

    }

    public static class Handler implements IMessageHandler<AreaUpdateAllMessage, IMessage>
    {
        @Override
        public IMessage onMessage(AreaUpdateAllMessage message, MessageContext ctx)
        {

            return null;
        }
    }
}
