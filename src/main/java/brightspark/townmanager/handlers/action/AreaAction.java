package brightspark.townmanager.handlers.action;

import brightspark.townmanager.TownManager;
import brightspark.townmanager.data.Area;
import brightspark.townmanager.data.AreasData;
import brightspark.townmanager.data.Plot;
import brightspark.townmanager.data.Town;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.UUID;

public class AreaAction
{
    private EntityPlayerMP player;
    private Action action = null;
    private String areaName, townName;
    private World world;
    private BlockPos pos1;

    public AreaAction(EntityPlayerMP player)
    {
        this.player = player;
    }

    public boolean isPlayer(EntityPlayerMP player)
    {
        return this.player.getUniqueID().equals(player.getUniqueID());
    }

    public EntityPlayerMP getPlayer()
    {
        return player;
    }

    public Action getAction()
    {
        return action;
    }

    public boolean hasAction()
    {
        return action != null;
    }

    public String getAreaName()
    {
        return areaName;
    }

    public void setInitData(Action action, World world, String name)
    {
        setInitData(action, world, name, null);
    }

    public void setInitData(Action action, World world, String name, String townName)
    {
        this.action = action;
        this.world = world;
        areaName = name;
        this.townName = townName;
    }

    public EnumActionResult setNextPos(BlockPos pos)
    {
        if(pos1 == null)
        {
            pos1 = pos;
            return EnumActionResult.PASS;
        }

        UUID uuid = player.getUniqueID();
        boolean result = false;
        switch(action)
        {
            case CREATE_PLOT:
                result = AreasData.get(world).getTown(townName).addPlot(new Plot(areaName, uuid, new Area(pos1, pos), townName));
                break;
            case CREATE_TOWN:
                result = AreasData.get(world).addTown(new Town(areaName, uuid, new Area(pos1, pos)));
                break;
            default:
                TownManager.LOGGER.warn("Action has set its second position, but its current action is %s!", action);
        }
        AreaHandler.removeAction(uuid);

        //Send message to player depending on result
        getPlayer().sendMessage(new TextComponentTranslation("tm.action." + action.langName() + (result ? "success" : "failed"), areaName));

        return result ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
    }
}
