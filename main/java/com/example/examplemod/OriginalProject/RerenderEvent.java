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
        if (event.phase != TickEvent.Phase.END) return;

        var minecraft = Minecraft.getInstance();

        if (minecraft.player == null) return;

        Item currentItem = minecraft.player.getMainHandItem().getItem();

        if (previousItem != currentItem){
            previousItem = currentItem;

            BlockPos pos = minecraft.player.blockPosition();

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
