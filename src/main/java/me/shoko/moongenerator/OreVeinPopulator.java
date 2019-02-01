package me.shoko.moongenerator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class OreVeinPopulator extends BlockPopulator {

    private static int randomIntBetween(int min, int max) {
        if (min >= max) {
            Random r = new Random();
            return r.nextInt((min - max) + 1) + max;
        } else {
            Random r = new Random();
            return r.nextInt((max - min) + 1) + min;
        }
    }

    public void populate(World world, Random random, Chunk chunk) {
        int amount = randomIntBetween(25, 60);
        for (int i = 1; i < amount; i++) {
            int maxY = 60;
            int X = random.nextInt(15);
            int Z = random.nextInt(15);

            for (int j = world.getMaxHeight() - 1; chunk.getBlock(X, j, Z).getType() == Material.AIR; j--) maxY = j;

            int maxVeinY = maxY-4;
            int Y;
            Material ore;
            int propagation;
            int orePicker = random.nextInt(99) + 1;
            if (orePicker > 90) {
                ore = Material.DIAMOND_ORE;
                propagation = randomIntBetween(0,3);
                Y = randomIntBetween(1, 30);
            } else if (orePicker > 65) {
                ore = Material.GOLD_ORE;
                propagation = randomIntBetween(1,4);
                Y = randomIntBetween(3, (int) (maxVeinY * 0.6));
            } else if (orePicker > 50) {
                ore = Material.REDSTONE_ORE;
                propagation = randomIntBetween(2,5);
                Y = randomIntBetween(1, (int) (maxVeinY * 0.4));
            } else if (orePicker > 35) {
                ore = Material.IRON_ORE;
                propagation = randomIntBetween(5,10);
                Y = randomIntBetween(3, (int) (maxVeinY * 0.9));
            } else if (orePicker > 35) {
                ore = Material.LAPIS_ORE;
                propagation = randomIntBetween(5,10);
                Y = randomIntBetween(3, (int) (maxVeinY * 0.5));
            } else {
                ore = Material.COAL_ORE;
                propagation = randomIntBetween(5,10);
                Y = randomIntBetween(1, maxVeinY);
            }

            if(orePicker == 6) {
                ore = Material.EMERALD_ORE;
                propagation = 0;
                Y = randomIntBetween(1, (int) (maxVeinY * 0.2));
            }

            int offsetX = chunk.getX() << 4;
            int offsetZ = chunk.getZ() << 4;

            if(world.getBlockAt(offsetX + X, Y, offsetZ + Z).getType().compareTo(Material.BEDROCK)!=0&&world.getBlockAt(offsetX + X, Y, offsetZ + Z).getType().compareTo(Material.AIR)!=0&&world.getBlockAt(offsetX + X, Y+1, offsetZ + Z).getType().compareTo(Material.AIR)!=0)
            world.getBlockAt(offsetX + X, Y, offsetZ + Z).setType(ore, false);

            for(int prop = 0; prop < propagation; prop++){
                if(world.getBlockAt(offsetX + X, Y, offsetZ + Z).getType().compareTo(Material.BEDROCK)!=0&&world.getBlockAt(offsetX + X, Y, offsetZ + Z).getType().compareTo(Material.AIR)!=0&&world.getBlockAt(offsetX + X, Y+1, offsetZ + Z).getType().compareTo(Material.AIR)!=0)
                world.getBlockAt(offsetX + X, Y, offsetZ + Z).setType(ore, false);

                orePicker = random.nextInt(2);
                if(orePicker == 0) {
                    X = randomIntBetween(X - 1, X + 1);
                } else if (orePicker == 1) {
                    Y = randomIntBetween(Y - 1, Y + 1);
                } else if (orePicker == 2) {
                    Z = randomIntBetween(Z - 1, Z + 1);
                }
            }
        }
    }
}
