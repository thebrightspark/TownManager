package brightspark.townmanager.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.INBTSerializable;

public class Area implements INBTSerializable<NBTTagCompound>
{
    private BlockPos pos1, pos2;

    public Area(BlockPos position1, BlockPos position2)
    {
        pos1 = new BlockPos(
                Math.min(position1.getX(), position2.getX()),
                Math.min(position1.getY(), position2.getY()),
                Math.min(position1.getZ(), position2.getZ()));
        pos2 = new BlockPos(
                Math.max(position1.getX(), position2.getX()),
                Math.max(position1.getY(), position2.getY()),
                Math.max(position1.getZ(), position2.getZ()));
    }

    public Area(NBTTagCompound nbt)
    {
        deserializeNBT(nbt);
    }

    public BlockPos getMinPos()
    {
        return pos1;
    }

    public BlockPos getMaxPos()
    {
        return pos2;
    }

    public AxisAlignedBB asAABB()
    {
        Vec3d p1 = new Vec3d(pos1).add(new Vec3d(0.4d, 0.4d, 0.4d));
        Vec3d p2 = new Vec3d(pos2).add(new Vec3d(0.6d, 0.6d, 0.6d));
        return new AxisAlignedBB(p1.x, p1.y, p1.z, p2.x, p2.y, p2.z);
    }

    public boolean intersects(Area area)
    {
        return asAABB().intersects(area.asAABB());
    }

    public boolean intersects(BlockPos pos)
    {
        return asAABB().contains(new Vec3d(pos).add(new Vec3d(0.5d, 0.5d, 0.5d)));
    }

    public void extendToMinMaxY()
    {
        pos1 = new BlockPos(pos1.getX(), 0, pos1.getZ());
        pos2 = new BlockPos(pos2.getX(), 255, pos2.getZ());
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Area)) return false;
        Area other = (Area) obj;
        return pos1.equals(other.pos1) && pos2.equals(other.pos2);
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setLong("pos1", pos1.toLong());
        nbt.setLong("pos2", pos2.toLong());
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        pos1 = BlockPos.fromLong(nbt.getLong("pos1"));
        pos2 = BlockPos.fromLong(nbt.getLong("pos2"));
    }
}
