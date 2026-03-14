package fr.cheatclient.modules;

import org.lwjgl.input.Keyboard;

public abstract class Module {

    private final String name;
    private final ModuleCategory category;
    private int keyBind;
    private boolean enabled;
    private String description = "";

    public Module(String name, ModuleCategory category) {
        this.name = name;
        this.category = category;
        this.keyBind = Keyboard.KEY_NONE;
        this.enabled = false;
    }

    public Module(String name, ModuleCategory category, int keyBind) {
        this(name, category);
        this.keyBind = keyBind;
    }

    public void toggle() {
        enabled = !enabled;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            toggle();
        }
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public void onTick() {
    }

    public String getName() {
        return name;
    }

    public ModuleCategory getCategory() {
        return category;
    }

    public int getKeyBind() {
        return keyBind;
    }

    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
