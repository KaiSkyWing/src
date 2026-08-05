package com.example.examplemod.AiMoc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class AiMocItem extends Item {
    public AiMocItem(){
        super(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        BlockPos pos = player.blockPosition().relative(player.getDirection());
        level.setBlockAndUpdate(pos, Blocks.TNT.defaultBlockState());
        level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 4.0F, Explosion.BlockInteraction.BREAK);
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
