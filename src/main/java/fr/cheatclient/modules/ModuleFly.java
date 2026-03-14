package fr.cheatclient.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ModuleFly extends Module {

    public ModuleFly() {
        super("Fly", ModuleCategory.MOVEMENT);
        setDescription("Voler comme en mode creatif");
    }

    @Override
    public void onEnable() {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (player != null) {
            player.capabilities.allowFlying = true;
            player.capabilities.isFlying = true;
        }
    }

    @Override
    public void onDisable() {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (player != null && !player.isCreative()) {
            player.capabilities.allowFlying = false;
            player.capabilities.isFlying = false;
        }
    }

    @Override
    public void onTick() {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (player != null) {
            player.capabilities.allowFlying = true;
            player.capabilities.isFlying = true;
        }
    }
}
