package me.shoko.moongenerator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import java.util.*;

public class ChunkMoonGenerator extends ChunkGenerator {

    JavaPlugin plugin = this.plugin;

    int currentHeight = 60;

    public float lerp(float min, float max, float norm){
        float res = (max - min) * norm + min;
        return res;
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        ChunkGenerator.ChunkData chunk = createChunkData(world);

        SimplexOctaveGenerator terrainGen = new SimplexOctaveGenerator(new Random(world.getSeed()), 2);

        terrainGen.setScale(0.0056D);

        for (int X = 0; X < 16; X++) {
            for (int Z = 0; Z < 16; Z++) {

                float terrainNoise = (float) (terrainGen.noise(chunkX * 16 + X, chunkZ * 16 + Z, 0.05D, 0.8D, true));

                currentHeight = (int) ( lerp(0f, 1f, terrainNoise) * 15D + 60D);

                if(currentHeight > 200) currentHeight = 200;
                if(currentHeight < 16) currentHeight = 16;


                chunk.setBlock(X, currentHeight, Z, Material.DEAD_BRAIN_CORAL_BLOCK);
                if (random.nextInt(100) < 90) {
                    chunk.setBlock(X, currentHeight - 1, Z, Material.DEAD_BUBBLE_CORAL_BLOCK);
                } else {
                    chunk.setBlock(X, currentHeight - 1, Z, Material.DEAD_HORN_CORAL_BLOCK);
                }
                chunk.setBlock(X, currentHeight - 2, Z, Material.DEAD_BUBBLE_CORAL_BLOCK);
                chunk.setBlock(X, currentHeight - 3, Z, Material.DEAD_BUBBLE_CORAL_BLOCK);

                for (int i = 0; i < currentHeight - 3; i++) {
                    if (i > currentHeight * 0.8) {
                        chunk.setBlock(X, i, Z, Material.DEAD_FIRE_CORAL_BLOCK);
                    } else if (i > currentHeight * 0.6) {
                        chunk.setBlock(X, i, Z, Material.DEAD_TUBE_CORAL_BLOCK);
                    } else if (i > currentHeight * 0.4) {
                        chunk.setBlock(X, i, Z, Material.ANDESITE);
                    } else if (i > currentHeight * 0.1) {
                        chunk.setBlock(X, i, Z, Material.PACKED_ICE);
                    } else {
                        chunk.setBlock(X, i, Z, Material.BLUE_ICE);
                    }
                    if (i <= 2){
                        if(random.nextBoolean() && random.nextBoolean() && random.nextBoolean())
                            chunk.setBlock(X, i, Z, Material.BEDROCK);
                    }

                }
                chunk.setBlock(X, 0, Z, Material.BEDROCK);

                biome.setBiome(X, Z, Biome.DESERT);
            }
        }
        return chunk;
    }
/*
    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Arrays.asList(new CraterPopulator(), new OreVeinPopulator(), new FloraPopulator());
    }
*/


}