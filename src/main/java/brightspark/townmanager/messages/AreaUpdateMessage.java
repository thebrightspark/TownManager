package brightspark.townmanager.messages;

import brightspark.townmanager.TownManager;
import brightspark.townmanager.data.Area;
import brightspark.townmanager.data.AreaBase;
import brightspark.townmanager.data.Plot;
import brightspark.townmanager.data.Town;
import brightspark.townmanager.handlers.ClientHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class AreaUpdateMessage implements IMessage
{
    private AreaBase area;
    private boolean toAdd, isTown;
    private String plotParentTown;

    public AreaUpdateMessage() {}

    public AreaUpdateMessage(AreaBase area, boolean toAdd)
    {
        this.area = area;
        this.toAdd = toAdd;
        isTown = area instanceof Town;
        if(!isTown)
            plotParentTown = ((Plot) area).getTownParent();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        toAdd = buf.readBoolean();
        isTown = buf.readBoolean();
        String name = ByteBufUtils.readUTF8String(buf);
        if(!isTown) plotParentTown = ByteBufUtils.readUTF8String(buf);
        long most = buf.readLong();
        long least = buf.readLong();
        long pos1 = buf.readLong();
        long pos2 = buf.readLong();
        if(isTown)
            area = new Town(name, new UUID(most, least), new Area(BlockPos.fromLong(pos1), BlockPos.fromLong(pos2)));
        else
            area = new Plot(name, new UUID(most, least), new Area(BlockPos.fromLong(pos1), BlockPos.fromLong(pos2)), plotParentTown);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(toAdd);
        buf.writeBoolean(isTown);
        ByteBufUtils.writeUTF8String(buf, area.getName());
        if(!isTown) ByteBufUtils.writeUTF8String(buf, plotParentTown);
        buf.writeLong(area.getOwner().getMostSignificantBits());
        buf.writeLong(area.getOwner().getLeastSignificantBits());
        buf.writeLong(area.getArea().getMinPos().toLong());
        buf.writeLong(area.getArea().getMaxPos().toLong());
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
                Town town = ClientHandler.getTown(message.plotParentTown);
                if(town == null)
                    TownManager.LOGGER.warn("Could not find parent town %s for plot %s during client sync!",
                            message.plotParentTown, message.area.getName());
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
