package com.example.examplemod.mc_18_eventhandler;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerHurtEventHandler {
    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event){
        if(event.getEntityLiving() instanceof  Player){
            Player player = (Player) event.getEntityLiving();

            if(!player.level.isClientSide){
                MobEffect effect = MobEffects.HEAL;
                int seconds = 5;
                int level = 1;
                player.addEffect(new MobEffectInstance(effect, seconds, level));
            }
        }
    }
}
