package com.example.examplemod.mc_18_eventhandler;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class LeafEventHandler {
    @SubscribeEvent
    public static void onBreakLeaf(BlockEvent.BreakEvent event){
        if(event.getState().getBlock() == Blocks.GRASS){
            Player player = event.getPlayer();
            Level level = player.level;

            if(!level.isClientSide){
                ItemStack stack = new ItemStack(Items.DIAMOND_SWORD);
                BlockPos pos = event.getPos();
                ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
            }
        }
    }
}
