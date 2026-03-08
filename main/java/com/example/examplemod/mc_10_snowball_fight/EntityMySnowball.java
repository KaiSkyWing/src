package com.example.examplemod.mc_10_snowball_fight;


import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class EntityMySnowball extends Snowball {
    private  static  final float DAMAGE = 2.0f;

    public EntityMySnowball(EntityType<? extends Snowball> entityTypeIn, Level level){
        super (entityTypeIn, level);
    }

    public EntityMySnowball(Level level, LivingEntity throwerIn){
        super (level, throwerIn);
    }

    @Override
    protected void onHit(HitResult result){
        super.onHit(result);

        if(result.getType() == HitResult.Type.BLOCK){
            BlockHitResult blockHitResult = (BlockHitResult) result;
            switch (blockHitResult.getDirection()){
                case EAST, WEST, NORTH, SOUTH -> {
                    Block block = level.getBlockState(blockHitResult.getBlockPos()).getBlock();
                    if(block == Blocks.SNOW_BLOCK){
                        level.setBlockAndUpdate(blockHitResult.getBlockPos(), Blocks.AIR.defaultBlockState());
                    }
                }
            }
        } else if (result.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityRayTraceResult = (EntityHitResult) result;
            entityRayTraceResult.getEntity().hurt(DamageSource.thrown(this, getOwner()), DAMAGE);

        }
    }
}
