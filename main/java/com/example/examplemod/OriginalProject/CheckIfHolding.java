package com.example.examplemod.OriginalProject;

import com.example.examplemod.ExampleMod;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CheckIfHolding {

    //アイテムを持っているときだけブロックを表示させようとした
    //現時点では何も対応させていない
    @SubscribeEvent
    public void checkHolding(RenderLevelLastEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) return;

        ItemStack heldItem = mc.player.getMainHandItem();
        if (heldItem.getItem() == ExampleMod.BLOCK_CHECKPOINT.asItem()) {
            //make it visible(render it)
        }
    }
}
