package com.example.examplemod.OriginalProject;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RerenderEvent {

    private static Item previousItem;

    @SubscribeEvent
    public static void ForRerenderCheckPoint(TickEvent.ClientTickEvent event) {
        //これなんで必要かわからんというか意味を理解してない
        if (event.phase != TickEvent.Phase.END) return;

        var minecraft = Minecraft.getInstance();

        if (minecraft.player == null) return;

        Item currentItem = minecraft.player.getMainHandItem().getItem();

        //BlockCheckpoint の getRenderShape を反映させるために、アイテムを切り替えるたびに近くのブロックを更新
        if (previousItem != currentItem){
            previousItem = currentItem;

            BlockPos pos = minecraft.player.blockPosition();

            //levelRenderer の setBlocksDirty に "Re-renders all blocks in the specified range." って書いてあった
            //前後左右16マス分更新
            minecraft.levelRenderer.setBlocksDirty(
                    pos.getX() - 16,
                    pos.getY() - 16,
                    pos.getZ() - 16,
                    pos.getX() + 16,
                    pos.getY() + 16,
                    pos.getZ() + 16
            );
        }
    }
}
