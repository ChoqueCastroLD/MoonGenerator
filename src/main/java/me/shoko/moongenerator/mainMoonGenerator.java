package me.shoko.moongenerator;


import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;


public final class mainMoonGenerator extends JavaPlugin implements Listener {

    // Global variables and stuff
    List<String> allowedWorlds;
    boolean useGravity, useParticles, useGlassHelmets, useCustomBlockDrops, useEffects = true;

    // Change the block drops in the specified worlds
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void BlockBreakEvent(BlockBreakEvent event){
        if(useCustomBlockDrops) {
            if (event.getPlayer() != null && allowedWorlds.contains(event.getPlayer().getWorld().getName())) {
                if (event.getBlock().getType().equals(Material.DEAD_BRAIN_CORAL_BLOCK)) {
                    event.setCancelled(true);
                    //event.getBlock().setType(Material.COBBLESTONE);
                    // TO DO
                }
            }
        }
    }

    // Allow players to use Glass as Helmets
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void InventoryClickEvent​(InventoryClickEvent event){
        if(useGlassHelmets) {
            if (event.getRawSlot() == 5 && event.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
                ItemStack preHelmet = event.getCurrentItem();
                ItemStack newHelmet = event.getCursor();
                if (newHelmet.getType().equals(Material.GLASS) ||
                        newHelmet.getType().equals(Material.WHITE_STAINED_GLASS) ||
                        newHelmet.getType().equals(Material.ORANGE_STAINED_GLASS) ||
                        newHelmet.getType().equals(Material.MAGENTA_STAINED_GLASS) ||
                        newHelmet.getType().equals(Material.LIGHT_BLUE_STAINED_GLASS) ||
                        newHelmet.getType().equals(Material.YELLOW_STAINED_GLASS) ||
                        newHelmet.getType().equals(Material.LIME_STAINED_GLASS) ||
                        newHelmet.getType().equals(Material.PINK_STAINED_GLASS) ||
                        newHelmet.getType().equals(Material.GRAY_STAINED_GLASS) ||
                        newHelmet.getType().equals(Material.LIGHT_GRAY_STAINED_GLASS) ||
                        newHelmet.getType().equals(Material.CYAN_STAINED_GLASS) ||
                        newHelmet.getType().equals(Material.PURPLE_STAINED_GLASS) ||
                        newHelmet.getType().equals(Material.BLUE_STAINED_GLASS) ||
                        newHelmet.getType().equals(Material.BROWN_STAINED_GLASS) ||
                        newHelmet.getType().equals(Material.GREEN_STAINED_GLASS) ||
                        newHelmet.getType().equals(Material.RED_STAINED_GLASS) ||
                        newHelmet.getType().equals(Material.BLACK_STAINED_GLASS)) {
                    event.setCancelled(true);

                    Player p = (Player) event.getWhoClicked();
                    p.getInventory().setHelmet(newHelmet);
                    p.setItemOnCursor(preHelmet);
                }
            }
        }
    }

    @Override
    public void onEnable() {
        System.out.println("Enabling Shoko's Moon Generator");

        saveDefaultConfig();

        JavaPlugin plugin = this;
        FileConfiguration config = plugin.getConfig();
        allowedWorlds =  config.getStringList("worlds");
        useGravity = config.getBoolean("useGravity");
        useEffects = config.getBoolean("useEffects");
        useParticles = config.getBoolean("useParticles");
        useGlassHelmets = config.getBoolean("useGlassHelmets");
        useCustomBlockDrops = config.getBoolean("useCustomBlockDrops");

        Bukkit.getPluginManager().registerEvents(this, this);

        // Apply effects to players in the worlds marked as Moons
        if(useEffects) {
            BukkitScheduler scheduler = getServer().getScheduler();
            scheduler.scheduleSyncRepeatingTask(this, () -> {
                for (World world : Bukkit.getServer().getWorlds()) {
                    if (allowedWorlds.contains(world.getName())) {
                        for (Player p : world.getPlayers()) {
                            if (!p.getGameMode().equals(GameMode.CREATIVE) && !p.getGameMode().equals(GameMode.SPECTATOR)) {
                                PotionEffect efecto1 = new PotionEffect(PotionEffectType.SLOW_FALLING, 40, 3);
                                PotionEffect efecto2 = new PotionEffect(PotionEffectType.JUMP, 40, 3);
                                PotionEffect efecto3 = new PotionEffect(PotionEffectType.WITHER, 40, 1);
                                p.addPotionEffect(efecto1, true);
                                p.addPotionEffect(efecto2, true);
                                ItemStack playerHelmet = p.getInventory().getHelmet();
                                if (playerHelmet != null) {
                                    if (playerHelmet.getType().equals(Material.GLASS) ||
                                            playerHelmet.getType().equals(Material.WHITE_STAINED_GLASS) ||
                                            playerHelmet.getType().equals(Material.ORANGE_STAINED_GLASS) ||
                                            playerHelmet.getType().equals(Material.MAGENTA_STAINED_GLASS) ||
                                            playerHelmet.getType().equals(Material.LIGHT_BLUE_STAINED_GLASS) ||
                                            playerHelmet.getType().equals(Material.YELLOW_STAINED_GLASS) ||
                                            playerHelmet.getType().equals(Material.LIME_STAINED_GLASS) ||
                                            playerHelmet.getType().equals(Material.PINK_STAINED_GLASS) ||
                                            playerHelmet.getType().equals(Material.GRAY_STAINED_GLASS) ||
                                            playerHelmet.getType().equals(Material.LIGHT_GRAY_STAINED_GLASS) ||
                                            playerHelmet.getType().equals(Material.CYAN_STAINED_GLASS) ||
                                            playerHelmet.getType().equals(Material.PURPLE_STAINED_GLASS) ||
                                            playerHelmet.getType().equals(Material.BLUE_STAINED_GLASS) ||
                                            playerHelmet.getType().equals(Material.BROWN_STAINED_GLASS) ||
                                            playerHelmet.getType().equals(Material.GREEN_STAINED_GLASS) ||
                                            playerHelmet.getType().equals(Material.RED_STAINED_GLASS) ||
                                            playerHelmet.getType().equals(Material.BLACK_STAINED_GLASS)) {
                                        // nothing to do here
                                    } else {
                                        p.addPotionEffect(efecto3, true);
                                        if (useParticles)
                                        p.spawnParticle(Particle.DAMAGE_INDICATOR, p.getLocation().getX(), p.getLocation().getY() + 1, p.getLocation().getZ(), 15);
                                    }
                                } else {
                                    p.addPotionEffect(efecto3, true);
                                    if (useParticles)
                                    p.spawnParticle(Particle.DAMAGE_INDICATOR, p.getLocation().getX(), p.getLocation().getY() + 1, p.getLocation().getZ(), 10);
                                }
                            }
                        }
                    }
                }
            }, 0L, 30L);
        }
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new ChunkMoonGenerator();
    }

    @Override
    public void onDisable() {
        System.out.println("Stopping Shoko's Moon Generator");
        System.out.println("Good bye! Take care, love you <3 -Shoko");
    }
}
