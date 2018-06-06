package brightspark.townmanager.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Plot implements INBTSerializable<NBTTagCompound>, Named
{
    private String name;
    private Area area;
    private UUID owner;
    private Set<UUID> members = new HashSet<>();

    public Plot(String name, Area area)
    {
        this.name = name;
        this.area = area;
    }

    public Plot(NBTTagCompound nbt)
    {
        deserializeNBT(nbt);
    }

    public String getName()
    {
        return name;
    }

    public Area getArea()
    {
        return area;
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

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Plot && ((Plot) obj).getName().equals(name);
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("name", name);
        nbt.setTag("area", area.serializeNBT());
        nbt.setUniqueId("owner", owner);

        NBTTagList list = new NBTTagList();
        members.forEach(member -> {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setUniqueId("uuid", member);
            list.appendTag(tag);
        });
        nbt.setTag("members", list);

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        name = nbt.getString("name");
        area = new Area(nbt.getCompoundTag("area"));
        owner = nbt.getUniqueId("owner");

        NBTTagList list = nbt.getTagList("members", Constants.NBT.TAG_COMPOUND);
        list.forEach(tag -> members.add(((NBTTagCompound) tag).getUniqueId("uuid")));
    }
}
