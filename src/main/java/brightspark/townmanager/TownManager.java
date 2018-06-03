package brightspark.townmanager;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@Mod(modid = TownManager.MOD_ID, name = TownManager.NAME, version = TownManager.VERSION)
public class TownManager
{
    public static final String MOD_ID = "townmanager";
    public static final String NAME = "Town Manager";
    public static final String VERSION = "@VERSION@";

    @Mod.Instance(MOD_ID)
    public static TownManager INSTANCE;
    public static Logger LOGGER;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LOGGER = event.getModLog();
        NetworkManager.init();
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        //TODO: Add commands
    }

    @NetworkCheckHandler
    public boolean checkModLists(Map<String,String> modList, Side side)
    {
        if(side.isServer())
        {
            NetworkManager.canSendToServer = modList.containsKey(MOD_ID);
            LOGGER.info("Server side mod " + (NetworkManager.canSendToServer ? "exists" : "does not exist"));
        }
        return true;
    }
}
