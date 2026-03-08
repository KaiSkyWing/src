package com.example.examplemod.mc_11_footprints_sand;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;

import java.util.Random;

public class BlockFootprintsSand extends Block {

    private static final IntegerProperty COLOR = IntegerProperty.create("color", 0, 4);

    public BlockFootprintsSand(){
        super (Block.Properties.of(Material.STONE).strength(10f));
        this.registerDefaultState(this.stateDefinition.any().setValue(COLOR, 0));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(COLOR);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState blockState, Entity entityIn){
        super.stepOn(level, pos, blockState, entityIn);
        if(!(entityIn instanceof Player)){
            return;
        }

        BlockState blockColorState = level.getBlockState(pos);

        if(blockColorState.getValue(COLOR) != 4){
            level.setBlockAndUpdate(pos, blockColorState.setValue(COLOR, 4));
            level.scheduleTick(pos, this, 5);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random rand){
        super.tick(state, level, pos, rand);

        int blockColorState = state.getValue(COLOR);
        blockColorState --;

        level.setBlockAndUpdate(pos, state.setValue(COLOR, blockColorState));
        if(blockColorState != 0){
            level.scheduleTick(pos, this, 5);
        }
    }
}
