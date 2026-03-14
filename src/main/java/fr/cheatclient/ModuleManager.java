package fr.cheatclient;

import fr.cheatclient.modules.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private static final List<Module> modules = new ArrayList<>();

    public static void init() {
        // Movement
        register(new ModuleFly());
        register(new ModuleNoclip());

        // Render
        register(new ModuleESP());
        register(new ModuleXray());

        // Combat
        register(new ModuleAutoclick());
        register(new ModuleKillAura());

        // World
        // Other
    }

    public static void register(Module module) {
        modules.add(module);
    }

    public static List<Module> getModules() {
        return modules;
    }

    public static List<Module> getModulesByCategory(ModuleCategory category) {
        List<Module> result = new ArrayList<>();
        for (Module m : modules) {
            if (m.getCategory() == category) {
                result.add(m);
            }
        }
        return result;
    }

    public static Module getModuleByName(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public static List<Module> getEnabledModules() {
        List<Module> result = new ArrayList<>();
        for (Module m : modules) {
            if (m.isEnabled()) {
                result.add(m);
            }
        }
        return result;
    }
}
