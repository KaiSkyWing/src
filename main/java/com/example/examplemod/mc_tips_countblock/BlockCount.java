package com.example.examplemod.mc_tips_countblock;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;

public class BlockCount extends Block{

    //int num = 0;
    private static final IntegerProperty COUNT = IntegerProperty.create("count", 0, 255);

    public BlockCount() {
        super (Block.Properties.of(Material.STONE).strength(10f));
        this.registerDefaultState(this.stateDefinition.any().setValue(COUNT, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(COUNT);
    }

    @Override
    public void attack(BlockState state, Level level, BlockPos pos, Player player){
        //num = num + 1;
        //player.sendMessage(new TextComponent(String.valueOf(num)), player.getUUID());

        int count = state.getValue(COUNT);
        count++;

        if(count >= 256){
            count = 0;
        }

        if(level.isClientSide()){
            player.sendMessage(new TextComponent(String.valueOf(count)), player.getUUID());
        }

        level.setBlockAndUpdate(pos, state.setValue(COUNT, count));
    }
}
