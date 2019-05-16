package me.shoko.moongenerator;


import com.destroystokyo.paper.ParticleBuilder;
import com.destroystokyo.paper.Title;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import javax.swing.text.html.parser.Entity;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.bukkit.Bukkit.getServer;


public final class mainMoonGenerator extends JavaPlugin implements Listener {

    // Global variables and stuff
    JavaPlugin plugin = this;
    List<String> configLore;
    boolean useGravity, useSkullHelmets, useParticles, useGlassHelmets, useCustomBlockDrops, useEffects, useRockets, useCustomMobs,useSpecialMobs, useOxygenSafeZone;
    String moonWorldName, evolvedMoonCreeperName, missionFailed, toTheMoon, toTheEarth, earthWorldName, MoonRockName, SoftMoonRockName, MoonCobblestoneName,
    LostAstronautName,MoonPhantomName,LostMoonExplorerName,FailedExperimentalHorseName,
    MoonMobSufixName,MoonMobPrefixName;

    // Change the block drops in the specified worlds
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void BlockBreakEvent(BlockBreakEvent event){
        if(useCustomBlockDrops) {
            if (moonWorldName.compareToIgnoreCase(event.getBlock().getWorld().getName()) == 0) {
                if (event.getBlock().getType().equals(Material.DEAD_BRAIN_CORAL_BLOCK)) {
                    event.setCancelled(true);

                    ItemStack drop = new ItemStack(Material.DEAD_BRAIN_CORAL_BLOCK);
                    ItemMeta meta = drop.getItemMeta();
                    meta.setDisplayName(ChatColor.RESET+MoonRockName);
                    drop.setItemMeta(meta);

                    event.getBlock().setType(Material.AIR);
                    event.getPlayer().getWorld().dropItem(event.getBlock().getLocation(), drop);
                }
                if (event.getBlock().getType().equals(Material.DEAD_BUBBLE_CORAL_BLOCK)) {
                    event.setCancelled(true);

                    ItemStack drop = new ItemStack(Material.DEAD_BUBBLE_CORAL_BLOCK);
                    ItemMeta meta = drop.getItemMeta();
                    meta.setDisplayName(ChatColor.RESET+SoftMoonRockName);
                    meta.setLore(configLore);
                    drop.setItemMeta(meta);

                    event.getBlock().setType(Material.AIR);
                    event.getPlayer().getWorld().dropItem(event.getBlock().getLocation(), drop);
                }
                if (event.getBlock().getType().equals(Material.DEAD_TUBE_CORAL_BLOCK)||event.getBlock().getType().equals(Material.DEAD_FIRE_CORAL_BLOCK)||event.getBlock().getType().equals(Material.DEAD_HORN_CORAL_BLOCK)) {
                    event.setCancelled(true);

                    ItemStack drop = new ItemStack(Material.DEAD_HORN_CORAL_BLOCK);
                    ItemMeta meta = drop.getItemMeta();
                    meta.setDisplayName(ChatColor.RESET+MoonCobblestoneName);
                    drop.setItemMeta(meta);

                    event.getBlock().setType(Material.AIR);
                    event.getPlayer().getWorld().dropItem(event.getBlock().getLocation(), drop);
                }
            }
        }
    }

    // Custom mob spawn logic
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onMobSpawn(CreatureSpawnEvent event){
        if (moonWorldName.compareToIgnoreCase(event.getEntity().getWorld().getName()) == 0 && useCustomMobs) {
            LivingEntity mob = event.getEntity();
            PotionEffect efecto1 = new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 3);
            PotionEffect efecto2 = new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 3);

            mob.addPotionEffect(efecto1, true);
            mob.addPotionEffect(efecto2, true);

            mob.getEquipment().setHelmet(new ItemStack(Material.GLASS));

            mob.setCustomName(ChatColor.AQUA+MoonMobSufixName+mob.getType().toString()+MoonMobPrefixName);

