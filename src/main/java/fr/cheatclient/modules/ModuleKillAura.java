package fr.cheatclient.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;
import java.util.List;

public class ModuleKillAura extends Module {

    private static final double RANGE = 4.0;
    private int attackCooldown = 0;

    public ModuleKillAura() {
        super("KillAura", ModuleCategory.COMBAT);
        setDescription("Attaque automatique des entites proches (PVP)");
    }

    @Override
    public void onEnable() {
        attackCooldown = 0;
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null || mc.world == null || mc.playerController == null) return;

        if (attackCooldown > 0) {
            attackCooldown--;
            return;
        }

        EntityLivingBase target = findTarget(mc);
        if (target != null) {
            rotateTowards(target, mc);
            mc.playerController.attackEntity(mc.player, target);
            mc.player.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);
            attackCooldown = 10;
        }
    }

    private EntityLivingBase findTarget(Minecraft mc) {
        AxisAlignedBB box = mc.player.getEntityBoundingBox().grow(RANGE);
        List<Entity> entities = mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, box);

        return entities.stream()
                .filter(e -> e instanceof EntityLivingBase)
                .map(e -> (EntityLivingBase) e)
                .filter(e -> e.isEntityAlive() && !e.equals(mc.player))
                .filter(e -> mc.player.getDistance(e) <= RANGE)
                .filter(e -> e instanceof EntityPlayer || e instanceof EntityMob || e instanceof EntityAnimal)
                .min(Comparator.comparingDouble(e -> mc.player.getDistance(e)))
                .orElse(null);
    }

    private void rotateTowards(EntityLivingBase target, Minecraft mc) {
        Vec3d eyePos = mc.player.getPositionEyes(1.0f);
        Vec3d targetPos = target.getPositionVector().addVector(0, target.getEyeHeight() / 2, 0);
        Vec3d diff = targetPos.subtract(eyePos);

        double dist = Math.sqrt(diff.x * diff.x + diff.z * diff.z);
        float yaw = (float) (Math.atan2(-diff.x, diff.z) * (180 / Math.PI));
        float pitch = (float) (-(Math.atan2(diff.y, dist) * (180 / Math.PI)));

        mc.player.rotationYaw = yaw;
        mc.player.rotationPitch = pitch;
    }
}
