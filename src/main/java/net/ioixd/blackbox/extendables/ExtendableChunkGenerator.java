package net.ioixd.blackbox.extendables;

import java.util.List;
import java.util.Random;

import org.bukkit.HeightMap;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

public class ExtendableChunkGenerator extends ChunkGenerator {

    @Override
    public boolean canSpawn(World world, int x, int z) {
        // TODO Auto-generated method stub
        return super.canSpawn(world, x, z);
    }

    @Override
    public void generateBedrock(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        // TODO Auto-generated method stub
        super.generateBedrock(worldInfo, random, chunkX, chunkZ, chunkData);
    }

    @Override
    public void generateCaves(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        // TODO Auto-generated method stub
        super.generateCaves(worldInfo, random, chunkX, chunkZ, chunkData);
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        // TODO Auto-generated method stub
        return super.generateChunkData(world, random, x, z, biome);
    }

    @Override
    public void generateNoise(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        // TODO Auto-generated method stub
        super.generateNoise(worldInfo, random, chunkX, chunkZ, chunkData);
    }

    @Override
    public void generateSurface(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        // TODO Auto-generated method stub
        super.generateSurface(worldInfo, random, chunkX, chunkZ, chunkData);
    }

    @Override
    public int getBaseHeight(WorldInfo worldInfo, Random random, int x, int z, HeightMap heightMap) {
        // TODO Auto-generated method stub
        return super.getBaseHeight(worldInfo, random, x, z, heightMap);
    }

    @Override
    public BiomeProvider getDefaultBiomeProvider(WorldInfo worldInfo) {
        // TODO Auto-generated method stub
        return super.getDefaultBiomeProvider(worldInfo);
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        // TODO Auto-generated method stub
        return super.getDefaultPopulators(world);
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        // TODO Auto-generated method stub
        return super.getFixedSpawnLocation(world, random);
    }

    @Override
    public boolean isParallelCapable() {
        // TODO Auto-generated method stub
        return super.isParallelCapable();
    }

    @Override
    public boolean shouldGenerateBedrock() {
        // TODO Auto-generated method stub
        return super.shouldGenerateBedrock();
    }

    @Override
    public boolean shouldGenerateCaves() {
        // TODO Auto-generated method stub
        return super.shouldGenerateCaves();
    }

    @Override
    public boolean shouldGenerateCaves(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        // TODO Auto-generated method stub
        return super.shouldGenerateCaves(worldInfo, random, chunkX, chunkZ);
    }

    @Override
    public boolean shouldGenerateDecorations() {
        // TODO Auto-generated method stub
        return super.shouldGenerateDecorations();
    }

    @Override
    public boolean shouldGenerateDecorations(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        // TODO Auto-generated method stub
        return super.shouldGenerateDecorations(worldInfo, random, chunkX, chunkZ);
    }

    @Override
    public boolean shouldGenerateMobs() {
        // TODO Auto-generated method stub
        return super.shouldGenerateMobs();
    }

    @Override
    public boolean shouldGenerateMobs(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        // TODO Auto-generated method stub
        return super.shouldGenerateMobs(worldInfo, random, chunkX, chunkZ);
    }

    @Override
    public boolean shouldGenerateNoise() {
        // TODO Auto-generated method stub
        return super.shouldGenerateNoise();
    }

    @Override
    public boolean shouldGenerateNoise(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        // TODO Auto-generated method stub
        return super.shouldGenerateNoise(worldInfo, random, chunkX, chunkZ);
    }

    @Override
    public boolean shouldGenerateStructures() {
        // TODO Auto-generated method stub
        return super.shouldGenerateStructures();
    }

    @Override
    public boolean shouldGenerateStructures(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        // TODO Auto-generated method stub
        return super.shouldGenerateStructures(worldInfo, random, chunkX, chunkZ);
    }

    @Override
    public boolean shouldGenerateSurface() {
        // TODO Auto-generated method stub
        return super.shouldGenerateSurface();
    }

    @Override
    public boolean shouldGenerateSurface(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        // TODO Auto-generated method stub
        return super.shouldGenerateSurface(worldInfo, random, chunkX, chunkZ);
    }

}
