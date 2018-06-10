package brightspark.townmanager.data;

import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public class Plot extends AreaBase
{
    private String townParent;

    public Plot(String name, UUID owner, Area area, String townParentName)
    {
        super(name, owner, area);
        townParent = townParentName;
    }

    public Plot(NBTTagCompound nbt)
    {
        super(nbt);
    }

    public String getTownParent()
    {
        return townParent;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = super.serializeNBT();
        nbt.setString("townParent", townParent);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        super.deserializeNBT(nbt);
        townParent = nbt.getString("townParent");
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Plot && super.equals(obj);
    }
}
