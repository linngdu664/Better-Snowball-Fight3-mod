package com.linngdu664.bsf.client.model;// Made with Blockbench 4.4.1
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BSFSnowGolemModel<T extends BSFSnowGolemEntity> extends EntityModel<T>  {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Main.MODID, "bsf_snow_golem"), "main");
    private final ModelPart middle;
    private final ModelPart up;
    private final ModelPart down;
    private final ModelPart left_arm_r1;
    private final ModelPart right_arm_r1;

    public BSFSnowGolemModel(ModelPart root) {
        this.middle = root.getChild("middle");
        this.left_arm_r1 = root.getChild("middle").getChild("left_arm_r1");
        this.right_arm_r1 = root.getChild("middle").getChild("right_arm_r1");
        this.up = root.getChild("up");
        this.down = root.getChild("down");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition middle = partdefinition.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(0, 16).addBox(-5.0F, -22.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition left_arm_r1 = middle.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(32, 0).addBox(1.0F, 0.0F, 0.0F, 12.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(4.0F, -21.0F, -1.0F, 0.0F, 0.0F, 1.0472F));

        PartDefinition right_arm_r1 = middle.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(32, 0).addBox(-13.0F, 0.0F, 0.0F, 12.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(-4.0F, -21.0F, -1.0F, 0.0F, 0.0F, -1.0472F));

        PartDefinition up = partdefinition.addOrReplaceChild("up", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.5F))
                .texOffs(64, 96).addBox(-8.0F, -9.5F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(-3.5F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition smoke_r1 = up.addOrReplaceChild("smoke_r1", CubeListBuilder.create().texOffs(33, 9).addBox(1.75F, -1.0F, -4.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-2.0F, 2.0F, -2.0F, -0.1309F, -0.2182F, 0.0F));

        PartDefinition hat = up.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 5).addBox(-5.0F, -3.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(32, 16).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(38, 30).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition headset = up.addOrReplaceChild("headset", CubeListBuilder.create().texOffs(38, 43).addBox(3.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(38, 43).addBox(-4.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition down = partdefinition.addOrReplaceChild("down", CubeListBuilder.create().texOffs(0, 36).addBox(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition left_caterpillar = down.addOrReplaceChild("left_caterpillar", CubeListBuilder.create().texOffs(0, 61).addBox(-7.0F, -4.0F, -8.0F, 5.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 60).addBox(-7.0F, -1.0F, -8.0F, 5.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 62).mirror().addBox(-6.0F, -3.0F, -8.0F, 3.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 36).addBox(-7.0F, -3.0F, -9.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 36).addBox(-7.0F, -3.0F, 8.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(9.1F, 0.1F, 0.0F));

        PartDefinition right_caterpillar = down.addOrReplaceChild("right_caterpillar", CubeListBuilder.create().texOffs(0, 61).addBox(-7.0F, -4.0F, -8.0F, 5.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 60).addBox(-7.0F, -1.0F, -8.0F, 5.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 62).mirror().addBox(-6.0F, -3.0F, -8.0F, 3.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 36).addBox(-7.0F, -3.0F, -9.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 36).addBox(-7.0F, -3.0F, 8.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1F, 0.1F, 0.0F));
        PartDefinition crown = up.addOrReplaceChild("crown", CubeListBuilder.create().texOffs(108, 26).addBox(-2.5F, -7.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(-0.7F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition nose = up.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(120, 0).addBox(-1.0F, -0.8F, -5.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
                .texOffs(118, 0).addBox(-1.0F, -0.8F, -6.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.5F))
                .texOffs(118, 0).addBox(-1.0F, -0.8F, -7.7F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.7F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(BSFSnowGolemEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.up.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.up.xRot = headPitch * Mth.DEG_TO_RAD;
        this.middle.yRot = netHeadYaw * Mth.DEG_TO_RAD * 0.25F;
        if (entity.getWeapon() != ItemStack.EMPTY) {
            this.left_arm_r1.xRot = Mth.DEG_TO_RAD * 103;
            this.left_arm_r1.yRot = Mth.DEG_TO_RAD * 67;
            this.left_arm_r1.zRot = Mth.DEG_TO_RAD * 162;
            this.right_arm_r1.xRot = Mth.DEG_TO_RAD * 124;
            this.right_arm_r1.yRot = Mth.DEG_TO_RAD * -81;
            this.right_arm_r1.zRot = Mth.DEG_TO_RAD * 176;
        } else {
            this.left_arm_r1.xRot = 0;
            this.left_arm_r1.yRot = 0;
            this.left_arm_r1.zRot = Mth.DEG_TO_RAD * 60;
            this.right_arm_r1.xRot = 0;
            this.right_arm_r1.yRot = 0;
            this.right_arm_r1.zRot = Mth.DEG_TO_RAD * -60;
        }
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        middle.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        up.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        down.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}