package com.example.examplemod.mc_02_fortuneblock;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.Random;

public class BlockFortune extends Block {
    public BlockFortune(){
        super(Block.Properties.of(Material.STONE).strength(30f));
    }

    @Override
    public void attack(BlockState state, Level level, BlockPos pos, Player player){

        Random random = new Random();
        int randomNumber = random.nextInt(5);

        String message ="";

        if (randomNumber == 0){
            message = "大当たり";
        } else if (randomNumber == 1){
            message = "あたり";
        } else if (randomNumber == 2){
            message = "~~~~";
        } else if (randomNumber == 3){
            message = "ハズレ";
        } else if (randomNumber == 4){
            message = "悲しい";
        }

        ItemStack heldItem = player.getMainHandItem();
        if (heldItem.getItem() == Items.GOLDEN_APPLE) {
            message = "大当たり";
        }

        if(!level.isClientSide){
            player.sendMessage(new TextComponent(message), player.getUUID());
        }
    }
}
