package fr.cheatclient;

import fr.cheatclient.proxy.ClientProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = CheatClientMod.MODID, name = CheatClientMod.NAME, version = CheatClientMod.VERSION, clientSideOnly = true)
public class CheatClientMod {

    public static final String MODID = "cheatclient";
    public static final String NAME = "Cheat Client";
    public static final String VERSION = "1.0";

    @Mod.Instance(MODID)
    public static CheatClientMod instance;

    @SidedProxy(clientSide = "fr.cheatclient.proxy.ClientProxy", serverSide = "fr.cheatclient.proxy.ServerProxy")
    public static fr.cheatclient.proxy.CommonProxy proxy;

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    public static Logger getLogger() {
        return logger;
    }
}
