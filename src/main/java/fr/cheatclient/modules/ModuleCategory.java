package fr.cheatclient.modules;

public enum ModuleCategory {
    MOVEMENT("Movement"),
    RENDER("Render"),
    COMBAT("Combat"),
    WORLD("World"),
    OTHER("Other");

    private final String displayName;

    ModuleCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
