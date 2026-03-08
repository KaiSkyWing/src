package com.example.examplemod;

import com.example.examplemod.BlockCopier.BlockCopier;
import com.example.examplemod.mc_01_myblock.BlockMyBlock;
import com.example.examplemod.mc_02_fortuneblock.BlockFortune;
import com.example.examplemod.mc_03_magicstick.ItemMagicStick;
import com.example.examplemod.mc_04_hipotion.ItemHiPotion;
import com.example.examplemod.mc_05_mysword.ItemMySword;
import com.example.examplemod.mc_06_rainbowblock.BlockRainbow;
import com.example.examplemod.mc_07_soundblock.BlockSound;
import com.example.examplemod.mc_08_woodcut.BlockBreakEventHandler;
import com.example.examplemod.mc_09_redstone.BlockSensor;
import com.example.examplemod.mc_10_snowball_fight.EntityMySnowball;
import com.example.examplemod.mc_10_snowball_fight.ItemMySnowBall;
import com.example.examplemod.mc_11_footprints_sand.BlockFootprintsSand;
import com.example.examplemod.mc_12_biome.BiomeMyBiome;
import com.example.examplemod.mc_12_biome.MyBiomeProvider;
import com.example.examplemod.mc_13_explosive_arrow.EntityExplosiveArrow;
import com.example.examplemod.mc_13_explosive_arrow.ItemExplosiveArrow;
import com.example.examplemod.mc_13_explosive_arrow.RenderExplosiveArrow;
import com.example.examplemod.mc_14_bull_fighting.EntityBull;
import com.example.examplemod.mc_14_bull_fighting.RenderBull;
import com.example.examplemod.mc_15_tobisuke.EntityTobisuke;
import com.example.examplemod.mc_15_tobisuke.ModelOriginalTobisuke;
import com.example.examplemod.mc_15_tobisuke.ModelTobisuke;
import com.example.examplemod.mc_15_tobisuke.RenderTobisuke;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import terrablender.api.BiomeProviders;

@Mod(ExampleMod.MODID)
public class ExampleMod {

    //MODID
    public static final String MODID = "examplemod";

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    //Block
    public static final Block BLOCK_MYBLOCK =
            new BlockMyBlock().setRegistryName(MODID, "block_myblock");

    public static final Block BLOCK_FORTUNE =
            new BlockFortune().setRegistryName(MODID, "block_fortune");

    public static final Block BLOCK_RAINBOW =
            new BlockRainbow().setRegistryName(MODID, "block_rainbow");

    public static  final Block BLOCK_SOUND =
            new BlockSound().setRegistryName(MODID, "block_sound");

    public static  final Block BLOCK_SENSOR =
            new BlockSensor().setRegistryName(MODID, "block_sensor");

    public static final Block BLOCK_FOOTPRINTS_SAND =
            new BlockFootprintsSand().setRegistryName(MODID, "block_footprints_sand");
    //Entity
    public static final EntityType<EntityMySnowball> ENTITY_MY_SNOWBALL =
            EntityType.Builder.<EntityMySnowball>of(EntityMySnowball::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .setShouldReceiveVelocityUpdates(true)
                    .build("my_snowball");

    public static final EntityType<EntityExplosiveArrow> ENTITY_EXPLOSIVE_ARROW =
            EntityType.Builder.<EntityExplosiveArrow>of(EntityExplosiveArrow::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .setShouldReceiveVelocityUpdates(true)
                    .build("explosive_arrow");

    public static final EntityType<EntityBull> ENTITY_BULL =
            EntityType.Builder.of(EntityBull::new, MobCategory.CREATURE)
                    .sized(0.9f, 1.4f)
                    .setTrackingRange(32)
                    .setShouldReceiveVelocityUpdates(true)
                    .build("bull");

    public static final EntityType<EntityTobisuke> ENTITY_TOBISUKE =
            EntityType.Builder
                    .of(EntityTobisuke::new, MobCategory.CREATURE)
                    .sized(0.9f, 0.9f)
                    .setTrackingRange(32)
                    .setUpdateInterval(1)
                    .setShouldReceiveVelocityUpdates(true)
                    .build("tobisuke");

    //Item
    public static final Item ITEM_MAGIC_STICK =
            new ItemMagicStick().setRegistryName(MODID, "magic_stick");

    public static final Item ITEM_HI_POTION =
            new ItemHiPotion().setRegistryName(MODID, "hi_potion");

    public static final Item ITEM_MY_SWORD =
            new ItemMySword().setRegistryName(MODID, "my_sword");

    public static final Item BLOCK_COPIER =
            new BlockCopier().setRegistryName(MODID, "block_copier");

    public static final Item ITEM_MY_SNOWBALL =
            new ItemMySnowBall().setRegistryName(MODID, "my_snowball");

    public static final Item ITEM_EXPLOSIVE_ARROW =
            new ItemExplosiveArrow().setRegistryName(MODID, "explosive_arrow");

    public static final Item BULL_SPAWN_EGG =
            new SpawnEggItem(ENTITY_BULL,
                    0x00FF00,
                    0x0000FF,
                    new Item.Properties().tab(CreativeModeTab.TAB_MISC)
            ).setRegistryName(MODID, "bull_spawn_egg");

    public static final Item TOBISUKE_SPAWN_EGG =
            new SpawnEggItem(ENTITY_TOBISUKE,
                    0xFF0000,
                    0x00FF00,
                    new Item.Properties().tab(CreativeModeTab.TAB_MISC)
            ).setRegistryName(MODID, "tobisuke_spawn_egg");

    public static final ResourceKey<Biome> MY_BIOME =
            ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(ExampleMod.MODID, "my_biome"));

    public ExampleMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new BlockBreakEventHandler());
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BiomeProviders.register(new MyBiomeProvider(new ResourceLocation(MODID, "biome_provider"), 50));
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        EntityRenderers.register(ENTITY_MY_SNOWBALL, ThrownItemRenderer::new);
        EntityRenderers.register(ENTITY_EXPLOSIVE_ARROW, RenderExplosiveArrow::new);
        EntityRenderers.register(ENTITY_BULL, RenderBull::new);
        EntityRenderers.register(ENTITY_TOBISUKE, RenderTobisuke::new);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        private static final RegisterBlockData[] registerBlocks = {
                // ここにBlockを書いてね！
                new RegisterBlockData(BLOCK_MYBLOCK),
                new RegisterBlockData(BLOCK_FORTUNE),
                new RegisterBlockData(BLOCK_RAINBOW),
                new RegisterBlockData(BLOCK_SOUND),
                new RegisterBlockData(BLOCK_SENSOR),
                new RegisterBlockData(BLOCK_FOOTPRINTS_SAND),
        };

