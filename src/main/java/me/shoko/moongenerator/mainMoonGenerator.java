package me.shoko.moongenerator;


import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;
import java.util.Random;


public final class mainMoonGenerator extends JavaPlugin implements Listener {

    // Global variables and stuff
    JavaPlugin plugin = this;
    List<String> configLore;
    FileConfiguration config;

    // Change the block drops in the specified worlds
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void BlockBreakEvent(BlockBreakEvent event){
        if(config.getBoolean("useCustomBlockDrops")) {
            if (config.getString("moonWorldName").compareToIgnoreCase(event.getBlock().getWorld().getName()) == 0) {
                if (event.getBlock().getType().equals(Material.DEAD_BRAIN_CORAL_BLOCK)) {
                    event.setCancelled(true);

                    ItemStack drop = new ItemStack(Material.DEAD_BRAIN_CORAL_BLOCK);
                    ItemMeta meta = drop.getItemMeta();
                    meta.setDisplayName(ChatColor.RESET+config.getString("MoonRockName"));
                    drop.setItemMeta(meta);

                    event.getBlock().setType(Material.AIR);
                    event.getPlayer().getWorld().dropItem(event.getBlock().getLocation(), drop);
                }
                if (event.getBlock().getType().equals(Material.DEAD_BUBBLE_CORAL_BLOCK)) {
                    event.setCancelled(true);

                    ItemStack drop = new ItemStack(Material.DEAD_BUBBLE_CORAL_BLOCK);
                    ItemMeta meta = drop.getItemMeta();
                    meta.setDisplayName(ChatColor.RESET+config.getString("SoftMoonRockName"));
                    meta.setLore(configLore);
                    drop.setItemMeta(meta);

                    event.getBlock().setType(Material.AIR);
                    event.getPlayer().getWorld().dropItem(event.getBlock().getLocation(), drop);
                }
                if (event.getBlock().getType().equals(Material.DEAD_TUBE_CORAL_BLOCK)||event.getBlock().getType().equals(Material.DEAD_FIRE_CORAL_BLOCK)||event.getBlock().getType().equals(Material.DEAD_HORN_CORAL_BLOCK)) {
                    event.setCancelled(true);

                    ItemStack drop = new ItemStack(Material.DEAD_HORN_CORAL_BLOCK);
                    ItemMeta meta = drop.getItemMeta();
                    meta.setDisplayName(ChatColor.RESET+config.getString("MoonCobblestoneName"));
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
        if (config.getString("moonWorldName").compareToIgnoreCase(event.getEntity().getWorld().getName()) == 0 && config.getBoolean("useCustomMobs")) {
            LivingEntity mob = event.getEntity();
            PotionEffect efecto1 = new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 3);
            PotionEffect efecto2 = new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 3);

            mob.addPotionEffect(efecto1, true);
            mob.addPotionEffect(efecto2, true);

            mob.getEquipment().setHelmet(new ItemStack(Material.GLASS));

            mob.setCustomName(ChatColor.AQUA+config.getString("MoonMobSufixName")+mob.getType().toString()+config.getString("MoonMobPrefixName"));

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
                    mober.setCustomName(ChatColor.AQUA + config.getString("FailedExperimentalHorseName"));
                    org.bukkit.entity.Entity newmob = mober.getWorld().spawnEntity(mober.getLocation(), EntityType.STRAY);
                    newmob.setSilent(true);
                    newmob.setCustomName(ChatColor.AQUA + config.getString("LostMoonExplorerName"));
                    mober.addPassenger(newmob);
                }
            }
            if(mob.getType().equals(EntityType.ZOMBIE)){
                event.setCancelled(true);
                if(new Random().nextBoolean()) {
                    if (new Random().nextBoolean()) {
                        org.bukkit.entity.Entity mober = event.getEntity().getWorld().spawnEntity(event.getLocation(), EntityType.ZOMBIE_VILLAGER);
                        mober.setCustomName(ChatColor.AQUA + config.getString("LostAstronautName"));
                    } else {
                        org.bukkit.entity.Entity mober = event.getEntity().getWorld().spawnEntity(event.getLocation(), EntityType.DROWNED);
                        mober.setCustomName(ChatColor.AQUA + config.getString("LostAstronautName"));
                    }
                }
            }
            if(mob.getType().equals(EntityType.CREEPER)){
                if(new Random().nextBoolean()) {
                    if (new Random().nextInt(10) > 8 && config.getBoolean("useSpecialMobs")) {
                        mob.setCustomName(ChatColor.GREEN + "" + ChatColor.BOLD + config.getString("evolvedMoonCreeperName"));
                        mob.setCustomNameVisible(true);
                        org.bukkit.entity.Entity newmob = mob.getWorld().spawnEntity(mob.getLocation(), EntityType.PHANTOM);
                        newmob.setSilent(true);
                        newmob.setCustomName(ChatColor.AQUA + config.getString("MoonPhantomName"));
                        newmob.addPassenger(mob);
                    } else {
                        org.bukkit.entity.Entity mober = event.getEntity().getWorld().spawnEntity(event.getLocation(), EntityType.STRAY);
                        mober.setCustomName(ChatColor.AQUA + config.getString("LostAstronautName"));
                        event.setCancelled(true);
                    }
                }
            }
        }
    }


    // Use rocket logic
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !event.getPlayer().isSneaking()) {
            if(config.getBoolean("useRockets")) {
                Player player = event.getPlayer();
                PlayerInventory playerInv = player.getInventory();
                if(playerInv.getItemInMainHand().getType().equals(new ItemStack(Material.FIREWORK_ROCKET).getType()) || playerInv.getItemInOffHand().getType().equals(new ItemStack(Material.FIREWORK_ROCKET).getType())){
                    if(player.hasPermission("moongenerator.userocket") || player.isOp()) {
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
                                    player.sendActionBar(ChatColor.RED + config.getString("missionFailed"));
                                    cancel();
                                } else {
                                    if (player.getVehicle().equals(rocket)) {
                                        if (player.getLocation().getY() >= 115) {
                                            if (config.getString("moonWorldName").compareToIgnoreCase(player.getWorld().getName()) != 0) {
                                                player.sendActionBar(ChatColor.GREEN + config.getString("toTheMoon"));
                                                int x = (int) player.getLocation().getX();
                                                int z = (int) player.getLocation().getZ();
                                                try {
                                                    player.teleport(new Location(Bukkit.getWorld(config.getString("moonWorldName")), x, 255, z));
                                                } catch (Exception e) {
                                                    player.sendActionBar(ChatColor.RED + "Moon World " + config.getString("moonWorldName") + " not found.");
                                                }
                                                cancel();
                                            } else {
                                                player.sendActionBar(ChatColor.GREEN + config.getString("toTheEarth"));
                                                int x = (int) player.getLocation().getX();
                                                int z = (int) player.getLocation().getZ();
                                                try {
                                                    player.teleport(new Location(Bukkit.getWorld(config.getString("earthWorldName")), x, 255, z));
                                                } catch (Exception e) {
                                                    player.sendActionBar(ChatColor.RED + "Earth World " + config.getString("earthWorldName") + " not found.");
                                                }
                                                cancel();
                                            }
                                        } else {
                                            player.sendActionBar(ChatColor.DARK_GREEN + "Y: " + (int) player.getLocation().getY());
                                        }
                                    } else {
                                        player.sendActionBar(ChatColor.RED + config.getString("missionFailed"));
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
        if(config.getBoolean("useGlassHelmets")) {
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

    public void log(String msg){
        Bukkit.getLogger().info(msg);
    }

    @Override
    public void onEnable() {
        log("Enabling Shoko's Moon Generator");

        saveDefaultConfig();

        config = plugin.getConfig();

        // ----------------------

        // generates or load moon world

        log("Trying to load Moon World");

        boolean moonWorldExists = false;

        for (World world: getServer().getWorlds()) {
            if(world.getName().toLowerCase().equals(config.getString("moonWorldName"))){
                moonWorldExists = true;
            }
        }

        if(!moonWorldExists){
            log("Moon world named '"+config.getString("moonWorldName")+"' found!");
            World w = getServer().createWorld(new WorldCreator(config.getString("moonWorldName")));
            w.getPopulators().add(new OreVeinPopulator());
            w.getPopulators().add(new FloraPopulator());

            w.setTime(24000);
            w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);

            log("Moon loaded");
        } else {
            log("Moon world named '"+config.getString("moonWorldName")+"' NOT found!");
            log("It will be generate on load");
            new BukkitRunnable() {
                @Override
                public void run() {
                    WorldCreator wc = new WorldCreator(config.getString("moonWorldName"));
                    wc.generator(new ChunkMoonGenerator());
                    World w = wc.createWorld();
                    log("Moon world named '"+config.getString("moonWorldName")+"' Generated!");


                    // w.getPopulators().add(new CraterPopulator());
                    w.getPopulators().add(new OreVeinPopulator());
                    w.getPopulators().add(new FloraPopulator());


                    Chunk[] cs = w.getLoadedChunks();
                    for (Chunk chunk : cs) {
                        chunk.load(true);
                    }
                }
            }.runTaskLater(plugin,1);

        }

        // ----------------------

        Bukkit.getPluginManager().registerEvents(this, this);

        BukkitScheduler scheduler = getServer().getScheduler();

        // Apply effects to players in the worlds marked as Moons
        if(config.getBoolean("useEffects")) {
            scheduler.scheduleSyncRepeatingTask(this, () -> {
                for (World world : Bukkit.getServer().getWorlds()) {
                    if (config.getString("moonWorldName").compareToIgnoreCase(world.getName()) == 0) {
                        for (Player p : world.getPlayers()) {
                            if (!p.getGameMode().equals(GameMode.CREATIVE) && !p.getGameMode().equals(GameMode.SPECTATOR)) {
                                boolean isNearOxygen = false;
                                if(config.getBoolean("useOxygenSafeZone")) {
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
                                            if (config.getBoolean("useParticles"))
                                                p.spawnParticle(Particle.DAMAGE_INDICATOR, p.getLocation().getX(), p.getLocation().getY() + 1, p.getLocation().getZ(), 15);
                                            p.playSound(p.getLocation(), Sound.ENTITY_DROWNED_DEATH, 1f, 1f);
                                        }
                                    } else {
                                        p.addPotionEffect(efecto3, true);
                                        if (config.getBoolean("useParticles"))
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
        log("Stopping Shoko's Moon Generator");
        log("Good bye! love you <3 -Shoko");
    }

}
