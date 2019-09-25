package me.shoko.moongenerator;


import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;


public final class mainMoonGenerator extends JavaPlugin implements Listener {

    // Global variables and stuff
    JavaPlugin plugin = this;

    private void log(String msg) {
        Bukkit.getLogger().info(msg);
    }

    @Override
    public void onEnable() {
        log("Enabling Shoko's Moon Generator");
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new ChunkMoonGenerator();
    }

    @Override
    public void onDisable() {
        log("Stopping Shoko's Moon Generator");
        log("Good bye! love you <3 -Shoko");
    }

}