        private static final Item[] registerItems = {
                // ここにItemを書いてね！
                ITEM_MAGIC_STICK,
                ITEM_HI_POTION,
                ITEM_MY_SWORD,
                BLOCK_COPIER,
                ITEM_MY_SNOWBALL,
                ITEM_EXPLOSIVE_ARROW,
                BULL_SPAWN_EGG,
                TOBISUKE_SPAWN_EGG,
        };

        @SubscribeEvent
        public static void onBiomeRegistry(final RegistryEvent.Register<Biome> event) {

        }

        @SubscribeEvent
        public static void registerBiomes(RegistryEvent.Register<Biome> event){
            IForgeRegistry<Biome> registry = event.getRegistry();
            registry.register(BiomeMyBiome.makeMyBiome().setRegistryName(MY_BIOME.location()));
        }

        @SubscribeEvent
        public static void onAttributeCreation(final EntityAttributeCreationEvent event) {
            event.put(ENTITY_BULL, EntityBull.registerAttributes().build());
            event.put(ENTITY_TOBISUKE, EntityTobisuke.registerAttributes().build());
        }

        @SubscribeEvent
        public static void onEntitiesRegistry(final RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().register(ENTITY_MY_SNOWBALL
                    .setRegistryName(MODID, "my_snowball"));
            event.getRegistry().register(ENTITY_EXPLOSIVE_ARROW
                    .setRegistryName(MODID, "explosive_arrow"));
            event.getRegistry().register(ENTITY_BULL
                    .setRegistryName(MODID, "bull"));
            event.getRegistry().register(ENTITY_TOBISUKE
                    .setRegistryName(MODID, "tobisuke"));

            ForgeHooksClient.registerLayerDefinition(RenderTobisuke.modelLayerLocation, ModelOriginalTobisuke::createLayer);
        }

        // ======================================================================================================
        // ここから下はいじらないよ！

        private static void setupBiome(Biome biome, int weight, BiomeManager.BiomeType biomeType, BiomeDictionary.Type... types) {
            ResourceKey<Biome> key = ResourceKey.create(ForgeRegistries.Keys.BIOMES, ForgeRegistries.BIOMES.getKey(biome));

            BiomeDictionary.addTypes(key, types);
            BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(key, weight));
        }

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            LOGGER.info("HELLO from Register Block");
            for (RegisterBlockData data : registerBlocks) {
                event.getRegistry().register(data.block);
            }
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            for (RegisterBlockData data : registerBlocks) {
                event.getRegistry().register(new BlockItem(data.block, new Item.Properties().tab(data.creativeModeTab)).setRegistryName(data.block.getRegistryName()));
            }

            for (Item item : registerItems) {
                event.getRegistry().register(item);
            }
        }

        static class RegisterBlockData {
            Block block;
            CreativeModeTab creativeModeTab;

            public RegisterBlockData(Block block) {
                this.block = block;
                creativeModeTab = CreativeModeTab.TAB_BUILDING_BLOCKS;
            }

            public RegisterBlockData(Block block, CreativeModeTab creativeModeTab) {
                this.block = block;
                this.creativeModeTab = creativeModeTab;
            }
        }
    }
}
