package com.example.examplemod.mc_15_tobisuke;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelTobisuke <T extends EntityTobisuke> extends AgeableListModel<T> {
    private final ModelPart head;
    private final ModelPart body;

    public ModelTobisuke(ModelPart modelPart){
        this.head = modelPart.getChild("head");
        this.body = modelPart.getChild("body");
    }

    public static LayerDefinition createLayer(){
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild(
                "head", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-3.0f, -1.0f, -7.0f, 6, 6, 6)
                        .texOffs(24, 0)
                        .addBox(-1.5f, 3.0f, -8.0f, 3, 1, 1),
                PartPose.offsetAndRotation(0f, 14f, 0f, 0f, 0f, 0f)
        );

        partDefinition.addOrReplaceChild(
                "body", CubeListBuilder.create()
                        .texOffs(0, 18)
                        .addBox(-4.5f, -6.0f, -7.5f, 9, 6, 12)
                        .texOffs(30, 0)
                        .addBox(-5.5f, -5.0f, -7.0f, 1, 4, 8)
                        .texOffs(30, 0)
                        .addBox(4.5f, -5.0f, -7.0f, 1, 4, 8),
                PartPose.offsetAndRotation(0f, 24f, 0f, 0f, 0f, 0f)
        );

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    protected Iterable<ModelPart> headParts(){
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts(){
        return ImmutableList.of(this.body);
    }

    @Override
    public void setupAnim(
            T entityIn,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ){
        head.yRot = netHeadYaw / (180.0f / (float) Math.PI) * 0.25f;
        body.yRot = netHeadYaw / (180.0f / (float) Math.PI) * 0.25f;
    }
}
