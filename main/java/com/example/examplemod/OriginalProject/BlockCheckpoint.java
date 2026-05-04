package com.example.examplemod.OriginalProject;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;

public class BlockCheckpoint extends Block {

    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

    public BlockCheckpoint() {
        super(BlockBehaviour.Properties
                .of(Material.STRUCTURAL_AIR)
                .noCollission());

        this.registerDefaultState(this.getStateDefinition().any().setValue(ACTIVATED, false));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!state.getValue(ACTIVATED)) {

            if (level.isClientSide) {
                for (int i = 0; i < 20; i++) {
                    level.addParticle(
                            ParticleTypes.TOTEM_OF_UNDYING,
                            pos.getX() + 0.5,
                            pos.getY() + 1.0,
                            pos.getZ() + 0.5,
                            0, 0, 0
                    );
                }
            } else {
                level.setBlock(pos, state.setValue(ACTIVATED, true), 3);
                System.out.println("interacted");
            }
        }

        super.entityInside(state, level, pos, entity);
    }
}