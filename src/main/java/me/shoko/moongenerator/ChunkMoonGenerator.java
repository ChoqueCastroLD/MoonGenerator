package me.shoko.moongenerator;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ChunkMoonGenerator extends ChunkGenerator {

    int currentHeight = 60;

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        ChunkGenerator.ChunkData chunk = createChunkData(world);

        SimplexOctaveGenerator terrainGen = new SimplexOctaveGenerator(new Random(world.getSeed()), 8);

        // generator.setScale(0.005D);
        terrainGen.setScale(0.0056D);

        for (int X = 0; X < 16; X++) {
            for (int Z = 0; Z < 16; Z++) {

                currentHeight = (int) ((terrainGen.noise(chunkX * 16 + X, chunkZ * 16 + Z, 0.5D, 0.5D, true) + 1) * 15D + 50D);

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

            }
        }
        return chunk;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Arrays.asList( new FloraPopulator(), new OreVeinPopulator(), new CraterPopulator() );
    }

}