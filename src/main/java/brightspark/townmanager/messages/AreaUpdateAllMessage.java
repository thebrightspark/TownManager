package brightspark.townmanager.messages;

import brightspark.townmanager.TownManager;
import brightspark.townmanager.data.AreasData;
import brightspark.townmanager.data.Plot;
import brightspark.townmanager.data.Town;
import brightspark.townmanager.handlers.ClientHandler;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AreaUpdateAllMessage implements IMessage
{
    private Map<String, Town> towns = new HashMap<>();
    private Set<Plot> plots = new HashSet<>();

    public AreaUpdateAllMessage() {}

    public AreaUpdateAllMessage(AreasData data)
    {
        data.getTowns().forEach(town -> towns.put(town.getName(), town));
        towns.values().forEach(town -> plots.addAll(town.getPlots()));
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        int townSize = buf.readInt();
        for(int i = 0; i < townSize; i++)
        {
            Town town = AreaBytesHelper.fromByteBuf(buf).getAsTown();
            towns.put(town.getName(), town);
        }
        int plotSize = buf.readInt();
        for(int i = 0; i < plotSize; i++)
        {
            Plot plot = AreaBytesHelper.fromByteBuf(buf).getAsPlot();
            plots.add(plot);
            Town town = towns.get(plot.getTownParent());
            if(town == null)
                TownManager.LOGGER.warn("Couldn't find town '%s' during sync for plot '%s'!");
            else
                town.addPlot(plot);
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(towns.size());
        towns.values().forEach(town -> AreaBytesHelper.toByteBuf(buf, town));
        buf.writeInt(plots.size());
        plots.forEach(plot -> AreaBytesHelper.toByteBuf(buf, plot));
    }

    public static class Handler implements IMessageHandler<AreaUpdateAllMessage, IMessage>
    {
        @Override
        public IMessage onMessage(AreaUpdateAllMessage message, MessageContext ctx)
        {
            message.towns.values().forEach(ClientHandler::setTown);
            return null;
        }
    }
}
