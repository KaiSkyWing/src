package com.example.examplemod.mc_13_explosive_arrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemExplosiveArrow extends ArrowItem {
    public ItemExplosiveArrow(){
        super(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
    }

    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        return new EntityExplosiveArrow(level, shooter);
    }
}
