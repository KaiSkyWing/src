package com.example.examplemod.mc_13_explosive_arrow;

import com.example.examplemod.ExampleMod;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class RenderExplosiveArrow extends ArrowRenderer<EntityExplosiveArrow> {

    private static final ResourceLocation TEXTURE_EXPLOSIVE_ARROW =
            new ResourceLocation(ExampleMod.MODID, "textures/entity/explosive_arrow.png");

    public  RenderExplosiveArrow(EntityRendererProvider.Context context){
        super(context);
    }

    @Override
    public  ResourceLocation getTextureLocation(EntityExplosiveArrow entity){
        return  TEXTURE_EXPLOSIVE_ARROW;
    }
}
