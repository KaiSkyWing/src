package com.example.examplemod.OriginalProject;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class BlockCheckpoint extends Block {

    private boolean isInside = false;

    public BlockCheckpoint(){
        super(BlockBehaviour.Properties
                .of(Material.STRUCTURAL_AIR)
                .noCollission()  );
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (!isInside) {
            if (pLevel.isClientSide) { // ← IMPORTANT
                for (int i = 0; i < 20; i++) {
                    pLevel.addParticle(
                            ParticleTypes.TOTEM_OF_UNDYING,
                            pPos.getX() + 0.5,
                            pPos.getY() + 1,
                            pPos.getZ() + 0.5,
                            0, 0, 0
                    );
                }
            }
            System.out.println("interacted");
            isInside = true;
        }

        super.entityInside(pState, pLevel, pPos, pEntity);
    }


}