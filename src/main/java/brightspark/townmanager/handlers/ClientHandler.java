package brightspark.townmanager.handlers;

import brightspark.townmanager.TownManager;
import brightspark.townmanager.data.Town;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = TownManager.MOD_ID, value = Side.CLIENT)
public class ClientHandler
{
    public static final Set<Town> towns = new HashSet<>();

    public static Town getTown(String name)
    {
        for(Town town : towns)
            if(town.getName().equals(name))
                return town;
        return null;
    }

    public static void removeTown(String name)
    {
        towns.removeIf(town -> town.getName().equals(name));
    }

    public static void setTown(Town town)
    {
        Town existing = getTown(town.getName());
        if(existing != null)
            removeTown(existing.getName());
        towns.add(town);
    }

    @SubscribeEvent
    public static void renderAreas(RenderWorldLastEvent event)
    {
        //TODO: Render areas
    }
}
