package fr.cheatclient.events;

import fr.cheatclient.ModuleManager;
import fr.cheatclient.gui.GuiCheatMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.HashSet;
import java.util.Set;

public class EventHandler {

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            for (fr.cheatclient.modules.Module m : ModuleManager.getModules()) {
                if (m.isEnabled()) {
                    m.onTick();
                }
            }
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (fr.cheatclient.ModKeyBindings.OPEN_MENU.isPressed()) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.currentScreen == null) {
                mc.displayGuiScreen(new GuiCheatMenu());
            } else if (mc.currentScreen instanceof GuiCheatMenu) {
                mc.displayGuiScreen(null);
            }
        }

        for (fr.cheatclient.modules.Module m : ModuleManager.getModules()) {
            if (m.getKeyBind() != Keyboard.KEY_NONE && Keyboard.isKeyDown(m.getKeyBind())) {
                m.toggle();
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null || mc.world == null) return;

        Entity viewEntity = mc.getRenderViewEntity();
        if (viewEntity == null) return;

        double partialTicks = event.getPartialTicks();
        double x = viewEntity.lastTickPosX + (viewEntity.posX - viewEntity.lastTickPosX) * partialTicks;
        double y = viewEntity.lastTickPosY + (viewEntity.posY - viewEntity.lastTickPosY) * partialTicks;
        double z = viewEntity.lastTickPosZ + (viewEntity.posZ - viewEntity.lastTickPosZ) * partialTicks;

        GlStateManager.pushMatrix();
        GlStateManager.translate(-x, -y, -z);

        if (ModuleManager.getModuleByName("ESP") != null && ModuleManager.getModuleByName("ESP").isEnabled()) {
            renderESP(mc, x, y, z);
        }

        fr.cheatclient.modules.Module xrayModule = ModuleManager.getModuleByName("Xray");
        if (xrayModule != null && xrayModule.isEnabled()) {
            renderOreESP(mc, x, y, z);
        }

        GlStateManager.popMatrix();
    }

    private void renderESP(Minecraft mc, double x, double y, double z) {
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GL11.glLineWidth(2.0f);

        for (Entity entity : mc.world.loadedEntityList) {
            if (entity == mc.player || !(entity instanceof EntityLivingBase)) continue;
            if (entity.getDistance(mc.player) > 64) continue;

            EntityLivingBase living = (EntityLivingBase) entity;
            if (!living.isEntityAlive()) continue;

            AxisAlignedBB box = entity.getEntityBoundingBox();
            float r = entity instanceof net.minecraft.entity.player.EntityPlayer ? 1.0f : 0.0f;
            float g = entity instanceof net.minecraft.entity.player.EntityPlayer ? 0.0f : 1.0f;
            float b = 0.0f;
            float a = 0.5f;

            drawOutlinedBox(box, r, g, b, a);
        }

        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
    }

    private void renderOreESP(Minecraft mc, double x, double y, double z) {
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GL11.glLineWidth(1.5f);

        Set<BlockPos> ores = new HashSet<>();
        int range = 32;
        BlockPos playerPos = mc.player.getPosition();

        for (int dx = -range; dx <= range; dx += 4) {
            for (int dy = -range; dy <= range; dy += 4) {
                for (int dz = -range; dz <= range; dz += 4) {
                    BlockPos pos = playerPos.add(dx, dy, dz);
                    if (mc.world.getBlockState(pos).getBlock() == Blocks.DIAMOND_ORE) {
                        ores.add(pos);
                    } else if (mc.world.getBlockState(pos).getBlock() == Blocks.GOLD_ORE || mc.world.getBlockState(pos).getBlock() == Blocks.IRON_ORE) {
                        ores.add(pos);
                    } else if (mc.world.getBlockState(pos).getBlock() == Blocks.COAL_ORE || mc.world.getBlockState(pos).getBlock() == Blocks.LAPIS_ORE) {
                        ores.add(pos);
                    } else if (mc.world.getBlockState(pos).getBlock() == Blocks.REDSTONE_ORE || mc.world.getBlockState(pos).getBlock() == Blocks.LIT_REDSTONE_ORE) {
                        ores.add(pos);
                    } else if (mc.world.getBlockState(pos).getBlock() == Blocks.EMERALD_ORE) {
                        ores.add(pos);
                    }
                }
            }
        }

        for (BlockPos pos : ores) {
            AxisAlignedBB box = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
            if (mc.world.getBlockState(pos).getBlock() == Blocks.DIAMOND_ORE) {
                drawOutlinedBox(box, 0.2f, 0.6f, 1.0f, 0.6f);
            } else if (mc.world.getBlockState(pos).getBlock() == Blocks.EMERALD_ORE) {
                drawOutlinedBox(box, 0.2f, 1.0f, 0.4f, 0.6f);
            } else if (mc.world.getBlockState(pos).getBlock() == Blocks.GOLD_ORE) {
                drawOutlinedBox(box, 1.0f, 0.84f, 0.0f, 0.6f);
            } else if (mc.world.getBlockState(pos).getBlock() == Blocks.IRON_ORE) {
                drawOutlinedBox(box, 0.8f, 0.7f, 0.6f, 0.6f);
            } else {
                drawOutlinedBox(box, 0.6f, 0.6f, 0.6f, 0.5f);
            }
        }

        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
    }

    private void drawOutlinedBox(AxisAlignedBB box, float r, float g, float b, float a) {
        Tessellator tess = Tessellator.getInstance();
        net.minecraft.client.renderer.BufferBuilder buffer = tess.getBuffer();

        GlStateManager.color(r, g, b, a);

        buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);

        buffer.pos(box.minX, box.minY, box.minZ).endVertex();
        buffer.pos(box.maxX, box.minY, box.minZ).endVertex();

        buffer.pos(box.maxX, box.minY, box.minZ).endVertex();
        buffer.pos(box.maxX, box.minY, box.maxZ).endVertex();

        buffer.pos(box.maxX, box.minY, box.maxZ).endVertex();
        buffer.pos(box.minX, box.minY, box.maxZ).endVertex();

        buffer.pos(box.minX, box.minY, box.maxZ).endVertex();
        buffer.pos(box.minX, box.minY, box.minZ).endVertex();

        buffer.pos(box.minX, box.maxY, box.minZ).endVertex();
        buffer.pos(box.maxX, box.maxY, box.minZ).endVertex();

        buffer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        buffer.pos(box.maxX, box.maxY, box.maxZ).endVertex();

        buffer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        buffer.pos(box.minX, box.maxY, box.maxZ).endVertex();

        buffer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        buffer.pos(box.minX, box.maxY, box.minZ).endVertex();

        buffer.pos(box.minX, box.minY, box.minZ).endVertex();
        buffer.pos(box.minX, box.maxY, box.minZ).endVertex();

        buffer.pos(box.maxX, box.minY, box.minZ).endVertex();
        buffer.pos(box.maxX, box.maxY, box.minZ).endVertex();

        buffer.pos(box.maxX, box.minY, box.maxZ).endVertex();
        buffer.pos(box.maxX, box.maxY, box.maxZ).endVertex();

        buffer.pos(box.minX, box.minY, box.maxZ).endVertex();
        buffer.pos(box.minX, box.maxY, box.maxZ).endVertex();

        tess.draw();
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null || mc.world == null) return;

        List<fr.cheatclient.modules.Module> enabled = ModuleManager.getEnabledModules();
        if (enabled.isEmpty()) return;

        net.minecraft.client.gui.ScaledResolution sr = new net.minecraft.client.gui.ScaledResolution(mc);
        int y = 2;
        int colorIndex = 0;
        int[] colors = {0xFF00CED1, 0xFF9370DB, 0xFFFF69B4};
        for (fr.cheatclient.modules.Module m : enabled) {
            int w = mc.fontRenderer.getStringWidth(m.getName());
            mc.fontRenderer.drawStringWithShadow(m.getName(), sr.getScaledWidth() - w - 4, y, colors[colorIndex % colors.length]);
            y += 10;
            colorIndex++;
        }
    }
}
