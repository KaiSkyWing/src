package com.example.examplemod.mc_16_buildingblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class BlockBuilding extends Block {
    /*
    int[][] blockList = {
            {1, 2, 3},
            {1, 2, 3},
            {1, 2, 3}
    };
    */

    int[][][] tofuHouse = {
            {
                    {2, 1, 1, 2, 2},
                    {2, 1, 1, 1, 2},
                    {2, 1, 1, 1, 2},
                    {2, 1, 1, 1, 2},
                    {2, 2, 2, 2, 2},
            },
            {
                    {2, 1, 1, 2, 2},
                    {2, 1, 1, 1, 2},
                    {2, 1, 1, 1, 2},
                    {2, 1, 1, 1, 2},
                    {2, 2, 2, 2, 2},
            },
            {
                    {2, 2, 2, 2, 2},
                    {2, 1, 1, 1, 2},
                    {2, 1, 1, 1, 2},
                    {2, 1, 1, 1, 2},
                    {2, 2, 2, 2, 2},
            },
            {
                    {2, 2, 2, 2, 2},
                    {2, 2, 2, 2, 2},
                    {2, 2, 2, 2, 2},
                    {2, 2, 2, 2, 2},
                    {2, 2, 2, 2, 2},
            }
    };



    public BlockBuilding(){
        super(BlockBehaviour.Properties.of(Material.DIRT).strength(30f));
    }

    @Override
    public void attack(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer){
        super.attack(pState, pLevel, pPos, pPlayer);
        //pLevel.setBlockAndUpdate(pPos, Blocks.DIAMOND_BLOCK.defaultBlockState());

        for (int y = 0; y < tofuHouse.length; y++) {
            for (int x = 0; x < tofuHouse[y].length; x++) {
                for (int z = 0; z < tofuHouse[y][x].length; z++) {
                    switch (tofuHouse[y][x][z]) {
                        case 1 -> pLevel.setBlockAndUpdate(pPos.offset(x, y, z), Blocks.DIAMOND_BLOCK.defaultBlockState());
                        case 2 -> pLevel.setBlockAndUpdate(pPos.offset(x, y, z), Blocks.GOLD_BLOCK.defaultBlockState());
                        case 3 -> pLevel.setBlockAndUpdate(pPos.offset(x, y, z), Blocks.IRON_BLOCK.defaultBlockState());
                    }
                }
            }
        }
    }
}
