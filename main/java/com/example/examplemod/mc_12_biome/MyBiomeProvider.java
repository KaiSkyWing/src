package com.example.examplemod.mc_12_biome;

import com.example.examplemod.ExampleMod;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.BiomeProvider;
import terrablender.worldgen.TBClimate;

import java.util.function.Consumer;

public class MyBiomeProvider extends BiomeProvider {
    public MyBiomeProvider(ResourceLocation name, int weight){
        super(name, weight);
    }

    @Override
    public void addOverworldBiomes(Registry<Biome> registry, Consumer<Pair<TBClimate.ParameterPoint, ResourceKey<Biome>>> mapper){
        this.addBiome(mapper,
                Climate.Parameter.point(0.0F),
                Climate.Parameter.point(0.0F),
                Climate.Parameter.point(0.0F),
                Climate.Parameter.point(0.0F),
                Climate.Parameter.point(0.0F),
                Climate.Parameter.point(0.0F),
                0.0F,
                ExampleMod.MY_BIOME);
    }
}
