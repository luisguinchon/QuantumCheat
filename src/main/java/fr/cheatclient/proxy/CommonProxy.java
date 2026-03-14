package fr.cheatclient.proxy;

import fr.cheatclient.ModuleManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ModuleManager.init();
    }

    public void init(FMLInitializationEvent event) {
    }
}
