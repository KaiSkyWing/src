package com.example.examplemod.BlockCopier;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;


public class BlockCopier extends Item {
    public BlockCopier(){
        super (new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext){
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        ItemStack stack = pContext.getItemInHand();

        if(level.isClientSide() || player == null){
            return InteractionResult.SUCCESS;
        }

        boolean isShiftClick = player.isShiftKeyDown();

        if(!isShiftClick) {
            BlockPos pos = pContext.getClickedPos();
            Block block = level.getBlockState(pos).getBlock();
            ResourceLocation blockName = block.getRegistryName();

            if (blockName == null) {
                player.displayClientMessage(new TextComponent("無効なブロック"), true);
                return InteractionResult.SUCCESS;
            }

            CompoundTag blockTag = new CompoundTag();
            blockTag.putString("block", blockName.toString());
            stack.getOrCreateTag().put("clicked_block", blockTag);

            player.displayClientMessage(new TextComponent("ブロックを保存済み: " + block.getRegistryName()), true);

            return InteractionResult.SUCCESS;
        }

        CompoundTag tag = stack.getTag();

        if(tag == null || !tag.contains("clicked_block")){
            return InteractionResult.SUCCESS;
        }

        CompoundTag blockTag = tag.getCompound("clicked_block");
        String blockName = blockTag.getString("block");
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockName));

        if(block == null){
            player.displayClientMessage(new TextComponent("無効なブロック"), true);
            return  InteractionResult.SUCCESS;
        }

        BlockPos playerPos = player.blockPosition();

        if(!level.isEmptyBlock(playerPos)){
            player.displayClientMessage(new TextComponent("ここは配置できません"), true);
            return InteractionResult.SUCCESS;
        }

        level.setBlockAndUpdate(playerPos, block.defaultBlockState());

        return InteractionResult.SUCCESS;
    }
}
