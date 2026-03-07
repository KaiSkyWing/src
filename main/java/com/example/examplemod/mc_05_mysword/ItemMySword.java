package com.example.examplemod.mc_05_mysword;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;

public class ItemMySword extends SwordItem {
    public ItemMySword(){
        super(Tiers.IRON,
                3,
                -2.4F,
                (new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        if(attacker instanceof Player){
            target.addEffect((new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1200, 0)));
            target.setRemainingFireTicks(1200);

            BlockPos pos = new BlockPos(target.getX()+1,target.getY()+1,target.getZ());
            target.level.setBlockAndUpdate(pos, Blocks.GLASS.defaultBlockState());

            pos = new BlockPos(target.getX()-1,target.getY()+1,target.getZ());
            target.level.setBlockAndUpdate(pos, Blocks.GLASS.defaultBlockState());

            pos = new BlockPos(target.getX(),target.getY()+1,target.getZ()+1);
            target.level.setBlockAndUpdate(pos, Blocks.GLASS.defaultBlockState());

            pos = new BlockPos(target.getX(),target.getY()+1,target.getZ()-1);
            target.level.setBlockAndUpdate(pos, Blocks.GLASS.defaultBlockState());
        }
        return  super.hurtEnemy(stack, target, attacker);
    }
}
