package com.example.examplemod.mc_14_bull_fighting;

import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)

public class RenderBull extends MobRenderer<EntityBull, CowModel<EntityBull>> {

    private static final ResourceLocation COW_LOCATION =
            new ResourceLocation("textures/entity/cow/cow.png");

    public RenderBull(EntityRendererProvider.Context context){
        super (context, new CowModel(context.bakeLayer(ModelLayers.COW)), 0.7f);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBull pEntity){
        return COW_LOCATION;
    }
}
