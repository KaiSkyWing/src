package com.example.examplemod.mc_12_biome;

import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

import static net.minecraft.data.worldgen.features.OreFeatures.STONE_ORE_REPLACEABLES;
import static net.minecraft.data.worldgen.placement.VegetationPlacements.TREE_THRESHOLD;


public class BiomeUtil {
    public static PlacedFeature makeFlowerSpawnSetting(FlowerData[] list, String name, int amount) {
        SimpleWeightedRandomList.Builder<BlockState> simpleWeightedRandomList = SimpleWeightedRandomList.builder();
        for (FlowerData flowerData : list) {
            simpleWeightedRandomList.add(flowerData.block.defaultBlockState(), flowerData.weight);
        }
        ConfiguredFeature<RandomPatchConfiguration, ?> MY_BIOME_FLOWER_CONFIGURED =
                FeatureUtils.register(
                        name,
                        Feature.FLOWER.configured(grassPatch(new WeightedStateProvider(
                                simpleWeightedRandomList
                        ), 64))
                );

        return PlacementUtils.register(
                name,
                MY_BIOME_FLOWER_CONFIGURED.placed(
                        CountPlacement.of(amount),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome())
        );
    }

    public static PlacedFeature makeOreSpawnSetting(String name, OreData oreData) {
        OreConfiguration oreConfiguration = new OreConfiguration(STONE_ORE_REPLACEABLES, oreData.block.defaultBlockState(), oreData.size);
        ConfiguredFeature<?, ?> MY_BIOME_ORE_CONFIGURED = FeatureUtils.register(name, Feature.ORE.configured(oreConfiguration));
        return PlacementUtils.register(name, MY_BIOME_ORE_CONFIGURED.placed(commonOrePlacement(oreData.count, HeightRangePlacement.uniform(VerticalAnchor.absolute(oreData.lowerRangeLimit), VerticalAnchor.absolute(oreData.upperRangeLimit)))));
    }

    // TODO:木のパラメータ確認

    /**
     * makeTreeSetting
     * biomeに渡す木の値を引数の値に応じて生成して返します(簡易版)
     * どんな木を生成するかだけを決める用
     *
     * @param name      String型 登録名
     * @param treeTrunk Block型 木の幹の部分に設定したいブロック
     * @param leave     Block型 葉っぱに設定したいブロック
     * @param sapling   Block型 苗木に設定したいブロック
     * @return ConfiguredFeature<?, ?>
     */
    public static PlacedFeature makeTreeSetting(String name, Block treeTrunk, Block leave, Block sapling) {
        return makeTreeSetting(name, treeTrunk, leave, sapling, 4, 2, 3, 2);
    }

    /**
     * makeTreeSetting
     * biomeに渡す木の値を引数の値に応じて生成して返します(簡易版)
     * どんな木を生成するか、木の生成確率を決める用
     *
     * @param name              String型 登録名
     * @param treeTrunk         Block型 木の幹の部分に設定したいブロック
     * @param leave             Block型 葉っぱに設定したいブロック
     * @param sapling           Block型 苗木に設定したいブロック
     * @param treeGenerateCount int型 木の生成確率を指定する、100だと足の踏み場もない
     * @return ConfiguredFeature<?, ?>
     */
    public static PlacedFeature makeTreeSetting(String name, Block treeTrunk, Block leave, Block sapling, int treeGenerateCount) {
        return makeTreeSetting(name, treeTrunk, leave, sapling, 4, 2, 3, treeGenerateCount);
    }

    /**
     * makeTreeSetting
     * biomeに渡す木の値を引数の値に応じて生成して返します
     *
     * @param name              String型 登録名
     * @param treeTrunk         Block型 木の幹の部分に設定したいブロック
     * @param leave             Block型 葉っぱに設定したいブロック
     * @param sapling           Block型 苗木に設定したいブロック
     * @param treeTrunkHeight   int型 木の幹の高さを指定する
     * @param leaveRadius       int型 葉っぱの半径を指定する
     * @param leaveHeight       int型 葉っぱの部分の高さを指定する
     * @param treeGenerateCount int型 木の生成確率を指定する、100だと足の踏み場もない
     * @return ConfiguredFeature<?, ?>
     */
    public static PlacedFeature makeTreeSetting(String name, Block treeTrunk, Block leave, Block sapling, int treeTrunkHeight, int leaveRadius, int leaveHeight, int treeGenerateCount) {
        TreeConfiguration treeConfiguration =
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(treeTrunk),
                        new StraightTrunkPlacer(treeTrunkHeight, 2, 0),
                        BlockStateProvider.simple(leave),
                        new BlobFoliagePlacer(ConstantInt.of(leaveRadius), ConstantInt.of(0), leaveHeight),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().build();

        ConfiguredFeature<?, ?> MY_BIOME_TREE_CONFIGURED = FeatureUtils.register(name, Feature.TREE.configured(treeConfiguration));

        return PlacementUtils.register(name, MY_BIOME_TREE_CONFIGURED.placed(
                CountPlacement.of(treeGenerateCount),
                InSquarePlacement.spread(),
                TREE_THRESHOLD,
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(sapling.defaultBlockState(), BlockPos.ZERO)),
                BiomeFilter.biome()));
    }

    static class FlowerData {
        Block block;
        int weight;

        public FlowerData(Block block, int weight) {
            this.block = block;
            this.weight = weight;
        }
    }

    static class OreData {
        Block block;
        int size;
        int count;
        int lowerRangeLimit;
        int upperRangeLimit;

        public OreData(Block block, int size, int count, int lowerRangeLimit, int upperRangeLimit) {
            this.block = block;
            this.size = size;
            this.count = count;
            this.lowerRangeLimit = lowerRangeLimit;
            this.upperRangeLimit = upperRangeLimit;
        }
    }

    private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    private static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
        return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(p_195203_)).onlyWhenEmpty());
    }
}
