package de.bjm.mc.bjmtools.commands;

import java.lang.reflect.Method;

public class BJMCommand {

    String name;
    String usage;
    private boolean requiresPlayer;
    private Method commandMethod;

    public String getName() {
        return name;
    }

    public String getUsage() {
        return usage;
    }

    public Method getCommandMethod() {
        return commandMethod;
    }

    public boolean requiresPlayer() {
        return requiresPlayer;
    }


    public BJMCommand(String name, String usage, boolean requiresPlayer, Method commandMethod) {
        this.name = name;
        this.usage = usage;
        this.requiresPlayer = requiresPlayer;
        this.commandMethod = commandMethod;
    }

}
