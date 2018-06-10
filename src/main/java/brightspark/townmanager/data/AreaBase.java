package brightspark.townmanager.data;

import brightspark.townmanager.handlers.NetworkHandler;
import brightspark.townmanager.messages.AreaUpdateMessage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Base class for Town and Plot
 */
public class AreaBase implements INBTSerializable<NBTTagCompound>
{
    private String name;
    private UUID owner;
    private Area area;
    private Set<UUID> members = new HashSet<>();

    public AreaBase(String name, UUID owner, Area area)
    {
        this.name = name;
        this.owner = owner;
        this.area = area;
    }

    public AreaBase(NBTTagCompound nbt)
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
        updateClients();
    }

    public Area getArea()
    {
        return area;
    }

    public boolean areaContainsPos(BlockPos pos)
    {
        return area.intersects(pos);
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

    public boolean canPlayerEdit(UUID uuid)
    {
        return owner.equals(uuid) || members.contains(uuid);
    }

    protected void updateClients()
    {
        NetworkHandler.sendToClients(new AreaUpdateMessage(this, true));
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("name", name);
        nbt.setUniqueId("owner", owner);
        nbt.setTag("area", area.serializeNBT());

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
        owner = nbt.getUniqueId("owner");
        area = new Area(nbt.getCompoundTag("area"));

        NBTTagList list = nbt.getTagList("members", Constants.NBT.TAG_COMPOUND);
        list.forEach(tag -> members.add(((NBTTagCompound) tag).getUniqueId("uuid")));
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof AreaBase && ((AreaBase) obj).getName().equals(name);
    }
}
