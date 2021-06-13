package me.fertiz.reward;

import me.fertiz.reward.commands.DrawCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("losuj").setExecutor(new DrawCommand(this));
        System.out.println("In my room i beat my clock");
    }
}
