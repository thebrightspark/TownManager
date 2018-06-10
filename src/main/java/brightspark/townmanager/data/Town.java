package brightspark.townmanager.data;

import brightspark.townmanager.handlers.NetworkHandler;
import brightspark.townmanager.messages.AreaUpdateMessage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class Town extends AreaBase
{
    private Set<Plot> plots = new HashSet<>();

    public Town(String name, UUID owner, Area area)
    {
        super(name, owner, area);
    }

    public Town(NBTTagCompound nbt)
    {
        super(nbt);
    }

    public Set<Plot> getPlots()
    {
        return plots;
    }

    public Plot getPlot(String name)
    {
        for(Plot plot : plots)
            if(plot.getName().equals(name))
                return plot;
        return null;
    }

    public boolean addPlot(Plot plot)
    {
        return plots.add(plot);
    }

    public boolean removePlot(String plotName)
    {
        boolean removed = false;
        Iterator<Plot> plotsIter = plots.iterator();
        while(plotsIter.hasNext())
        {
            Plot plot = plotsIter.next();
            if(plot.getName().equals(plotName))
            {
                NetworkHandler.sendToClients(new AreaUpdateMessage(plot, false));
                plotsIter.remove();
                removed = true;
            }
        }
        return removed;
    }

    public boolean setPlot(Plot plot)
    {
        Plot existing = getPlot(plot.getName());
        if(existing != null)
            removePlot(existing.getName());
        return addPlot(plot);
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = super.serializeNBT();
        NBTTagList plotList = new NBTTagList();
        plots.forEach(plot -> plotList.appendTag(plot.serializeNBT()));
        nbt.setTag("plots", plotList);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        super.deserializeNBT(nbt);
        NBTTagList plotList = nbt.getTagList("plots", Constants.NBT.TAG_COMPOUND);
        plotList.forEach(tag -> plots.add(new Plot((NBTTagCompound) tag)));
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Town && super.equals(obj);
    }
}
