package com.linngdu664.bsf.client.model;// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.linngdu664.bsf.entity.executor.ForceExecutor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class ForceExecutorModel<T extends ForceExecutor> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION1 = new ModelLayerLocation(new ResourceLocation("bsf", "textures/models/monster_gravity_executor.png"), "main");
	public static final ModelLayerLocation LAYER_LOCATION2 = new ModelLayerLocation(new ResourceLocation("bsf", "textures/models/monster_repulsion_executor.png"), "main");
	public static final ModelLayerLocation LAYER_LOCATION3 = new ModelLayerLocation(new ResourceLocation("bsf", "textures/models/projectile_gravity_executor.png"), "main");
	public static final ModelLayerLocation LAYER_LOCATION4 = new ModelLayerLocation(new ResourceLocation("bsf", "textures/models/projectile_repulsion_executor.png"), "main");
	private final ModelPart circle1;
	private final ModelPart circle2;
	private final ModelPart bb_main;

	public ForceExecutorModel(ModelPart root) {
		this.circle1 = root.getChild("circle1");
		this.circle2 = root.getChild("circle2");
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition circle1 = partdefinition.addOrReplaceChild("circle1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition bone = circle1.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(18, 4).addBox(0.0F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r2 = bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(21, 16).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r3 = bone.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(23, 5).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone2 = circle1.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r4 = bone2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(23, 3).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r5 = bone2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(5, 17).addBox(0.0F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r6 = bone2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(21, 14).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone3 = circle1.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r7 = bone3.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(16, 15).addBox(0.0F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r8 = bone3.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(10, 21).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r9 = bone3.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 23).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone4 = circle1.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r10 = bone4.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r11 = bone4.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(21, 1).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r12 = bone4.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(18, 21).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle2 = partdefinition.addOrReplaceChild("circle2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition bone5 = circle2.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r13 = bone5.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(12, 10).addBox(0.0F, -1.0F, -4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.727F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r14 = bone5.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 21).addBox(-4.0159F, -1.0F, 0.0064F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.85F, 0.0F, -6.702F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r15 = bone5.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(9, 15).addBox(-4.0F, -1.0F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.727F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone6 = circle2.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r16 = bone6.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(12, 4).addBox(0.0F, -1.0F, -4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.727F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r17 = bone6.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(13, 19).addBox(-4.0159F, -1.0F, 0.0064F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.85F, 0.0F, -6.702F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r18 = bone6.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(0, 14).addBox(-4.0F, -1.0F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.727F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone7 = circle2.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r19 = bone7.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(6, 9).addBox(0.0F, -1.0F, -4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.727F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r20 = bone7.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(18, 11).addBox(-4.0159F, -1.0F, 0.0064F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.85F, 0.0F, -6.702F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r21 = bone7.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(12, 2).addBox(-4.0F, -1.0F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.727F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone8 = circle2.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r22 = bone8.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -1.0F, -4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.727F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r23 = bone8.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(18, 9).addBox(-4.0159F, -1.0F, 0.0064F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.85F, 0.0F, -6.702F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r24 = bone8.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(12, 0).addBox(-4.0F, -1.0F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.727F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		circle1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		circle2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public ModelPart getCircle1() {
		return circle1;
	}

	public ModelPart getCircle2() {
		return circle2;
	}

	public ModelPart getBb_main() {
		return bb_main;
	}
}