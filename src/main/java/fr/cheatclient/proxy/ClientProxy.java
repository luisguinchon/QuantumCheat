package fr.cheatclient.proxy;

import fr.cheatclient.ModKeyBindings;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        ModKeyBindings.register();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ModKeyBindings.registerHandlers();
    }
}
