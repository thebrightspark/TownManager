package brightspark.townmanager.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CheckForClientModMessage implements IMessage
{
    public CheckForClientModMessage() {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<CheckForClientModMessage, ConnectionMessage>
    {
        @Override
        public ConnectionMessage onMessage(CheckForClientModMessage message, MessageContext ctx)
        {
            return new ConnectionMessage(true, Minecraft.getMinecraft().player.getUniqueID());
        }
    }
}
