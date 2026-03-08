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
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    //Entity
    public static final  EntityType<EntityMySnowball> ENTITY_MY_SNOWBALL =
            EntityType.Builder.<EntityMySnowball>of(EntityMySnowball::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .setShouldReceiveVelocityUpdates(true)
                    .build("my_snowball");

    public ExampleMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new BlockBreakEventHandler());
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        EntityRenderers.register(ENTITY_MY_SNOWBALL, ThrownItemRenderer::new);
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
        };

        private static final Item[] registerItems = {
                // ここにItemを書いてね！
                ITEM_MAGIC_STICK,
                ITEM_HI_POTION,
                ITEM_MY_SWORD,
                BLOCK_COPIER,
                ITEM_MY_SNOWBALL,
        };

        @SubscribeEvent
        public static void onBiomeRegistry(final RegistryEvent.Register<Biome> event) {

        }

        @SubscribeEvent
        public static void onAttributeCreation(final EntityAttributeCreationEvent event) {

        }

        @SubscribeEvent
        public static void onEntitiesRegistry(final RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().register(ENTITY_MY_SNOWBALL.setRegistryName(MODID, "my_snowball"));
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
