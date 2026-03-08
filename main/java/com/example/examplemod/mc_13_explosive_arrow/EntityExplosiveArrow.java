package com.example.examplemod.mc_13_explosive_arrow;

import com.example.examplemod.ExampleMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class EntityExplosiveArrow extends AbstractArrow {
    public EntityExplosiveArrow(EntityType<? extends  EntityExplosiveArrow> type, Level level){
        super(type, level);
    }

    public EntityExplosiveArrow(Level level, LivingEntity entity){
        super(ExampleMod.ENTITY_EXPLOSIVE_ARROW, entity, level);
    }

    @Override
    protected ItemStack getPickupItem(){
        return new ItemStack(Items.ARROW);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity livingEntity){
        if(!level.isClientSide()){
            float explosionRaduis = 2;
            level.explode(this, getX(), getY(), getZ(), explosionRaduis, Explosion.BlockInteraction.DESTROY);
        }
        this.remove(RemovalReason.KILLED);
    }

    @Override
    protected void onHitBlock(BlockHitResult result){
        if(!level.isClientSide()){
            float explosionRaduis = 2;
            level.explode(this, getX(), getY(), getZ(), explosionRaduis, Explosion.BlockInteraction.DESTROY);
        }
        this.remove(RemovalReason.KILLED);
    }
}
