package com.example.examplemod.OriginalProject;

import com.example.examplemod.ExampleMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)

public class RenderCar extends EntityRenderer<EntityCar> {

    private final ModelCar<EntityCar> model;

    private static final ResourceLocation CAR_LOCATION =
            new ResourceLocation(ExampleMod.MODID, "textures/entity/car_texture.png");

    public static final ModelLayerLocation modelLayerLocation =
            new ModelLayerLocation(new ResourceLocation(ExampleMod.MODID, "car"), "car");

    public RenderCar(EntityRendererProvider.Context context){
        super(context);
        this.model = new ModelCar<>(context.bakeLayer(modelLayerLocation));
    }


    @Override
    public void render(EntityCar entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        poseStack.pushPose();

        //なんか初期で逆さまに、位置がずれて表示されるから修正
        poseStack.scale(-3.0F, -3.0F, 3.0F);
        poseStack.translate(0.0D, -1.5D, 0.0D);

        //BoatRendererは180.0F - entityYawだけど反転している分、ここは+に
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F + entityYaw));

        VertexConsumer vc =
                buffer.getBuffer(RenderType.entityCutout(getTextureLocation(entity)));

        model.renderToBuffer(poseStack, vc, packedLight,
                OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);

        poseStack.popPose();

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCar entity){
        return CAR_LOCATION;
    }
}
