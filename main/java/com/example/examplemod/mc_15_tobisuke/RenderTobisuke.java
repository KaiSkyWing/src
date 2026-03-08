package com.example.examplemod.mc_15_tobisuke;

import com.example.examplemod.ExampleMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)

public class RenderTobisuke extends MobRenderer<EntityTobisuke, ModelTobisuke<EntityTobisuke>> {

    private static final ResourceLocation tobisukeTexture
            = new ResourceLocation(ExampleMod.MODID, "textures/entity/original_tobisuke.png");
    public static final ModelLayerLocation modelLayerLocation =
            new ModelLayerLocation(new ResourceLocation(ExampleMod.MODID, "tobisuke"), "tobisuke");

    public RenderTobisuke(EntityRendererProvider.Context context){
        super(context, new ModelTobisuke<>(context.bakeLayer(modelLayerLocation)), 0.5f);
    }

    @Override
    public  ResourceLocation getTextureLocation(EntityTobisuke entity){
        return tobisukeTexture;
    }
}
