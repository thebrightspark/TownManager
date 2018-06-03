package brightspark.townmanager.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Town implements INBTSerializable<NBTTagCompound>
{
    private String name;
    private UUID owner;
    private Set<UUID> members = new HashSet<>();
    private Set<Plot> plots = new HashSet<>();

    public Town(String name)
    {
        this.name = name;
    }

    public Town(NBTTagCompound nbt)
    {
        deserializeNBT(nbt);
    }

    public String getName()
    {
        return name;
    }

    public UUID getOwner()
    {
        return owner;
    }

    public void setOwner(UUID uuid)
    {
        owner = uuid;
    }

    public Set<UUID> getMembers()
    {
        return members;
    }

    public boolean addMember(UUID uuid)
    {
        return members.add(uuid);
    }

    public boolean removeMember(UUID uuid)
    {
        return members.remove(uuid);
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
        return plots.removeIf(plot -> plot.getName().equals(plotName));
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Town && ((Town) obj).getName().equals(name);
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("name", name);
        nbt.setUniqueId("owner", owner);

        NBTTagList memberList = new NBTTagList();
        members.forEach(member -> {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setUniqueId("uuid", member);
            memberList.appendTag(tag);
        });
        nbt.setTag("members", memberList);

        NBTTagList plotList = new NBTTagList();
        plots.forEach(plot -> plotList.appendTag(plot.serializeNBT()));
        nbt.setTag("plots", plotList);

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        name = nbt.getString("name");
        owner = nbt.getUniqueId("owner");

        NBTTagList memberList = nbt.getTagList("members", Constants.NBT.TAG_COMPOUND);
        memberList.forEach(tag -> members.add(((NBTTagCompound) tag).getUniqueId("uuid")));

        NBTTagList plotList = nbt.getTagList("plots", Constants.NBT.TAG_COMPOUND);
        plotList.forEach(tag -> plots.add(new Plot((NBTTagCompound) tag)));
    }
}
