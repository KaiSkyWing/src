package com.example.examplemod.OriginalProject;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerInputOnCar {

    //EntityCarにも書いたがこれでいいのか？が正直いまいち分かり切ってない
    //動くには動く
    @SubscribeEvent
    public void onInputKeyPressed(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) return;

        if (mc.player.getVehicle() instanceof EntityCar car) {

            boolean left  = mc.options.keyLeft.isDown();
            boolean right = mc.options.keyRight.isDown();
            boolean up    = mc.options.keyUp.isDown();
            boolean down  = mc.options.keyDown.isDown();

            car.setInput(left, right, up, down);
        }
    }
}
