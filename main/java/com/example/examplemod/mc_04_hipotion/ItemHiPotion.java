package com.example.examplemod.mc_04_hipotion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemHiPotion extends Item {
    public ItemHiPotion() {
        super(new Item.Properties().food(new FoodProperties.Builder()
                .nutrition(1)
                .saturationMod(0.5f)
                .alwaysEat()
                .build()
        ).tab(CreativeModeTab.TAB_FOOD));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity player){
        MobEffect effect = MobEffects.MOVEMENT_SPEED;
        int duration = 200;
        int potionLevel = 0;
        int size = stack.getCount();

        if(size >= 20){
            effect = MobEffects.POISON;
            duration = 600;
            potionLevel = 2;
        } else if(size >= 15){
            effect = MobEffects.REGENERATION;
            duration = 1200;
            potionLevel = 1;
        } else if(size >= 10){
            duration = 600;
            potionLevel = 1;
        }

        player.addEffect(new MobEffectInstance(effect, duration, potionLevel));

        return super.finishUsingItem(stack, level, player);
    }

    @Override
    public boolean isFoil(ItemStack stack){
        return true;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack){
        return UseAnim.DRINK;
    }
}