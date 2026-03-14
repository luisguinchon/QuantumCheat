package fr.cheatclient.modules;

public class ModuleXray extends Module {

    public ModuleXray() {
        super("Xray", ModuleCategory.RENDER);
        setDescription("Voir les minerais a travers la pierre");
    }

    @Override
    public void onEnable() {
        if (net.minecraft.client.Minecraft.getMinecraft().world != null) {
            net.minecraft.client.Minecraft.getMinecraft().renderGlobal.loadRenderers();
        }
    }

    @Override
    public void onDisable() {
        if (net.minecraft.client.Minecraft.getMinecraft().world != null) {
            net.minecraft.client.Minecraft.getMinecraft().renderGlobal.loadRenderers();
        }
    }
}
