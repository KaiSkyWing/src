package com.example.examplemod.mc_12_biome;

import com.example.examplemod.ExampleMod;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BiomeMyBiome {
    public static Biome makeMyBiome(){
        //1
        BiomeGenerationSettings.Builder biomeGenerationSettingBuilder =
                new BiomeGenerationSettings.Builder();

        //3
        //花の設定
        PlacedFeature flowerSettings =
                BiomeUtil.makeFlowerSpawnSetting(
                        new BiomeUtil.FlowerData[]{
                                new BiomeUtil.FlowerData(Blocks.POPPY, 1),
                                new BiomeUtil.FlowerData(Blocks.DANDELION, 2)
                        },
                        "my_biome_flower", 10
                );
        biomeGenerationSettingBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, flowerSettings);

        //地下に生成する鉱石ブロックの設定
        PlacedFeature oreSettings =
                BiomeUtil.makeOreSpawnSetting(
                        "my_biome_ore",
                        new BiomeUtil.OreData(Blocks.DIAMOND_BLOCK, 12, 256, 58, 63)
                );
        biomeGenerationSettingBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, oreSettings);

        //木の設定
        PlacedFeature myBiomeTree =
                BiomeUtil.makeTreeSetting(
                        "my_biome_tree",
                        Blocks.OAK_LOG,
                        Blocks.OAK_LEAVES,
                        Blocks.OAK_SAPLING
                );
        biomeGenerationSettingBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, myBiomeTree);

        //4
        MobSpawnSettings.Builder mobSpawnSettingsBuilder = new MobSpawnSettings.Builder();

        //動物のスポーン
        mobSpawnSettingsBuilder.addSpawn(
                MobCategory.CREATURE,
                new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 1, 100, 100
                )
        );

        //モンスターのスポーン
        mobSpawnSettingsBuilder.addSpawn(
                MobCategory.MONSTER,
                new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 100, 100, 100
                )
        );

        /*
        //オリジナルスポーン
        mobSpawnSettingsBuilder.addSpawn(
                MobCategory.CREATURE,
                new MobSpawnSettings.SpawnerData(ExampleMod.ENTITY_TOBISUKE, 100, 100, 100)
        );
        */

        //2
        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .biomeCategory(Biome.BiomeCategory.ICY)
                .temperature(0.0f)
                .downfall(1.0f)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0xffffff)
                        .waterFogColor(0x000000)
                        .fogColor(0xc0d8ff)
                        .skyColor(0x55ffff)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SOUL_SAND_VALLEY))
                        .build())
                .mobSpawnSettings(mobSpawnSettingsBuilder.build())
                .generationSettings(biomeGenerationSettingBuilder.build())
                .build();

    }
}
