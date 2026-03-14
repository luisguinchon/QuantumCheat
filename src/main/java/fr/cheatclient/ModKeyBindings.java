package fr.cheatclient;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class ModKeyBindings {

    public static KeyBinding OPEN_MENU;

    public static void register() {
        OPEN_MENU = new KeyBinding("key.cheatclient.openmenu", Keyboard.KEY_INSERT, "key.categories.cheatclient");
        ClientRegistry.registerKeyBinding(OPEN_MENU);
    }

    public static void registerHandlers() {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new fr.cheatclient.events.EventHandler());
    }
}
