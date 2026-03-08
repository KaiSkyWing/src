package com.example.examplemod.mc_10_snowball_fight;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.level.Level;

import java.util.Random;

public class ItemMySnowBall extends SnowballItem {

    public ItemMySnowBall(){
        super(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn){
        ItemStack itemStack = playerIn.getItemInHand(handIn);

        if(!playerIn.isCreative()){
            itemStack.setCount(itemStack.getCount()-1);
        }

        Random random = new Random();

        level.playSound(
                null,
                playerIn.getX(),
                playerIn.getY(),
                playerIn.getX(),
                SoundEvents.SNOWBALL_THROW,
                SoundSource.NEUTRAL,
                0.5f,
                0.4f / (random.nextFloat() * 0.4f + 0.8f)
        );

        if(!level.isClientSide()){
            EntityMySnowball entity = new EntityMySnowball(level, playerIn);
            entity.shootFromRotation(playerIn, playerIn.xRotO, playerIn.yRotO, 0.0f, 1.5f, 1.0f);
            level.addFreshEntity(entity);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
