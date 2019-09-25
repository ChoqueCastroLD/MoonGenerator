package me.shoko.moongenerator;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class OreVeinPopulator extends BlockPopulator {

    JavaPlugin plugin;

    private int randomIntBetween(int min, int max) {
        if (min >= max) {
            Random r = new Random();
            return r.nextInt((min - max) + 1) + max;
        } else {
            Random r = new Random();
            return r.nextInt((max - min) + 1) + min;
        }
    }

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        int amount = randomIntBetween(20, 60);
        for (int i = 1; i < amount; i++) {
            int maxY = 60;
            int X = random.nextInt(15);
            int Z = random.nextInt(15);

            for (int j = world.getMaxHeight() - 1; chunk.getBlock(X, j, Z).getType() == Material.AIR; j--) maxY = j;

            int maxVeinY = maxY - 4;
            int Y;
            Material ore;
            int propagation;
            int orePicker = random.nextInt(99) + 1;
            if (orePicker > 90) {
                ore = Material.DIAMOND_ORE;
                propagation = randomIntBetween(0, 3);
                Y = randomIntBetween(1, 30);
            } else if (orePicker > 65) {
                ore = Material.GOLD_ORE;
                propagation = randomIntBetween(1, 4);
                Y = randomIntBetween(3, (int) (maxVeinY * 0.6));
            } else if (orePicker > 50) {
                ore = Material.REDSTONE_ORE;
                propagation = randomIntBetween(2, 5);
                Y = randomIntBetween(1, (int) (maxVeinY * 0.4));
            } else if (orePicker > 35) {
                ore = Material.IRON_ORE;
                propagation = randomIntBetween(5, 10);
                Y = randomIntBetween(3, (int) (maxVeinY * 0.9));
            } else if (orePicker > 25) {
                ore = Material.LAPIS_ORE;
                propagation = randomIntBetween(5, 10);
                Y = randomIntBetween(3, (int) (maxVeinY * 0.5));
            } else {
                ore = Material.COAL_ORE;
                propagation = randomIntBetween(5, 10);
                Y = randomIntBetween(1, maxVeinY);
            }

            if (orePicker == 6) {
                ore = Material.EMERALD_ORE;
                propagation = 1;
                Y = randomIntBetween(1, (int) (maxVeinY * 0.2));
            }

            Block b = world.getBlockAt((chunk.getX() << 4) + X, Y, (chunk.getZ() << 4) + Z);

            if (b.getType().equals(Material.BEDROCK) && !b.getType().equals(Material.AIR))
                b.setType(ore, false);

            int propX = (chunk.getX() << 4) + X;
            int propZ = (chunk.getZ() << 4) + Z;
            for (int prop = 0; prop < propagation + 1; prop++) {
                try {
                    b = world.getBlockAt(propX, Y, propZ);

                    if (!b.getType().equals(Material.BEDROCK) && !b.getType().equals(Material.AIR))
                        b.setType(ore, false);

                    orePicker = random.nextInt(2);
                    if (orePicker == 0) {
                        propX = randomIntBetween(propX - 1, propX + 1);
                    } else if (orePicker == 1) {
                        Y = randomIntBetween(Y - 1, Y + 1);
                        if (Y < 1) Y = 1;
                    } else if (orePicker == 2) {
                        propZ = randomIntBetween(propZ - 1, propZ + 1);
                    }
                } catch (Exception e) {
                }

            }
        }
    }
}
