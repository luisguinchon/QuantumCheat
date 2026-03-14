package fr.cheatclient.gui;

import fr.cheatclient.ModuleManager;
import fr.cheatclient.modules.Module;
import fr.cheatclient.modules.ModuleCategory;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

import java.io.IOException;
import java.util.List;

public class GuiCheatMenu extends GuiScreen {

    private static final int TURQUOISE = 0xFF00CED1;
    private static final int TURQUOISE_DARK = 0xFF008B8B;
    private static final int BACKGROUND = 0xCC000000;
    private static final int SIDEBAR_WIDTH = 160;
    private static final int MODULE_PANEL_WIDTH = 280;
    private static final int MENU_HEIGHT = 350;
    private static final int CATEGORY_HEIGHT = 24;
    private static final int MODULE_HEIGHT = 20;

    private ModuleCategory selectedCategory = ModuleCategory.MOVEMENT;
    private int menuX, menuY;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        int screenWidth = this.width;
        int screenHeight = this.height;
        menuX = (screenWidth - (SIDEBAR_WIDTH + MODULE_PANEL_WIDTH)) / 2;
        menuY = (screenHeight - MENU_HEIGHT) / 2;

        drawGradientRect(0, 0, screenWidth, screenHeight, 0x40000000, 0x40000000);

        drawRect(menuX, menuY, menuX + SIDEBAR_WIDTH + MODULE_PANEL_WIDTH, menuY + MENU_HEIGHT, BACKGROUND);

        drawRect(menuX, menuY, menuX + 2, menuY + MENU_HEIGHT, TURQUOISE);
        drawRect(menuX + SIDEBAR_WIDTH, menuY, menuX + SIDEBAR_WIDTH + 2, menuY + MENU_HEIGHT, TURQUOISE);

        drawString(fontRenderer, "Cheat Client", menuX + 10, menuY + 8, TURQUOISE);
        drawRect(menuX, menuY + 22, menuX + SIDEBAR_WIDTH + MODULE_PANEL_WIDTH, menuY + 24, TURQUOISE_DARK);

        int categoryY = menuY + 35;
        for (ModuleCategory cat : ModuleCategory.values()) {
            boolean hovered = mouseX >= menuX && mouseX <= menuX + SIDEBAR_WIDTH - 4 &&
                    mouseY >= categoryY && mouseY < categoryY + CATEGORY_HEIGHT;
            boolean selected = cat == selectedCategory;

            if (selected) {
                drawRect(menuX + 2, categoryY, menuX + SIDEBAR_WIDTH - 2, categoryY + CATEGORY_HEIGHT - 2, 0x4400CED1);
            }
            if (hovered && !selected) {
                drawRect(menuX + 2, categoryY, menuX + SIDEBAR_WIDTH - 2, categoryY + CATEGORY_HEIGHT - 2, 0x2200CED1);
            }

            drawString(fontRenderer, cat.getDisplayName(), menuX + 8, categoryY + 6, selected ? TURQUOISE : 0xFFFFFFFF);
            categoryY += CATEGORY_HEIGHT;
        }

        int panelX = menuX + SIDEBAR_WIDTH + 10;
        int panelY = menuY + 35;
        drawString(fontRenderer, selectedCategory.getDisplayName(), panelX, panelY - 2, TURQUOISE);
        panelY += 20;

        List<Module> modules = ModuleManager.getModulesByCategory(selectedCategory);
        for (Module mod : modules) {
            boolean hovered = mouseX >= panelX && mouseX <= menuX + SIDEBAR_WIDTH + MODULE_PANEL_WIDTH - 10 &&
                    mouseY >= panelY && mouseY < panelY + MODULE_HEIGHT;

            if (hovered) {
                drawRect(panelX - 2, panelY - 2, menuX + SIDEBAR_WIDTH + MODULE_PANEL_WIDTH - 12, panelY + MODULE_HEIGHT - 2, 0x2200CED1);
            }

            String prefix = mod.isEnabled() ? "+" : "-";
            int color = mod.isEnabled() ? TURQUOISE : 0xFFFFFFFF;
            drawString(fontRenderer, prefix + " " + mod.getName() + " >", panelX, panelY + 4, color);
            panelY += MODULE_HEIGHT;
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        int categoryY = menuY + 35;
        for (ModuleCategory cat : ModuleCategory.values()) {
            if (mouseX >= menuX && mouseX <= menuX + SIDEBAR_WIDTH - 4 &&
                    mouseY >= categoryY && mouseY < categoryY + CATEGORY_HEIGHT) {
                selectedCategory = cat;
                return;
            }
            categoryY += CATEGORY_HEIGHT;
        }

        int panelX = menuX + SIDEBAR_WIDTH + 10;
        int panelY = menuY + 55;
        List<Module> modules = ModuleManager.getModulesByCategory(selectedCategory);
        for (Module mod : modules) {
            if (mouseX >= panelX && mouseX <= menuX + SIDEBAR_WIDTH + MODULE_PANEL_WIDTH - 10 &&
                    mouseY >= panelY && mouseY < panelY + MODULE_HEIGHT && mouseButton == 0) {
                mod.toggle();
                return;
            }
            panelY += MODULE_HEIGHT;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
