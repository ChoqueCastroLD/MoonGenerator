package me.shoko.moongenerator;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

public class FloraPopulator extends BlockPopulator {

    static void setBlockAt(final Chunk chunk, final int x, final int y, final int z, final Material material) {
        final Waterlogged data = (Waterlogged) material.createBlockData();
        data.setWaterlogged(false);
        final Block block = chunk.getBlock(x, y, z);
        block.setBlockData(data, false);
    }

    int amount;

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        if (random.nextBoolean()) {
            amount = random.nextInt(4) + 1;
            for (int i = 1; i < amount; i++) {
                int X = random.nextInt(15);
                int Z = random.nextInt(15);
                int Y = 1;
                for (int j = world.getMaxHeight() - 1; chunk.getBlock(X, j, Z).getType() == Material.AIR; j--) Y = j;

                int chance = random.nextInt(100);
                if (chance > 97) {
                    setBlockAt(chunk, X, Y, Z, Material.DEAD_FIRE_CORAL_FAN);
                    chunk.getBlock(X, Y - 1, Z).setType(Material.ANDESITE, false);
                    chunk.getBlock(X, Y - 2, Z).setType(Material.DIAMOND_ORE, false);
                } else if (chance > 86) {
                    setBlockAt(chunk, X, Y, Z, Material.DEAD_BUBBLE_CORAL);
                    chunk.getBlock(X, Y - 1, Z).setType(Material.ANDESITE, false);
                    chunk.getBlock(X, Y - 2, Z).setType(Material.IRON_ORE, false);
                } else if (chance > 64) {
                    setBlockAt(chunk, X, Y, Z, Material.DEAD_TUBE_CORAL);
                    chunk.getBlock(X, Y - 1, Z).setType(Material.ANDESITE, false);
                    chunk.getBlock(X, Y - 2, Z).setType(Material.COAL_ORE, false);
                } else if (chance > 54)  {
                    setBlockAt(chunk, X, Y, Z, Material.DEAD_TUBE_CORAL_FAN);
                    chunk.getBlock(X, Y - 1, Z).setType(Material.ANDESITE, false);
                    if (random.nextBoolean()) chunk.getBlock(X, Y - 2, Z).setType(Material.COBBLESTONE, false);
                } else if (chance > 34)  {
                    setBlockAt(chunk, X, Y, Z, Material.DEAD_HORN_CORAL_FAN);
                    chunk.getBlock(X, Y - 1, Z).setType(Material.ANDESITE, false);
                    if (random.nextBoolean()) chunk.getBlock(X, Y - 2, Z).setType(Material.COBBLESTONE, false);
                } else {
                    setBlockAt(chunk, X, Y, Z, Material.DEAD_FIRE_CORAL_FAN);
                    chunk.getBlock(X, Y - 1, Z).setType(Material.ANDESITE, false);
                    if (random.nextBoolean()) setBlockAt(chunk, X, Y, Z, Material.DEAD_TUBE_CORAL_FAN);
                    if (random.nextBoolean()) chunk.getBlock(X, Y - 2, Z).setType(Material.COBBLESTONE, false);
                }
            }
        }
    }
}
