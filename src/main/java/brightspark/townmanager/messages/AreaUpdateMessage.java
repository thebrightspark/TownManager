package brightspark.townmanager.messages;

import brightspark.townmanager.TownManager;
import brightspark.townmanager.data.AreaBase;
import brightspark.townmanager.data.Plot;
import brightspark.townmanager.data.Town;
import brightspark.townmanager.handlers.ClientHandler;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AreaUpdateMessage implements IMessage
{
    private AreaBase area;
    private boolean toAdd, isTown;

    public AreaUpdateMessage() {}

    public AreaUpdateMessage(AreaBase area, boolean toAdd)
    {
        this.area = area;
        this.toAdd = toAdd;
        isTown = area instanceof Town;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        toAdd = buf.readBoolean();
        AreaBytesHelper helper = AreaBytesHelper.fromByteBuf(buf);
        area = helper.getArea();
        isTown = helper.isTown();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(toAdd);
        AreaBytesHelper.toByteBuf(buf, area);
    }

    public static class Handler implements IMessageHandler<AreaUpdateMessage, IMessage>
    {
        @Override
        public IMessage onMessage(AreaUpdateMessage message, MessageContext ctx)
        {
            //Update client with plot/town change
            if(message.isTown)
            {
                if(message.toAdd)
                    ClientHandler.setTown((Town) message.area);
                else
                    ClientHandler.removeTown(message.area.getName());
            }
            else
            {
                String parent = ((Plot) message.area).getTownParent();
                Town town = ClientHandler.getTown(parent);
                if(town == null)
                    TownManager.LOGGER.warn("Could not find parent town %s for plot %s during client sync!",
                            parent, message.area.getName());
                else
                {
                    if(message.toAdd)
                    {
                        if(!town.setPlot((Plot) message.area))
                            TownManager.LOGGER.warn("Failed to set plot %s in town %s during client sync!",
                                    message.area.getName(), town.getName());
                    }
                    else
                        town.removePlot(message.area.getName());
                }
            }
            return null;
        }
    }
}
