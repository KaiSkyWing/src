package com.example.examplemod.mc_03_magicstick;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemMagicStick extends Item{
    public ItemMagicStick() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker){
        //LivingEntity entity = new Pig(EntityType.PIG, pAttacker.level);
        Level level = pTarget.level;

        BlockPos spawnPos = pTarget.blockPosition();
        //entity.setPos(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());

        LivingEntity entity;
        if (pTarget instanceof Villager){
            entity = new Zombie(level);
        } else {
            entity = new Pig(EntityType.PIG, level);
        }

        entity.setPos(spawnPos.getX(), spawnPos.getY(),spawnPos.getX());
        if(!pTarget.level.isClientSide){
            ServerLevel serverLevel = (ServerLevel) pTarget.level;
            serverLevel.tryAddFreshEntityWithPassengers(entity);
            //serverLevel.removeEntity(pTarget);
            pTarget.setRemoved(Entity.RemovalReason.KILLED);
        }

        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}
