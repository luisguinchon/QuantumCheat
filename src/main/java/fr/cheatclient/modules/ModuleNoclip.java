package fr.cheatclient.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ModuleNoclip extends Module {

    public ModuleNoclip() {
        super("Noclip", ModuleCategory.MOVEMENT);
        setDescription("Traverser les blocs");
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (player != null) {
            player.noClip = false;
        }
    }

    @Override
    public void onTick() {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (player != null && isEnabled()) {
            player.noClip = true;
        }
    }
}
