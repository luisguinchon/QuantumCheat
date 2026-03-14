package fr.cheatclient.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceResult;

public class ModuleAutoclick extends Module {

    private long lastClickTime = 0;
    private static final int CPS = 10;
    private static final long DELAY_MS = 1000 / CPS;

    public ModuleAutoclick() {
        super("Autoclick", ModuleCategory.COMBAT);
        setDescription("Clic automatique sur les entites visees");
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null || mc.world == null || mc.playerController == null) return;

        long now = System.currentTimeMillis();
        if (now - lastClickTime < DELAY_MS) return;

        RayTraceResult objectMouseOver = mc.objectMouseOver;
        if (objectMouseOver != null && objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY) {
            Entity entity = objectMouseOver.entityHit;
            if (entity != null && entity != mc.player) {
                mc.playerController.attackEntity(mc.player, entity);
                mc.player.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);
                lastClickTime = now;
            }
        }
    }
}
