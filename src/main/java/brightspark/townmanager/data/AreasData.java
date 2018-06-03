package brightspark.townmanager.data;

import brightspark.townmanager.TownManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.HashSet;
import java.util.Set;

public class AreasData extends WorldSavedData
{
    private static final String NAME = TownManager.MOD_ID + "_areas";

    private final Set<Town> towns = new HashSet<>();

    public AreasData()
    {
        this(NAME);
    }

    public AreasData(String name)
    {
        super(name);
    }

    public static AreasData get(World world)
    {
        MapStorage storage = world.getPerWorldStorage();
        AreasData data = (AreasData) storage.getOrLoadData(AreasData.class, NAME);
        if(data == null)
        {
            data = new AreasData();
            storage.setData(NAME, data);
        }
        return data;
    }

    public Set<Town> getTowns()
    {
        return towns;
    }

    public boolean addTown(Town town)
    {
        return towns.add(town);
    }

    public Town getTown(String name)
    {
        for(Town town : towns)
            if(town.getName().equals(name))
                return town;
        return null;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        NBTTagList list = nbt.getTagList("towns", Constants.NBT.TAG_COMPOUND);
        list.forEach(tag -> towns.add(new Town((NBTTagCompound) tag)));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        NBTTagList list = new NBTTagList();
        towns.forEach(town -> list.appendTag(town.serializeNBT()));
        nbt.setTag("towns", list);
        return nbt;
    }
}
