package brightspark.townmanager;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = TownManager.MOD_ID, name = TownManager.NAME, version = TownManager.VERSION)
public class TownManager
{
    public static final String MOD_ID = "modname";
    public static final String NAME = "Mod Name";
    public static final String VERSION = "@VERSION@";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }
}