            if(mob.getType().equals(EntityType.SLIME)
                    || mob.getType().equals(EntityType.SPIDER)
                    || mob.getType().equals(EntityType.ENDERMAN)
                    || mob.getType().equals(EntityType.WITCH)) {
                event.setCancelled(true);
                if (new Random().nextBoolean()) {
                    if (new Random().nextBoolean())
                        event.getEntity().getWorld().spawnEntity(event.getLocation(), EntityType.DROWNED);
                    else
                        event.getEntity().getWorld().spawnEntity(event.getLocation(), EntityType.STRAY).setCustomName(ChatColor.AQUA + "Moon Skeleton");
                }
            }
            if(mob.getType().equals(EntityType.SKELETON)){
                if(new Random().nextInt(100)<10) {
                    event.setCancelled(true);
                    org.bukkit.entity.Entity mober = event.getEntity().getWorld().spawnEntity(event.getLocation(), EntityType.SKELETON_HORSE);
                    mober.setCustomName(ChatColor.AQUA + FailedExperimentalHorseName);
                    org.bukkit.entity.Entity newmob = mober.getWorld().spawnEntity(mober.getLocation(), EntityType.STRAY);
                    newmob.setSilent(true);
                    newmob.setCustomName(ChatColor.AQUA + LostMoonExplorerName);
                    mober.addPassenger(newmob);
                }
            }
            if(mob.getType().equals(EntityType.ZOMBIE)){
                event.setCancelled(true);
                if(new Random().nextBoolean()) {
                    if (new Random().nextBoolean()) {
                        org.bukkit.entity.Entity mober = event.getEntity().getWorld().spawnEntity(event.getLocation(), EntityType.ZOMBIE_VILLAGER);
                        mober.setCustomName(ChatColor.AQUA + LostAstronautName);
                    } else {
                        org.bukkit.entity.Entity mober = event.getEntity().getWorld().spawnEntity(event.getLocation(), EntityType.DROWNED);
                        mober.setCustomName(ChatColor.AQUA + LostAstronautName);
                    }
                }
            }
            if(mob.getType().equals(EntityType.CREEPER)){
                if(new Random().nextBoolean()) {
                    if (new Random().nextInt(10) > 8 && useSpecialMobs) {
                        mob.setCustomName(ChatColor.GREEN + "" + ChatColor.BOLD + evolvedMoonCreeperName);
                        mob.setCustomNameVisible(true);
                        org.bukkit.entity.Entity newmob = mob.getWorld().spawnEntity(mob.getLocation(), EntityType.PHANTOM);
                        newmob.setSilent(true);
                        newmob.setCustomName(ChatColor.AQUA + MoonPhantomName);
                        newmob.addPassenger(mob);
                    } else {
                        org.bukkit.entity.Entity mober = event.getEntity().getWorld().spawnEntity(event.getLocation(), EntityType.STRAY);
                        mober.setCustomName(ChatColor.AQUA + LostAstronautName);
                        event.setCancelled(true);
                    }
                }
            }
        }
    }


    // Use rocket logic
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(useRockets) {
                Player player = event.getPlayer();
                PlayerInventory playerInv = player.getInventory();
                if(playerInv.getItemInMainHand().equals(new ItemStack(Material.FIREWORK_ROCKET)) || playerInv.getItemInOffHand().equals(new ItemStack(Material.FIREWORK_ROCKET))){
                    if(player.hasPermission("moongenerator.userocket")) {
                        event.getPlayer().sendActionBar(ChatColor.RED + "3 2 1...!");

                        event.setCancelled(true);

                        Firework rocket = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
                        FireworkMeta meta = rocket.getFireworkMeta();

                        meta.setPower(3);

                        rocket.setFireworkMeta(meta);

                        rocket.addPassenger(player);

                        new BukkitRunnable() {
                            Player player = event.getPlayer();
                            org.bukkit.entity.Entity rocket = player.getVehicle();

                            @Override
                            public void run() {
                                if (player.getVehicle() == null) {
                                    player.sendActionBar(ChatColor.RED + missionFailed);
                                    cancel();
                                } else {
                                    if (player.getVehicle().equals(rocket)) {
                                        if (player.getLocation().getY() >= 250) {
                                            if (moonWorldName.compareToIgnoreCase(player.getWorld().getName()) != 0) {
                                                player.sendActionBar(ChatColor.GREEN + toTheMoon);
                                                int x = (int) player.getLocation().getX();
                                                int z = (int) player.getLocation().getZ();
                                                try {
                                                    player.teleport(new Location(Bukkit.getWorld(moonWorldName), x, 255, z));
                                                } catch (Exception e) {
                                                    player.sendActionBar(ChatColor.RED + "Moon World " + moonWorldName + " not found.");
                                                }
                                                cancel();
                                            } else {
                                                player.sendActionBar(ChatColor.GREEN + toTheEarth);
                                                int x = (int) player.getLocation().getX();
                                                int z = (int) player.getLocation().getZ();
                                                try {
                                                    player.teleport(new Location(Bukkit.getWorld(earthWorldName), x, 255, z));
                                                } catch (Exception e) {
                                                    player.sendActionBar(ChatColor.RED + "Earth World " + earthWorldName + " not found.");
                                                }
                                                cancel();
                                            }
                                        } else {
                                            player.sendActionBar(ChatColor.DARK_GREEN + "Y: " + (int) player.getLocation().getY());
                                        }
                                    } else {
                                        player.sendActionBar(ChatColor.RED + missionFailed);
                                        cancel();
                                    }
                                }
                            }
                        }.runTaskTimer(this, 20L, 10L);
                    }
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


        // ----------------------

        System.out.println("Trying to load Moon World");

        if( getServer().getWorld("moon") != null ){
            System.out.println("Moon world found!");
        } else {
            System.out.println("Moon world NOT found!");
            System.out.println("Please generate it using /moon");
        }

        // ----------------------

        System.out.println("Enabling Shoko's Moon Generator");

        this.getCommand("moon").setExecutor(new generateMoon());

        saveDefaultConfig();

        FileConfiguration config = plugin.getConfig();

        moonWorldName =  config.getString("moonWorldName").toLowerCase();
        earthWorldName = config.getString("earthWorldName").toLowerCase();

        toTheMoon =  config.getString("toTheMoon");
        toTheEarth =  config.getString("toTheEarth");

        missionFailed =  config.getString("missionFailed");

        useSkullHelmets = config.getBoolean("useSkullHelmets");

        useGravity = config.getBoolean("useGravity");
        useEffects = config.getBoolean("useEffects");
        useParticles = config.getBoolean("useParticles");
        useGlassHelmets = config.getBoolean("useGlassHelmets");
        useCustomBlockDrops = config.getBoolean("useCustomBlockDrops");
        useRockets = config.getBoolean("useRockets");

        useCustomMobs = config.getBoolean("useCustomMobs");
        useSpecialMobs = config.getBoolean("useSpecialMobs");

        useOxygenSafeZone = config.getBoolean("useOxygenSafeZone");

        configLore = config.getStringList("CustomBlockLore");
        MoonRockName = config.getString("MoonRockName");
        SoftMoonRockName = config.getString("SoftMoonRockName");
        MoonCobblestoneName = config.getString("MoonCobblestoneName");

        LostAstronautName = config.getString("LostAstronautName");
        MoonPhantomName = config.getString("MoonPhantomName");
        LostMoonExplorerName = config.getString("LostMoonExplorerName");
        FailedExperimentalHorseName = config.getString("FailedExperimentalHorseName");
        evolvedMoonCreeperName = config.getString("evolvedMoonCreeperName");

        MoonMobSufixName = config.getString("MoonMobSufixName");
        MoonMobPrefixName = config.getString("MoonMobPrefixName");

        Bukkit.getPluginManager().registerEvents(this, this);

        BukkitScheduler scheduler = getServer().getScheduler();

        // Apply effects to players in the worlds marked as Moons
        if(useEffects) {
            scheduler.scheduleSyncRepeatingTask(this, () -> {
                for (World world : Bukkit.getServer().getWorlds()) {
                    if (moonWorldName.compareToIgnoreCase(world.getName()) == 0) {
                        for (Player p : world.getPlayers()) {
                            if (!p.getGameMode().equals(GameMode.CREATIVE) && !p.getGameMode().equals(GameMode.SPECTATOR)) {
                                boolean isNearOxygen = false;
                                if(useOxygenSafeZone) {
                                    int radius = 10;
                                    Block middle = p.getLocation().getBlock();
                                    for (int x = radius; x >= -radius; x--) {
                                        for (int y = radius; y >= -radius; y--) {
                                            for (int z = radius; z >= -radius; z--) {
                                                Material looped = middle.getRelative(x, y, z).getType();
                                                if (looped.equals(Material.OAK_LEAVES) || looped.equals(Material.ACACIA_LEAVES) || looped.equals(Material.BIRCH_LEAVES)
                                                        || looped.equals(Material.DARK_OAK_LEAVES) || looped.equals(Material.JUNGLE_LEAVES) || looped.equals(Material.SPRUCE_LEAVES)
                                                        || looped.equals(Material.SPONGE)) {
                                                    isNearOxygen = true;
                                                }
                                            }
                                        }
                                    }
                                }
                                PotionEffect efecto1 = new PotionEffect(PotionEffectType.SLOW_FALLING, 40, 3);
                                PotionEffect efecto2 = new PotionEffect(PotionEffectType.JUMP, 40, 3);

                                p.addPotionEffect(efecto1, true);
                                p.addPotionEffect(efecto2, true);

                                if (!isNearOxygen) {
                                    PotionEffect efecto3 = new PotionEffect(PotionEffectType.WITHER, 40, 1);
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
                                            p.playSound(p.getLocation(), Sound.ENTITY_DROWNED_DEATH, 1f, 1f);
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
        System.out.println("Good bye! love you <3 -Shoko");
    }

}
