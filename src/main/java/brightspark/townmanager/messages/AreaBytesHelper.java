package brightspark.townmanager.messages;

import brightspark.townmanager.data.Area;
import brightspark.townmanager.data.AreaBase;
import brightspark.townmanager.data.Plot;
import brightspark.townmanager.data.Town;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.UUID;

public class AreaBytesHelper
{
    private AreaBase area;
    private boolean isTown;

    //Use fromByteBuf
    private AreaBytesHelper() {}

    public static AreaBytesHelper fromByteBuf(ByteBuf buf)
    {
        AreaBytesHelper helper = new AreaBytesHelper();
        helper.isTown = buf.readBoolean();
        String name = ByteBufUtils.readUTF8String(buf);
        String parent = helper.isTown ? null : ByteBufUtils.readUTF8String(buf);
        long most = buf.readLong();
        long least = buf.readLong();
        long pos1 = buf.readLong();
        long pos2 = buf.readLong();
        if(helper.isTown)
            helper.area = new Town(name, new UUID(most, least), new Area(BlockPos.fromLong(pos1), BlockPos.fromLong(pos2)));
        else
            helper.area = new Plot(name, new UUID(most, least), new Area(BlockPos.fromLong(pos1), BlockPos.fromLong(pos2)), parent);
        return helper;
    }

    public static void toByteBuf(ByteBuf buf, AreaBase area)
    {
        boolean isTown = area instanceof Town;
        buf.writeBoolean(isTown);
        ByteBufUtils.writeUTF8String(buf, area.getName());
        if(!isTown) ByteBufUtils.writeUTF8String(buf, ((Plot) area).getTownParent());
        buf.writeLong(area.getOwner().getMostSignificantBits());
        buf.writeLong(area.getOwner().getLeastSignificantBits());
        buf.writeLong(area.getArea().getMinPos().toLong());
        buf.writeLong(area.getArea().getMaxPos().toLong());
    }

    public AreaBase getArea()
    {
        return area;
    }

    public boolean isTown()
    {
        return isTown;
    }

    public Town getAsTown()
    {
        return isTown ? (Town) area : null;
    }

    public Plot getAsPlot()
    {
        return !isTown ? (Plot) area : null;
    }
}
