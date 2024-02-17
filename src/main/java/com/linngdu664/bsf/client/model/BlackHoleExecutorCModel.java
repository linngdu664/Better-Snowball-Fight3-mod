package com.linngdu664.bsf.client.model;
// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
import com.linngdu664.bsf.entity.snowball.special.BlackHoleSnowballEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


public class BlackHoleExecutorCModel<T extends BlackHoleSnowballEntity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("bsf:textures/models/black_hole_executor_c.png"), "main");
	private final ModelPart body;
	private final ModelPart plate;

	public BlackHoleExecutorCModel(ModelPart root) {
		this.body = root.getChild("body");
		this.plate = root.getChild("plate");
	}

	public ModelPart getBody() {
		return body;
	}

	public ModelPart getPlate() {
		return plate;
	}

	@SuppressWarnings("unused")
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition ball1 = body.addOrReplaceChild("ball1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition r90d9 = ball1.addOrReplaceChild("r90d9", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.3963F, 0.0F));

		PartDefinition circle17 = r90d9.addOrReplaceChild("circle17", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone65 = circle17.addOrReplaceChild("bone65", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r1 = bone65.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(30, 42).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r2 = bone65.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(6, 56).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r3 = bone65.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(48, 66).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone66 = circle17.addOrReplaceChild("bone66", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r4 = bone66.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(66, 47).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r5 = bone66.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(42, 29).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r6 = bone66.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 56).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone67 = circle17.addOrReplaceChild("bone67", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r7 = bone67.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(42, 28).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r8 = bone67.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(54, 55).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r9 = bone67.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(66, 46).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone68 = circle17.addOrReplaceChild("bone68", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r10 = bone68.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(42, 27).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r11 = bone68.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(48, 55).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r12 = bone68.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(66, 45).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle18 = r90d9.addOrReplaceChild("circle18", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone69 = circle18.addOrReplaceChild("bone69", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r13 = bone69.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(42, 26).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r14 = bone69.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(42, 55).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r15 = bone69.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(66, 44).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone70 = circle18.addOrReplaceChild("bone70", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r16 = bone70.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(66, 43).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r17 = bone70.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(24, 42).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r18 = bone70.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(36, 55).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone71 = circle18.addOrReplaceChild("bone71", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r19 = bone71.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(18, 42).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r20 = bone71.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(30, 55).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r21 = bone71.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(66, 42).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone72 = circle18.addOrReplaceChild("bone72", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r22 = bone72.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(12, 42).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r23 = bone72.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(24, 55).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r24 = bone72.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(42, 66).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d8 = ball1.addOrReplaceChild("r90d8", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.2217F, 0.0F));

		PartDefinition circle15 = r90d8.addOrReplaceChild("circle15", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone57 = circle15.addOrReplaceChild("bone57", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r25 = bone57.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(42, 36).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r26 = bone57.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(54, 56).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r27 = bone57.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(66, 54).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone58 = circle15.addOrReplaceChild("bone58", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r28 = bone58.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(54, 66).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r29 = bone58.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(36, 42).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r30 = bone58.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(48, 56).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone59 = circle15.addOrReplaceChild("bone59", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r31 = bone59.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(42, 35).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r32 = bone59.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(42, 56).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r33 = bone59.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(66, 53).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone60 = circle15.addOrReplaceChild("bone60", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r34 = bone60.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(42, 34).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r35 = bone60.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(36, 56).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r36 = bone60.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(66, 52).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle16 = r90d8.addOrReplaceChild("circle16", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone61 = circle16.addOrReplaceChild("bone61", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r37 = bone61.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(42, 33).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r38 = bone61.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(30, 56).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r39 = bone61.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(66, 51).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone62 = circle16.addOrReplaceChild("bone62", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r40 = bone62.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(66, 50).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r41 = bone62.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(42, 32).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r42 = bone62.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(24, 56).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone63 = circle16.addOrReplaceChild("bone63", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r43 = bone63.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(42, 31).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r44 = bone63.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(18, 56).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r45 = bone63.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(66, 49).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone64 = circle16.addOrReplaceChild("bone64", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r46 = bone64.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(42, 30).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r47 = bone64.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(12, 56).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r48 = bone64.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(66, 48).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d7 = ball1.addOrReplaceChild("r90d7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition circle13 = r90d7.addOrReplaceChild("circle13", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone49 = circle13.addOrReplaceChild("bone49", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r49 = bone49.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(43, 0).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r50 = bone49.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(57, 10).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r51 = bone49.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(66, 61).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone50 = circle13.addOrReplaceChild("bone50", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r52 = bone50.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(66, 60).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r53 = bone50.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(0, 43).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r54 = bone50.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(57, 9).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone51 = circle13.addOrReplaceChild("bone51", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r55 = bone51.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(42, 42).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r56 = bone51.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(57, 8).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r57 = bone51.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(60, 66).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone52 = circle13.addOrReplaceChild("bone52", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r58 = bone52.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(42, 41).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r59 = bone52.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(57, 7).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r60 = bone52.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(66, 59).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle14 = r90d7.addOrReplaceChild("circle14", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone53 = circle14.addOrReplaceChild("bone53", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r61 = bone53.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(42, 40).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r62 = bone53.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(57, 6).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r63 = bone53.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(66, 58).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone54 = circle14.addOrReplaceChild("bone54", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r64 = bone54.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(66, 57).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r65 = bone54.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(42, 39).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r66 = bone54.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(6, 57).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone55 = circle14.addOrReplaceChild("bone55", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r67 = bone55.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(42, 38).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r68 = bone55.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(57, 5).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r69 = bone55.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(66, 56).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone56 = circle14.addOrReplaceChild("bone56", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r70 = bone56.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(42, 37).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r71 = bone56.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(0, 57).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r72 = bone56.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(66, 55).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d6 = ball1.addOrReplaceChild("r90d6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.8727F, 0.0F));

		PartDefinition circle11 = r90d6.addOrReplaceChild("circle11", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone41 = circle11.addOrReplaceChild("bone41", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r73 = bone41.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(42, 43).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r74 = bone41.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(57, 17).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r75 = bone41.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(67, 1).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone42 = circle11.addOrReplaceChild("bone42", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r76 = bone42.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(67, 0).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r77 = bone42.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(36, 43).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r78 = bone42.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(57, 16).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone43 = circle11.addOrReplaceChild("bone43", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r79 = bone43.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(30, 43).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r80 = bone43.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(57, 15).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r81 = bone43.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(0, 67).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone44 = circle11.addOrReplaceChild("bone44", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r82 = bone44.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(24, 43).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r83 = bone44.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(57, 14).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r84 = bone44.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(66, 66).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle12 = r90d6.addOrReplaceChild("circle12", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone45 = circle12.addOrReplaceChild("bone45", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r85 = bone45.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(18, 43).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r86 = bone45.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(57, 13).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r87 = bone45.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(66, 65).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone46 = circle12.addOrReplaceChild("bone46", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r88 = bone46.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(66, 64).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r89 = bone46.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(12, 43).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r90 = bone46.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(57, 12).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone47 = circle12.addOrReplaceChild("bone47", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r91 = bone47.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(6, 43).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r92 = bone47.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(12, 57).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r93 = bone47.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(66, 63).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone48 = circle12.addOrReplaceChild("bone48", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r94 = bone48.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(43, 1).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r95 = bone48.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(57, 11).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r96 = bone48.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(66, 62).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d5 = ball1.addOrReplaceChild("r90d5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6981F, 0.0F));

		PartDefinition circle9 = r90d5.addOrReplaceChild("circle9", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone33 = circle9.addOrReplaceChild("bone33", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r97 = bone33.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(42, 44).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r98 = bone33.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(24, 57).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r99 = bone33.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(30, 67).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone34 = circle9.addOrReplaceChild("bone34", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r100 = bone34.addOrReplaceChild("cube_r100", CubeListBuilder.create().texOffs(24, 67).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r101 = bone34.addOrReplaceChild("cube_r101", CubeListBuilder.create().texOffs(36, 44).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r102 = bone34.addOrReplaceChild("cube_r102", CubeListBuilder.create().texOffs(57, 23).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone35 = circle9.addOrReplaceChild("bone35", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r103 = bone35.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(30, 44).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r104 = bone35.addOrReplaceChild("cube_r104", CubeListBuilder.create().texOffs(57, 22).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r105 = bone35.addOrReplaceChild("cube_r105", CubeListBuilder.create().texOffs(18, 67).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone36 = circle9.addOrReplaceChild("bone36", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r106 = bone36.addOrReplaceChild("cube_r106", CubeListBuilder.create().texOffs(24, 44).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r107 = bone36.addOrReplaceChild("cube_r107", CubeListBuilder.create().texOffs(57, 21).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r108 = bone36.addOrReplaceChild("cube_r108", CubeListBuilder.create().texOffs(12, 67).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle10 = r90d5.addOrReplaceChild("circle10", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone37 = circle10.addOrReplaceChild("bone37", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r109 = bone37.addOrReplaceChild("cube_r109", CubeListBuilder.create().texOffs(18, 44).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r110 = bone37.addOrReplaceChild("cube_r110", CubeListBuilder.create().texOffs(57, 20).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r111 = bone37.addOrReplaceChild("cube_r111", CubeListBuilder.create().texOffs(6, 67).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone38 = circle10.addOrReplaceChild("bone38", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r112 = bone38.addOrReplaceChild("cube_r112", CubeListBuilder.create().texOffs(67, 4).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r113 = bone38.addOrReplaceChild("cube_r113", CubeListBuilder.create().texOffs(12, 44).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r114 = bone38.addOrReplaceChild("cube_r114", CubeListBuilder.create().texOffs(57, 19).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone39 = circle10.addOrReplaceChild("bone39", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r115 = bone39.addOrReplaceChild("cube_r115", CubeListBuilder.create().texOffs(6, 44).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r116 = bone39.addOrReplaceChild("cube_r116", CubeListBuilder.create().texOffs(57, 18).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r117 = bone39.addOrReplaceChild("cube_r117", CubeListBuilder.create().texOffs(67, 3).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone40 = circle10.addOrReplaceChild("bone40", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r118 = bone40.addOrReplaceChild("cube_r118", CubeListBuilder.create().texOffs(0, 44).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r119 = bone40.addOrReplaceChild("cube_r119", CubeListBuilder.create().texOffs(18, 57).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r120 = bone40.addOrReplaceChild("cube_r120", CubeListBuilder.create().texOffs(67, 2).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d4 = ball1.addOrReplaceChild("r90d4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition circle7 = r90d4.addOrReplaceChild("circle7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone25 = circle7.addOrReplaceChild("bone25", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r121 = bone25.addOrReplaceChild("cube_r121", CubeListBuilder.create().texOffs(45, 7).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r122 = bone25.addOrReplaceChild("cube_r122", CubeListBuilder.create().texOffs(6, 58).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r123 = bone25.addOrReplaceChild("cube_r123", CubeListBuilder.create().texOffs(6, 68).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone26 = circle7.addOrReplaceChild("bone26", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r124 = bone26.addOrReplaceChild("cube_r124", CubeListBuilder.create().texOffs(0, 68).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r125 = bone26.addOrReplaceChild("cube_r125", CubeListBuilder.create().texOffs(45, 6).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r126 = bone26.addOrReplaceChild("cube_r126", CubeListBuilder.create().texOffs(0, 58).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone27 = circle7.addOrReplaceChild("bone27", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r127 = bone27.addOrReplaceChild("cube_r127", CubeListBuilder.create().texOffs(6, 45).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r128 = bone27.addOrReplaceChild("cube_r128", CubeListBuilder.create().texOffs(54, 57).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r129 = bone27.addOrReplaceChild("cube_r129", CubeListBuilder.create().texOffs(66, 67).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone28 = circle7.addOrReplaceChild("bone28", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r130 = bone28.addOrReplaceChild("cube_r130", CubeListBuilder.create().texOffs(45, 5).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r131 = bone28.addOrReplaceChild("cube_r131", CubeListBuilder.create().texOffs(48, 57).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r132 = bone28.addOrReplaceChild("cube_r132", CubeListBuilder.create().texOffs(60, 67).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle8 = r90d4.addOrReplaceChild("circle8", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone29 = circle8.addOrReplaceChild("bone29", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r133 = bone29.addOrReplaceChild("cube_r133", CubeListBuilder.create().texOffs(45, 4).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r134 = bone29.addOrReplaceChild("cube_r134", CubeListBuilder.create().texOffs(42, 57).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r135 = bone29.addOrReplaceChild("cube_r135", CubeListBuilder.create().texOffs(54, 67).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone30 = circle8.addOrReplaceChild("bone30", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r136 = bone30.addOrReplaceChild("cube_r136", CubeListBuilder.create().texOffs(48, 67).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r137 = bone30.addOrReplaceChild("cube_r137", CubeListBuilder.create().texOffs(45, 3).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r138 = bone30.addOrReplaceChild("cube_r138", CubeListBuilder.create().texOffs(36, 57).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone31 = circle8.addOrReplaceChild("bone31", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r139 = bone31.addOrReplaceChild("cube_r139", CubeListBuilder.create().texOffs(45, 2).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r140 = bone31.addOrReplaceChild("cube_r140", CubeListBuilder.create().texOffs(30, 57).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r141 = bone31.addOrReplaceChild("cube_r141", CubeListBuilder.create().texOffs(42, 67).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone32 = circle8.addOrReplaceChild("bone32", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r142 = bone32.addOrReplaceChild("cube_r142", CubeListBuilder.create().texOffs(0, 45).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r143 = bone32.addOrReplaceChild("cube_r143", CubeListBuilder.create().texOffs(57, 24).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r144 = bone32.addOrReplaceChild("cube_r144", CubeListBuilder.create().texOffs(36, 67).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d3 = ball1.addOrReplaceChild("r90d3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.3491F, 0.0F));

		PartDefinition circle5 = r90d3.addOrReplaceChild("circle5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone17 = circle5.addOrReplaceChild("bone17", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r145 = bone17.addOrReplaceChild("cube_r145", CubeListBuilder.create().texOffs(45, 14).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r146 = bone17.addOrReplaceChild("cube_r146", CubeListBuilder.create().texOffs(30, 58).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r147 = bone17.addOrReplaceChild("cube_r147", CubeListBuilder.create().texOffs(54, 68).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone18 = circle5.addOrReplaceChild("bone18", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r148 = bone18.addOrReplaceChild("cube_r148", CubeListBuilder.create().texOffs(48, 68).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r149 = bone18.addOrReplaceChild("cube_r149", CubeListBuilder.create().texOffs(45, 13).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r150 = bone18.addOrReplaceChild("cube_r150", CubeListBuilder.create().texOffs(58, 28).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone19 = circle5.addOrReplaceChild("bone19", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r151 = bone19.addOrReplaceChild("cube_r151", CubeListBuilder.create().texOffs(45, 12).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r152 = bone19.addOrReplaceChild("cube_r152", CubeListBuilder.create().texOffs(58, 27).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r153 = bone19.addOrReplaceChild("cube_r153", CubeListBuilder.create().texOffs(42, 68).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone20 = circle5.addOrReplaceChild("bone20", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r154 = bone20.addOrReplaceChild("cube_r154", CubeListBuilder.create().texOffs(12, 45).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r155 = bone20.addOrReplaceChild("cube_r155", CubeListBuilder.create().texOffs(58, 26).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r156 = bone20.addOrReplaceChild("cube_r156", CubeListBuilder.create().texOffs(36, 68).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle6 = r90d3.addOrReplaceChild("circle6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone21 = circle6.addOrReplaceChild("bone21", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r157 = bone21.addOrReplaceChild("cube_r157", CubeListBuilder.create().texOffs(45, 11).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r158 = bone21.addOrReplaceChild("cube_r158", CubeListBuilder.create().texOffs(58, 25).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r159 = bone21.addOrReplaceChild("cube_r159", CubeListBuilder.create().texOffs(30, 68).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone22 = circle6.addOrReplaceChild("bone22", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r160 = bone22.addOrReplaceChild("cube_r160", CubeListBuilder.create().texOffs(24, 68).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r161 = bone22.addOrReplaceChild("cube_r161", CubeListBuilder.create().texOffs(45, 10).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r162 = bone22.addOrReplaceChild("cube_r162", CubeListBuilder.create().texOffs(24, 58).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone23 = circle6.addOrReplaceChild("bone23", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r163 = bone23.addOrReplaceChild("cube_r163", CubeListBuilder.create().texOffs(45, 9).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r164 = bone23.addOrReplaceChild("cube_r164", CubeListBuilder.create().texOffs(18, 58).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r165 = bone23.addOrReplaceChild("cube_r165", CubeListBuilder.create().texOffs(18, 68).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone24 = circle6.addOrReplaceChild("bone24", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r166 = bone24.addOrReplaceChild("cube_r166", CubeListBuilder.create().texOffs(45, 8).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r167 = bone24.addOrReplaceChild("cube_r167", CubeListBuilder.create().texOffs(12, 58).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r168 = bone24.addOrReplaceChild("cube_r168", CubeListBuilder.create().texOffs(12, 68).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d2 = ball1.addOrReplaceChild("r90d2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.1745F, 0.0F));

		PartDefinition circle3 = r90d2.addOrReplaceChild("circle3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone9 = circle3.addOrReplaceChild("bone9", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r169 = bone9.addOrReplaceChild("cube_r169", CubeListBuilder.create().texOffs(45, 21).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r170 = bone9.addOrReplaceChild("cube_r170", CubeListBuilder.create().texOffs(18, 59).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r171 = bone9.addOrReplaceChild("cube_r171", CubeListBuilder.create().texOffs(69, 8).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone10 = circle3.addOrReplaceChild("bone10", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r172 = bone10.addOrReplaceChild("cube_r172", CubeListBuilder.create().texOffs(69, 7).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r173 = bone10.addOrReplaceChild("cube_r173", CubeListBuilder.create().texOffs(45, 20).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r174 = bone10.addOrReplaceChild("cube_r174", CubeListBuilder.create().texOffs(12, 59).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone11 = circle3.addOrReplaceChild("bone11", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r175 = bone11.addOrReplaceChild("cube_r175", CubeListBuilder.create().texOffs(45, 19).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r176 = bone11.addOrReplaceChild("cube_r176", CubeListBuilder.create().texOffs(6, 59).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r177 = bone11.addOrReplaceChild("cube_r177", CubeListBuilder.create().texOffs(69, 6).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone12 = circle3.addOrReplaceChild("bone12", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r178 = bone12.addOrReplaceChild("cube_r178", CubeListBuilder.create().texOffs(45, 18).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r179 = bone12.addOrReplaceChild("cube_r179", CubeListBuilder.create().texOffs(0, 59).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r180 = bone12.addOrReplaceChild("cube_r180", CubeListBuilder.create().texOffs(6, 69).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle4 = r90d2.addOrReplaceChild("circle4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone13 = circle4.addOrReplaceChild("bone13", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r181 = bone13.addOrReplaceChild("cube_r181", CubeListBuilder.create().texOffs(18, 45).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r182 = bone13.addOrReplaceChild("cube_r182", CubeListBuilder.create().texOffs(54, 58).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r183 = bone13.addOrReplaceChild("cube_r183", CubeListBuilder.create().texOffs(69, 5).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone14 = circle4.addOrReplaceChild("bone14", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r184 = bone14.addOrReplaceChild("cube_r184", CubeListBuilder.create().texOffs(0, 69).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r185 = bone14.addOrReplaceChild("cube_r185", CubeListBuilder.create().texOffs(45, 17).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r186 = bone14.addOrReplaceChild("cube_r186", CubeListBuilder.create().texOffs(48, 58).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone15 = circle4.addOrReplaceChild("bone15", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r187 = bone15.addOrReplaceChild("cube_r187", CubeListBuilder.create().texOffs(45, 16).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r188 = bone15.addOrReplaceChild("cube_r188", CubeListBuilder.create().texOffs(42, 58).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r189 = bone15.addOrReplaceChild("cube_r189", CubeListBuilder.create().texOffs(66, 68).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone16 = circle4.addOrReplaceChild("bone16", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r190 = bone16.addOrReplaceChild("cube_r190", CubeListBuilder.create().texOffs(45, 15).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r191 = bone16.addOrReplaceChild("cube_r191", CubeListBuilder.create().texOffs(36, 58).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r192 = bone16.addOrReplaceChild("cube_r192", CubeListBuilder.create().texOffs(60, 68).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d1 = ball1.addOrReplaceChild("r90d1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition circle2 = r90d1.addOrReplaceChild("circle2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone5 = circle2.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r193 = bone5.addOrReplaceChild("cube_r193", CubeListBuilder.create().texOffs(42, 45).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r194 = bone5.addOrReplaceChild("cube_r194", CubeListBuilder.create().texOffs(42, 59).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r195 = bone5.addOrReplaceChild("cube_r195", CubeListBuilder.create().texOffs(12, 69).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone6 = circle2.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r196 = bone6.addOrReplaceChild("cube_r196", CubeListBuilder.create().texOffs(69, 11).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r197 = bone6.addOrReplaceChild("cube_r197", CubeListBuilder.create().texOffs(36, 45).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r198 = bone6.addOrReplaceChild("cube_r198", CubeListBuilder.create().texOffs(36, 59).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone7 = circle2.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r199 = bone7.addOrReplaceChild("cube_r199", CubeListBuilder.create().texOffs(30, 45).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r200 = bone7.addOrReplaceChild("cube_r200", CubeListBuilder.create().texOffs(30, 59).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r201 = bone7.addOrReplaceChild("cube_r201", CubeListBuilder.create().texOffs(69, 10).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone8 = circle2.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r202 = bone8.addOrReplaceChild("cube_r202", CubeListBuilder.create().texOffs(24, 45).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r203 = bone8.addOrReplaceChild("cube_r203", CubeListBuilder.create().texOffs(24, 59).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r204 = bone8.addOrReplaceChild("cube_r204", CubeListBuilder.create().texOffs(69, 9).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle1 = r90d1.addOrReplaceChild("circle1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone = circle1.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r205 = bone.addOrReplaceChild("cube_r205", CubeListBuilder.create().texOffs(18, 46).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r206 = bone.addOrReplaceChild("cube_r206", CubeListBuilder.create().texOffs(6, 60).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r207 = bone.addOrReplaceChild("cube_r207", CubeListBuilder.create().texOffs(69, 15).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone2 = circle1.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r208 = bone2.addOrReplaceChild("cube_r208", CubeListBuilder.create().texOffs(69, 14).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r209 = bone2.addOrReplaceChild("cube_r209", CubeListBuilder.create().texOffs(12, 46).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r210 = bone2.addOrReplaceChild("cube_r210", CubeListBuilder.create().texOffs(0, 60).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone3 = circle1.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r211 = bone3.addOrReplaceChild("cube_r211", CubeListBuilder.create().texOffs(6, 46).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r212 = bone3.addOrReplaceChild("cube_r212", CubeListBuilder.create().texOffs(54, 59).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r213 = bone3.addOrReplaceChild("cube_r213", CubeListBuilder.create().texOffs(69, 13).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone4 = circle1.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r214 = bone4.addOrReplaceChild("cube_r214", CubeListBuilder.create().texOffs(0, 46).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r215 = bone4.addOrReplaceChild("cube_r215", CubeListBuilder.create().texOffs(48, 59).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r216 = bone4.addOrReplaceChild("cube_r216", CubeListBuilder.create().texOffs(69, 12).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition ball2 = body.addOrReplaceChild("ball2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition r90d10 = ball2.addOrReplaceChild("r90d10", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.3963F, 0.0F));

		PartDefinition circle19 = r90d10.addOrReplaceChild("circle19", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone73 = circle19.addOrReplaceChild("bone73", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r217 = bone73.addOrReplaceChild("cube_r217", CubeListBuilder.create().texOffs(6, 42).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r218 = bone73.addOrReplaceChild("cube_r218", CubeListBuilder.create().texOffs(18, 55).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r219 = bone73.addOrReplaceChild("cube_r219", CubeListBuilder.create().texOffs(66, 41).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone74 = circle19.addOrReplaceChild("bone74", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r220 = bone74.addOrReplaceChild("cube_r220", CubeListBuilder.create().texOffs(66, 40).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r221 = bone74.addOrReplaceChild("cube_r221", CubeListBuilder.create().texOffs(0, 42).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r222 = bone74.addOrReplaceChild("cube_r222", CubeListBuilder.create().texOffs(12, 55).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone75 = circle19.addOrReplaceChild("bone75", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r223 = bone75.addOrReplaceChild("cube_r223", CubeListBuilder.create().texOffs(36, 41).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r224 = bone75.addOrReplaceChild("cube_r224", CubeListBuilder.create().texOffs(6, 55).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r225 = bone75.addOrReplaceChild("cube_r225", CubeListBuilder.create().texOffs(66, 39).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone76 = circle19.addOrReplaceChild("bone76", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r226 = bone76.addOrReplaceChild("cube_r226", CubeListBuilder.create().texOffs(30, 41).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r227 = bone76.addOrReplaceChild("cube_r227", CubeListBuilder.create().texOffs(55, 4).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r228 = bone76.addOrReplaceChild("cube_r228", CubeListBuilder.create().texOffs(66, 38).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle20 = r90d10.addOrReplaceChild("circle20", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone77 = circle20.addOrReplaceChild("bone77", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r229 = bone77.addOrReplaceChild("cube_r229", CubeListBuilder.create().texOffs(24, 41).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r230 = bone77.addOrReplaceChild("cube_r230", CubeListBuilder.create().texOffs(55, 3).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r231 = bone77.addOrReplaceChild("cube_r231", CubeListBuilder.create().texOffs(66, 37).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone78 = circle20.addOrReplaceChild("bone78", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r232 = bone78.addOrReplaceChild("cube_r232", CubeListBuilder.create().texOffs(66, 36).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r233 = bone78.addOrReplaceChild("cube_r233", CubeListBuilder.create().texOffs(18, 41).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r234 = bone78.addOrReplaceChild("cube_r234", CubeListBuilder.create().texOffs(55, 2).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone79 = circle20.addOrReplaceChild("bone79", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r235 = bone79.addOrReplaceChild("cube_r235", CubeListBuilder.create().texOffs(12, 41).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r236 = bone79.addOrReplaceChild("cube_r236", CubeListBuilder.create().texOffs(55, 1).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r237 = bone79.addOrReplaceChild("cube_r237", CubeListBuilder.create().texOffs(36, 66).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone80 = circle20.addOrReplaceChild("bone80", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r238 = bone80.addOrReplaceChild("cube_r238", CubeListBuilder.create().texOffs(6, 41).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r239 = bone80.addOrReplaceChild("cube_r239", CubeListBuilder.create().texOffs(55, 0).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r240 = bone80.addOrReplaceChild("cube_r240", CubeListBuilder.create().texOffs(66, 35).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d11 = ball2.addOrReplaceChild("r90d11", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.2217F, 0.0F));

		PartDefinition circle21 = r90d11.addOrReplaceChild("circle21", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone81 = circle21.addOrReplaceChild("bone81", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r241 = bone81.addOrReplaceChild("cube_r241", CubeListBuilder.create().texOffs(0, 41).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r242 = bone81.addOrReplaceChild("cube_r242", CubeListBuilder.create().texOffs(0, 55).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r243 = bone81.addOrReplaceChild("cube_r243", CubeListBuilder.create().texOffs(66, 34).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone82 = circle21.addOrReplaceChild("bone82", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r244 = bone82.addOrReplaceChild("cube_r244", CubeListBuilder.create().texOffs(66, 33).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r245 = bone82.addOrReplaceChild("cube_r245", CubeListBuilder.create().texOffs(36, 40).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r246 = bone82.addOrReplaceChild("cube_r246", CubeListBuilder.create().texOffs(54, 54).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone83 = circle21.addOrReplaceChild("bone83", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r247 = bone83.addOrReplaceChild("cube_r247", CubeListBuilder.create().texOffs(30, 40).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r248 = bone83.addOrReplaceChild("cube_r248", CubeListBuilder.create().texOffs(54, 53).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r249 = bone83.addOrReplaceChild("cube_r249", CubeListBuilder.create().texOffs(66, 32).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone84 = circle21.addOrReplaceChild("bone84", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r250 = bone84.addOrReplaceChild("cube_r250", CubeListBuilder.create().texOffs(40, 25).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r251 = bone84.addOrReplaceChild("cube_r251", CubeListBuilder.create().texOffs(54, 52).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r252 = bone84.addOrReplaceChild("cube_r252", CubeListBuilder.create().texOffs(66, 31).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle22 = r90d11.addOrReplaceChild("circle22", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone85 = circle22.addOrReplaceChild("bone85", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r253 = bone85.addOrReplaceChild("cube_r253", CubeListBuilder.create().texOffs(40, 24).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r254 = bone85.addOrReplaceChild("cube_r254", CubeListBuilder.create().texOffs(54, 51).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r255 = bone85.addOrReplaceChild("cube_r255", CubeListBuilder.create().texOffs(66, 30).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone86 = circle22.addOrReplaceChild("bone86", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r256 = bone86.addOrReplaceChild("cube_r256", CubeListBuilder.create().texOffs(30, 66).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r257 = bone86.addOrReplaceChild("cube_r257", CubeListBuilder.create().texOffs(24, 40).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r258 = bone86.addOrReplaceChild("cube_r258", CubeListBuilder.create().texOffs(54, 50).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone87 = circle22.addOrReplaceChild("bone87", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r259 = bone87.addOrReplaceChild("cube_r259", CubeListBuilder.create().texOffs(40, 23).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r260 = bone87.addOrReplaceChild("cube_r260", CubeListBuilder.create().texOffs(54, 49).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r261 = bone87.addOrReplaceChild("cube_r261", CubeListBuilder.create().texOffs(66, 29).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone88 = circle22.addOrReplaceChild("bone88", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r262 = bone88.addOrReplaceChild("cube_r262", CubeListBuilder.create().texOffs(40, 22).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r263 = bone88.addOrReplaceChild("cube_r263", CubeListBuilder.create().texOffs(54, 48).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r264 = bone88.addOrReplaceChild("cube_r264", CubeListBuilder.create().texOffs(24, 66).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d12 = ball2.addOrReplaceChild("r90d12", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition circle23 = r90d12.addOrReplaceChild("circle23", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone89 = circle23.addOrReplaceChild("bone89", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r265 = bone89.addOrReplaceChild("cube_r265", CubeListBuilder.create().texOffs(18, 40).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r266 = bone89.addOrReplaceChild("cube_r266", CubeListBuilder.create().texOffs(48, 54).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r267 = bone89.addOrReplaceChild("cube_r267", CubeListBuilder.create().texOffs(18, 66).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone90 = circle23.addOrReplaceChild("bone90", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r268 = bone90.addOrReplaceChild("cube_r268", CubeListBuilder.create().texOffs(12, 66).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r269 = bone90.addOrReplaceChild("cube_r269", CubeListBuilder.create().texOffs(12, 40).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r270 = bone90.addOrReplaceChild("cube_r270", CubeListBuilder.create().texOffs(54, 47).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone91 = circle23.addOrReplaceChild("bone91", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r271 = bone91.addOrReplaceChild("cube_r271", CubeListBuilder.create().texOffs(6, 40).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r272 = bone91.addOrReplaceChild("cube_r272", CubeListBuilder.create().texOffs(54, 46).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r273 = bone91.addOrReplaceChild("cube_r273", CubeListBuilder.create().texOffs(6, 66).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone92 = circle23.addOrReplaceChild("bone92", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r274 = bone92.addOrReplaceChild("cube_r274", CubeListBuilder.create().texOffs(0, 40).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r275 = bone92.addOrReplaceChild("cube_r275", CubeListBuilder.create().texOffs(54, 45).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r276 = bone92.addOrReplaceChild("cube_r276", CubeListBuilder.create().texOffs(0, 66).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle24 = r90d12.addOrReplaceChild("circle24", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone93 = circle24.addOrReplaceChild("bone93", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r277 = bone93.addOrReplaceChild("cube_r277", CubeListBuilder.create().texOffs(36, 39).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r278 = bone93.addOrReplaceChild("cube_r278", CubeListBuilder.create().texOffs(54, 44).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r279 = bone93.addOrReplaceChild("cube_r279", CubeListBuilder.create().texOffs(60, 65).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone94 = circle24.addOrReplaceChild("bone94", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r280 = bone94.addOrReplaceChild("cube_r280", CubeListBuilder.create().texOffs(54, 65).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r281 = bone94.addOrReplaceChild("cube_r281", CubeListBuilder.create().texOffs(30, 39).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r282 = bone94.addOrReplaceChild("cube_r282", CubeListBuilder.create().texOffs(54, 43).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone95 = circle24.addOrReplaceChild("bone95", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r283 = bone95.addOrReplaceChild("cube_r283", CubeListBuilder.create().texOffs(24, 39).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r284 = bone95.addOrReplaceChild("cube_r284", CubeListBuilder.create().texOffs(54, 42).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r285 = bone95.addOrReplaceChild("cube_r285", CubeListBuilder.create().texOffs(48, 65).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone96 = circle24.addOrReplaceChild("bone96", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r286 = bone96.addOrReplaceChild("cube_r286", CubeListBuilder.create().texOffs(39, 21).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r287 = bone96.addOrReplaceChild("cube_r287", CubeListBuilder.create().texOffs(42, 54).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r288 = bone96.addOrReplaceChild("cube_r288", CubeListBuilder.create().texOffs(42, 65).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d13 = ball2.addOrReplaceChild("r90d13", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.8727F, 0.0F));

		PartDefinition circle25 = r90d13.addOrReplaceChild("circle25", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone97 = circle25.addOrReplaceChild("bone97", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r289 = bone97.addOrReplaceChild("cube_r289", CubeListBuilder.create().texOffs(39, 20).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r290 = bone97.addOrReplaceChild("cube_r290", CubeListBuilder.create().texOffs(54, 41).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r291 = bone97.addOrReplaceChild("cube_r291", CubeListBuilder.create().texOffs(36, 65).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone98 = circle25.addOrReplaceChild("bone98", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r292 = bone98.addOrReplaceChild("cube_r292", CubeListBuilder.create().texOffs(30, 65).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r293 = bone98.addOrReplaceChild("cube_r293", CubeListBuilder.create().texOffs(39, 19).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r294 = bone98.addOrReplaceChild("cube_r294", CubeListBuilder.create().texOffs(54, 40).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone99 = circle25.addOrReplaceChild("bone99", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r295 = bone99.addOrReplaceChild("cube_r295", CubeListBuilder.create().texOffs(39, 18).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r296 = bone99.addOrReplaceChild("cube_r296", CubeListBuilder.create().texOffs(54, 39).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r297 = bone99.addOrReplaceChild("cube_r297", CubeListBuilder.create().texOffs(24, 65).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone100 = circle25.addOrReplaceChild("bone100", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r298 = bone100.addOrReplaceChild("cube_r298", CubeListBuilder.create().texOffs(18, 39).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r299 = bone100.addOrReplaceChild("cube_r299", CubeListBuilder.create().texOffs(54, 38).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r300 = bone100.addOrReplaceChild("cube_r300", CubeListBuilder.create().texOffs(18, 65).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle26 = r90d13.addOrReplaceChild("circle26", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone101 = circle26.addOrReplaceChild("bone101", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r301 = bone101.addOrReplaceChild("cube_r301", CubeListBuilder.create().texOffs(39, 17).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r302 = bone101.addOrReplaceChild("cube_r302", CubeListBuilder.create().texOffs(54, 37).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r303 = bone101.addOrReplaceChild("cube_r303", CubeListBuilder.create().texOffs(12, 65).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone102 = circle26.addOrReplaceChild("bone102", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r304 = bone102.addOrReplaceChild("cube_r304", CubeListBuilder.create().texOffs(6, 65).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r305 = bone102.addOrReplaceChild("cube_r305", CubeListBuilder.create().texOffs(39, 16).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r306 = bone102.addOrReplaceChild("cube_r306", CubeListBuilder.create().texOffs(54, 36).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone103 = circle26.addOrReplaceChild("bone103", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r307 = bone103.addOrReplaceChild("cube_r307", CubeListBuilder.create().texOffs(39, 15).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r308 = bone103.addOrReplaceChild("cube_r308", CubeListBuilder.create().texOffs(36, 54).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r309 = bone103.addOrReplaceChild("cube_r309", CubeListBuilder.create().texOffs(0, 65).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone104 = circle26.addOrReplaceChild("bone104", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r310 = bone104.addOrReplaceChild("cube_r310", CubeListBuilder.create().texOffs(39, 14).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r311 = bone104.addOrReplaceChild("cube_r311", CubeListBuilder.create().texOffs(54, 35).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r312 = bone104.addOrReplaceChild("cube_r312", CubeListBuilder.create().texOffs(60, 64).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d14 = ball2.addOrReplaceChild("r90d14", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6981F, 0.0F));

		PartDefinition circle27 = r90d14.addOrReplaceChild("circle27", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone105 = circle27.addOrReplaceChild("bone105", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r313 = bone105.addOrReplaceChild("cube_r313", CubeListBuilder.create().texOffs(39, 13).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r314 = bone105.addOrReplaceChild("cube_r314", CubeListBuilder.create().texOffs(54, 34).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r315 = bone105.addOrReplaceChild("cube_r315", CubeListBuilder.create().texOffs(54, 64).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone106 = circle27.addOrReplaceChild("bone106", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r316 = bone106.addOrReplaceChild("cube_r316", CubeListBuilder.create().texOffs(48, 64).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r317 = bone106.addOrReplaceChild("cube_r317", CubeListBuilder.create().texOffs(39, 12).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r318 = bone106.addOrReplaceChild("cube_r318", CubeListBuilder.create().texOffs(54, 33).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone107 = circle27.addOrReplaceChild("bone107", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r319 = bone107.addOrReplaceChild("cube_r319", CubeListBuilder.create().texOffs(12, 39).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r320 = bone107.addOrReplaceChild("cube_r320", CubeListBuilder.create().texOffs(54, 32).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r321 = bone107.addOrReplaceChild("cube_r321", CubeListBuilder.create().texOffs(42, 64).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone108 = circle27.addOrReplaceChild("bone108", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r322 = bone108.addOrReplaceChild("cube_r322", CubeListBuilder.create().texOffs(39, 11).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r323 = bone108.addOrReplaceChild("cube_r323", CubeListBuilder.create().texOffs(54, 31).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r324 = bone108.addOrReplaceChild("cube_r324", CubeListBuilder.create().texOffs(36, 64).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle28 = r90d14.addOrReplaceChild("circle28", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone109 = circle28.addOrReplaceChild("bone109", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r325 = bone109.addOrReplaceChild("cube_r325", CubeListBuilder.create().texOffs(39, 10).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r326 = bone109.addOrReplaceChild("cube_r326", CubeListBuilder.create().texOffs(54, 30).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r327 = bone109.addOrReplaceChild("cube_r327", CubeListBuilder.create().texOffs(30, 64).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone110 = circle28.addOrReplaceChild("bone110", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r328 = bone110.addOrReplaceChild("cube_r328", CubeListBuilder.create().texOffs(64, 28).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r329 = bone110.addOrReplaceChild("cube_r329", CubeListBuilder.create().texOffs(39, 9).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r330 = bone110.addOrReplaceChild("cube_r330", CubeListBuilder.create().texOffs(30, 54).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone111 = circle28.addOrReplaceChild("bone111", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r331 = bone111.addOrReplaceChild("cube_r331", CubeListBuilder.create().texOffs(39, 8).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r332 = bone111.addOrReplaceChild("cube_r332", CubeListBuilder.create().texOffs(54, 29).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r333 = bone111.addOrReplaceChild("cube_r333", CubeListBuilder.create().texOffs(64, 27).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone112 = circle28.addOrReplaceChild("bone112", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r334 = bone112.addOrReplaceChild("cube_r334", CubeListBuilder.create().texOffs(39, 7).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r335 = bone112.addOrReplaceChild("cube_r335", CubeListBuilder.create().texOffs(24, 54).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r336 = bone112.addOrReplaceChild("cube_r336", CubeListBuilder.create().texOffs(64, 26).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d15 = ball2.addOrReplaceChild("r90d15", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition circle29 = r90d15.addOrReplaceChild("circle29", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone113 = circle29.addOrReplaceChild("bone113", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r337 = bone113.addOrReplaceChild("cube_r337", CubeListBuilder.create().texOffs(39, 6).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r338 = bone113.addOrReplaceChild("cube_r338", CubeListBuilder.create().texOffs(18, 54).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r339 = bone113.addOrReplaceChild("cube_r339", CubeListBuilder.create().texOffs(64, 25).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone114 = circle29.addOrReplaceChild("bone114", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r340 = bone114.addOrReplaceChild("cube_r340", CubeListBuilder.create().texOffs(24, 64).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r341 = bone114.addOrReplaceChild("cube_r341", CubeListBuilder.create().texOffs(6, 39).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r342 = bone114.addOrReplaceChild("cube_r342", CubeListBuilder.create().texOffs(12, 54).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone115 = circle29.addOrReplaceChild("bone115", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r343 = bone115.addOrReplaceChild("cube_r343", CubeListBuilder.create().texOffs(39, 5).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r344 = bone115.addOrReplaceChild("cube_r344", CubeListBuilder.create().texOffs(6, 54).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r345 = bone115.addOrReplaceChild("cube_r345", CubeListBuilder.create().texOffs(18, 64).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone116 = circle29.addOrReplaceChild("bone116", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r346 = bone116.addOrReplaceChild("cube_r346", CubeListBuilder.create().texOffs(39, 4).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r347 = bone116.addOrReplaceChild("cube_r347", CubeListBuilder.create().texOffs(0, 54).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r348 = bone116.addOrReplaceChild("cube_r348", CubeListBuilder.create().texOffs(12, 64).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle30 = r90d15.addOrReplaceChild("circle30", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone117 = circle30.addOrReplaceChild("bone117", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r349 = bone117.addOrReplaceChild("cube_r349", CubeListBuilder.create().texOffs(39, 3).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r350 = bone117.addOrReplaceChild("cube_r350", CubeListBuilder.create().texOffs(48, 53).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r351 = bone117.addOrReplaceChild("cube_r351", CubeListBuilder.create().texOffs(6, 64).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone118 = circle30.addOrReplaceChild("bone118", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r352 = bone118.addOrReplaceChild("cube_r352", CubeListBuilder.create().texOffs(0, 64).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r353 = bone118.addOrReplaceChild("cube_r353", CubeListBuilder.create().texOffs(39, 2).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r354 = bone118.addOrReplaceChild("cube_r354", CubeListBuilder.create().texOffs(42, 53).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone119 = circle30.addOrReplaceChild("bone119", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r355 = bone119.addOrReplaceChild("cube_r355", CubeListBuilder.create().texOffs(0, 39).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r356 = bone119.addOrReplaceChild("cube_r356", CubeListBuilder.create().texOffs(36, 53).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r357 = bone119.addOrReplaceChild("cube_r357", CubeListBuilder.create().texOffs(60, 63).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone120 = circle30.addOrReplaceChild("bone120", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r358 = bone120.addOrReplaceChild("cube_r358", CubeListBuilder.create().texOffs(36, 38).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r359 = bone120.addOrReplaceChild("cube_r359", CubeListBuilder.create().texOffs(30, 53).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r360 = bone120.addOrReplaceChild("cube_r360", CubeListBuilder.create().texOffs(54, 63).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d16 = ball2.addOrReplaceChild("r90d16", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.3491F, 0.0F));

		PartDefinition circle31 = r90d16.addOrReplaceChild("circle31", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone121 = circle31.addOrReplaceChild("bone121", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r361 = bone121.addOrReplaceChild("cube_r361", CubeListBuilder.create().texOffs(30, 38).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r362 = bone121.addOrReplaceChild("cube_r362", CubeListBuilder.create().texOffs(24, 53).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r363 = bone121.addOrReplaceChild("cube_r363", CubeListBuilder.create().texOffs(48, 63).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone122 = circle31.addOrReplaceChild("bone122", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r364 = bone122.addOrReplaceChild("cube_r364", CubeListBuilder.create().texOffs(42, 63).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r365 = bone122.addOrReplaceChild("cube_r365", CubeListBuilder.create().texOffs(24, 38).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r366 = bone122.addOrReplaceChild("cube_r366", CubeListBuilder.create().texOffs(18, 53).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone123 = circle31.addOrReplaceChild("bone123", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r367 = bone123.addOrReplaceChild("cube_r367", CubeListBuilder.create().texOffs(18, 38).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r368 = bone123.addOrReplaceChild("cube_r368", CubeListBuilder.create().texOffs(12, 53).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r369 = bone123.addOrReplaceChild("cube_r369", CubeListBuilder.create().texOffs(36, 63).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone124 = circle31.addOrReplaceChild("bone124", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r370 = bone124.addOrReplaceChild("cube_r370", CubeListBuilder.create().texOffs(12, 38).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r371 = bone124.addOrReplaceChild("cube_r371", CubeListBuilder.create().texOffs(6, 53).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r372 = bone124.addOrReplaceChild("cube_r372", CubeListBuilder.create().texOffs(30, 63).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle32 = r90d16.addOrReplaceChild("circle32", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone125 = circle32.addOrReplaceChild("bone125", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r373 = bone125.addOrReplaceChild("cube_r373", CubeListBuilder.create().texOffs(6, 38).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r374 = bone125.addOrReplaceChild("cube_r374", CubeListBuilder.create().texOffs(0, 53).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r375 = bone125.addOrReplaceChild("cube_r375", CubeListBuilder.create().texOffs(63, 24).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone126 = circle32.addOrReplaceChild("bone126", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r376 = bone126.addOrReplaceChild("cube_r376", CubeListBuilder.create().texOffs(24, 63).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r377 = bone126.addOrReplaceChild("cube_r377", CubeListBuilder.create().texOffs(0, 38).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r378 = bone126.addOrReplaceChild("cube_r378", CubeListBuilder.create().texOffs(48, 52).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone127 = circle32.addOrReplaceChild("bone127", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r379 = bone127.addOrReplaceChild("cube_r379", CubeListBuilder.create().texOffs(36, 37).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r380 = bone127.addOrReplaceChild("cube_r380", CubeListBuilder.create().texOffs(42, 52).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r381 = bone127.addOrReplaceChild("cube_r381", CubeListBuilder.create().texOffs(63, 23).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone128 = circle32.addOrReplaceChild("bone128", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r382 = bone128.addOrReplaceChild("cube_r382", CubeListBuilder.create().texOffs(30, 37).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r383 = bone128.addOrReplaceChild("cube_r383", CubeListBuilder.create().texOffs(36, 52).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r384 = bone128.addOrReplaceChild("cube_r384", CubeListBuilder.create().texOffs(63, 22).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d17 = ball2.addOrReplaceChild("r90d17", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.1745F, 0.0F));

		PartDefinition circle33 = r90d17.addOrReplaceChild("circle33", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone129 = circle33.addOrReplaceChild("bone129", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r385 = bone129.addOrReplaceChild("cube_r385", CubeListBuilder.create().texOffs(24, 37).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r386 = bone129.addOrReplaceChild("cube_r386", CubeListBuilder.create().texOffs(30, 52).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r387 = bone129.addOrReplaceChild("cube_r387", CubeListBuilder.create().texOffs(63, 21).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone130 = circle33.addOrReplaceChild("bone130", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r388 = bone130.addOrReplaceChild("cube_r388", CubeListBuilder.create().texOffs(63, 20).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r389 = bone130.addOrReplaceChild("cube_r389", CubeListBuilder.create().texOffs(18, 37).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r390 = bone130.addOrReplaceChild("cube_r390", CubeListBuilder.create().texOffs(52, 28).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone131 = circle33.addOrReplaceChild("bone131", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r391 = bone131.addOrReplaceChild("cube_r391", CubeListBuilder.create().texOffs(12, 37).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r392 = bone131.addOrReplaceChild("cube_r392", CubeListBuilder.create().texOffs(52, 27).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r393 = bone131.addOrReplaceChild("cube_r393", CubeListBuilder.create().texOffs(63, 19).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone132 = circle33.addOrReplaceChild("bone132", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r394 = bone132.addOrReplaceChild("cube_r394", CubeListBuilder.create().texOffs(6, 37).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r395 = bone132.addOrReplaceChild("cube_r395", CubeListBuilder.create().texOffs(52, 26).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r396 = bone132.addOrReplaceChild("cube_r396", CubeListBuilder.create().texOffs(63, 18).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle34 = r90d17.addOrReplaceChild("circle34", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone133 = circle34.addOrReplaceChild("bone133", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r397 = bone133.addOrReplaceChild("cube_r397", CubeListBuilder.create().texOffs(37, 1).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r398 = bone133.addOrReplaceChild("cube_r398", CubeListBuilder.create().texOffs(52, 25).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r399 = bone133.addOrReplaceChild("cube_r399", CubeListBuilder.create().texOffs(18, 63).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone134 = circle34.addOrReplaceChild("bone134", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r400 = bone134.addOrReplaceChild("cube_r400", CubeListBuilder.create().texOffs(63, 17).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r401 = bone134.addOrReplaceChild("cube_r401", CubeListBuilder.create().texOffs(37, 0).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r402 = bone134.addOrReplaceChild("cube_r402", CubeListBuilder.create().texOffs(24, 52).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone135 = circle34.addOrReplaceChild("bone135", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r403 = bone135.addOrReplaceChild("cube_r403", CubeListBuilder.create().texOffs(0, 37).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r404 = bone135.addOrReplaceChild("cube_r404", CubeListBuilder.create().texOffs(18, 52).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r405 = bone135.addOrReplaceChild("cube_r405", CubeListBuilder.create().texOffs(63, 16).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone136 = circle34.addOrReplaceChild("bone136", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r406 = bone136.addOrReplaceChild("cube_r406", CubeListBuilder.create().texOffs(36, 36).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r407 = bone136.addOrReplaceChild("cube_r407", CubeListBuilder.create().texOffs(12, 52).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r408 = bone136.addOrReplaceChild("cube_r408", CubeListBuilder.create().texOffs(63, 15).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d18 = ball2.addOrReplaceChild("r90d18", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition circle35 = r90d18.addOrReplaceChild("circle35", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone137 = circle35.addOrReplaceChild("bone137", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r409 = bone137.addOrReplaceChild("cube_r409", CubeListBuilder.create().texOffs(36, 35).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r410 = bone137.addOrReplaceChild("cube_r410", CubeListBuilder.create().texOffs(6, 52).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r411 = bone137.addOrReplaceChild("cube_r411", CubeListBuilder.create().texOffs(63, 14).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone138 = circle35.addOrReplaceChild("bone138", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r412 = bone138.addOrReplaceChild("cube_r412", CubeListBuilder.create().texOffs(63, 13).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r413 = bone138.addOrReplaceChild("cube_r413", CubeListBuilder.create().texOffs(36, 34).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r414 = bone138.addOrReplaceChild("cube_r414", CubeListBuilder.create().texOffs(0, 52).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone139 = circle35.addOrReplaceChild("bone139", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r415 = bone139.addOrReplaceChild("cube_r415", CubeListBuilder.create().texOffs(36, 33).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r416 = bone139.addOrReplaceChild("cube_r416", CubeListBuilder.create().texOffs(48, 51).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r417 = bone139.addOrReplaceChild("cube_r417", CubeListBuilder.create().texOffs(63, 12).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone140 = circle35.addOrReplaceChild("bone140", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r418 = bone140.addOrReplaceChild("cube_r418", CubeListBuilder.create().texOffs(36, 32).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r419 = bone140.addOrReplaceChild("cube_r419", CubeListBuilder.create().texOffs(42, 51).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r420 = bone140.addOrReplaceChild("cube_r420", CubeListBuilder.create().texOffs(12, 63).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle36 = r90d18.addOrReplaceChild("circle36", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone141 = circle36.addOrReplaceChild("bone141", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r421 = bone141.addOrReplaceChild("cube_r421", CubeListBuilder.create().texOffs(36, 31).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r422 = bone141.addOrReplaceChild("cube_r422", CubeListBuilder.create().texOffs(36, 51).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r423 = bone141.addOrReplaceChild("cube_r423", CubeListBuilder.create().texOffs(63, 11).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone142 = circle36.addOrReplaceChild("bone142", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r424 = bone142.addOrReplaceChild("cube_r424", CubeListBuilder.create().texOffs(63, 10).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r425 = bone142.addOrReplaceChild("cube_r425", CubeListBuilder.create().texOffs(36, 30).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r426 = bone142.addOrReplaceChild("cube_r426", CubeListBuilder.create().texOffs(30, 51).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone143 = circle36.addOrReplaceChild("bone143", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r427 = bone143.addOrReplaceChild("cube_r427", CubeListBuilder.create().texOffs(30, 36).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r428 = bone143.addOrReplaceChild("cube_r428", CubeListBuilder.create().texOffs(51, 24).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r429 = bone143.addOrReplaceChild("cube_r429", CubeListBuilder.create().texOffs(63, 9).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone144 = circle36.addOrReplaceChild("bone144", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r430 = bone144.addOrReplaceChild("cube_r430", CubeListBuilder.create().texOffs(36, 29).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r431 = bone144.addOrReplaceChild("cube_r431", CubeListBuilder.create().texOffs(24, 51).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r432 = bone144.addOrReplaceChild("cube_r432", CubeListBuilder.create().texOffs(63, 8).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition ball3 = body.addOrReplaceChild("ball3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition r90d19 = ball3.addOrReplaceChild("r90d19", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.3963F, 0.0F));

		PartDefinition circle37 = r90d19.addOrReplaceChild("circle37", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone145 = circle37.addOrReplaceChild("bone145", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r433 = bone145.addOrReplaceChild("cube_r433", CubeListBuilder.create().texOffs(36, 28).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r434 = bone145.addOrReplaceChild("cube_r434", CubeListBuilder.create().texOffs(51, 23).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r435 = bone145.addOrReplaceChild("cube_r435", CubeListBuilder.create().texOffs(63, 7).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone146 = circle37.addOrReplaceChild("bone146", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r436 = bone146.addOrReplaceChild("cube_r436", CubeListBuilder.create().texOffs(63, 6).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r437 = bone146.addOrReplaceChild("cube_r437", CubeListBuilder.create().texOffs(36, 27).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r438 = bone146.addOrReplaceChild("cube_r438", CubeListBuilder.create().texOffs(51, 22).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone147 = circle37.addOrReplaceChild("bone147", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r439 = bone147.addOrReplaceChild("cube_r439", CubeListBuilder.create().texOffs(36, 26).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r440 = bone147.addOrReplaceChild("cube_r440", CubeListBuilder.create().texOffs(51, 21).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r441 = bone147.addOrReplaceChild("cube_r441", CubeListBuilder.create().texOffs(6, 63).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone148 = circle37.addOrReplaceChild("bone148", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r442 = bone148.addOrReplaceChild("cube_r442", CubeListBuilder.create().texOffs(24, 36).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r443 = bone148.addOrReplaceChild("cube_r443", CubeListBuilder.create().texOffs(51, 20).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r444 = bone148.addOrReplaceChild("cube_r444", CubeListBuilder.create().texOffs(63, 5).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle38 = r90d19.addOrReplaceChild("circle38", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone149 = circle38.addOrReplaceChild("bone149", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r445 = bone149.addOrReplaceChild("cube_r445", CubeListBuilder.create().texOffs(18, 36).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r446 = bone149.addOrReplaceChild("cube_r446", CubeListBuilder.create().texOffs(51, 19).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r447 = bone149.addOrReplaceChild("cube_r447", CubeListBuilder.create().texOffs(0, 63).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone150 = circle38.addOrReplaceChild("bone150", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r448 = bone150.addOrReplaceChild("cube_r448", CubeListBuilder.create().texOffs(60, 62).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r449 = bone150.addOrReplaceChild("cube_r449", CubeListBuilder.create().texOffs(12, 36).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r450 = bone150.addOrReplaceChild("cube_r450", CubeListBuilder.create().texOffs(51, 18).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone151 = circle38.addOrReplaceChild("bone151", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r451 = bone151.addOrReplaceChild("cube_r451", CubeListBuilder.create().texOffs(6, 36).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r452 = bone151.addOrReplaceChild("cube_r452", CubeListBuilder.create().texOffs(18, 51).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r453 = bone151.addOrReplaceChild("cube_r453", CubeListBuilder.create().texOffs(54, 62).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone152 = circle38.addOrReplaceChild("bone152", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r454 = bone152.addOrReplaceChild("cube_r454", CubeListBuilder.create().texOffs(0, 36).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r455 = bone152.addOrReplaceChild("cube_r455", CubeListBuilder.create().texOffs(51, 17).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r456 = bone152.addOrReplaceChild("cube_r456", CubeListBuilder.create().texOffs(48, 62).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d20 = ball3.addOrReplaceChild("r90d20", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.2217F, 0.0F));

		PartDefinition circle39 = r90d20.addOrReplaceChild("circle39", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone153 = circle39.addOrReplaceChild("bone153", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r457 = bone153.addOrReplaceChild("cube_r457", CubeListBuilder.create().texOffs(30, 35).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r458 = bone153.addOrReplaceChild("cube_r458", CubeListBuilder.create().texOffs(51, 16).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r459 = bone153.addOrReplaceChild("cube_r459", CubeListBuilder.create().texOffs(42, 62).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone154 = circle39.addOrReplaceChild("bone154", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r460 = bone154.addOrReplaceChild("cube_r460", CubeListBuilder.create().texOffs(36, 62).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r461 = bone154.addOrReplaceChild("cube_r461", CubeListBuilder.create().texOffs(24, 35).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r462 = bone154.addOrReplaceChild("cube_r462", CubeListBuilder.create().texOffs(51, 15).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone155 = circle39.addOrReplaceChild("bone155", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r463 = bone155.addOrReplaceChild("cube_r463", CubeListBuilder.create().texOffs(18, 35).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r464 = bone155.addOrReplaceChild("cube_r464", CubeListBuilder.create().texOffs(51, 14).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r465 = bone155.addOrReplaceChild("cube_r465", CubeListBuilder.create().texOffs(30, 62).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone156 = circle39.addOrReplaceChild("bone156", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r466 = bone156.addOrReplaceChild("cube_r466", CubeListBuilder.create().texOffs(12, 35).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r467 = bone156.addOrReplaceChild("cube_r467", CubeListBuilder.create().texOffs(51, 13).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r468 = bone156.addOrReplaceChild("cube_r468", CubeListBuilder.create().texOffs(24, 62).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle40 = r90d20.addOrReplaceChild("circle40", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone157 = circle40.addOrReplaceChild("bone157", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r469 = bone157.addOrReplaceChild("cube_r469", CubeListBuilder.create().texOffs(6, 35).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r470 = bone157.addOrReplaceChild("cube_r470", CubeListBuilder.create().texOffs(51, 12).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r471 = bone157.addOrReplaceChild("cube_r471", CubeListBuilder.create().texOffs(18, 62).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone158 = circle40.addOrReplaceChild("bone158", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r472 = bone158.addOrReplaceChild("cube_r472", CubeListBuilder.create().texOffs(12, 62).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r473 = bone158.addOrReplaceChild("cube_r473", CubeListBuilder.create().texOffs(0, 35).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r474 = bone158.addOrReplaceChild("cube_r474", CubeListBuilder.create().texOffs(12, 51).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone159 = circle40.addOrReplaceChild("bone159", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r475 = bone159.addOrReplaceChild("cube_r475", CubeListBuilder.create().texOffs(30, 34).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r476 = bone159.addOrReplaceChild("cube_r476", CubeListBuilder.create().texOffs(51, 11).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r477 = bone159.addOrReplaceChild("cube_r477", CubeListBuilder.create().texOffs(6, 62).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone160 = circle40.addOrReplaceChild("bone160", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r478 = bone160.addOrReplaceChild("cube_r478", CubeListBuilder.create().texOffs(34, 25).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r479 = bone160.addOrReplaceChild("cube_r479", CubeListBuilder.create().texOffs(51, 10).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r480 = bone160.addOrReplaceChild("cube_r480", CubeListBuilder.create().texOffs(0, 62).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d21 = ball3.addOrReplaceChild("r90d21", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition circle41 = r90d21.addOrReplaceChild("circle41", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone161 = circle41.addOrReplaceChild("bone161", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r481 = bone161.addOrReplaceChild("cube_r481", CubeListBuilder.create().texOffs(34, 24).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r482 = bone161.addOrReplaceChild("cube_r482", CubeListBuilder.create().texOffs(51, 9).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r483 = bone161.addOrReplaceChild("cube_r483", CubeListBuilder.create().texOffs(60, 61).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone162 = circle41.addOrReplaceChild("bone162", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r484 = bone162.addOrReplaceChild("cube_r484", CubeListBuilder.create().texOffs(54, 61).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r485 = bone162.addOrReplaceChild("cube_r485", CubeListBuilder.create().texOffs(24, 34).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r486 = bone162.addOrReplaceChild("cube_r486", CubeListBuilder.create().texOffs(51, 8).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone163 = circle41.addOrReplaceChild("bone163", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r487 = bone163.addOrReplaceChild("cube_r487", CubeListBuilder.create().texOffs(34, 23).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r488 = bone163.addOrReplaceChild("cube_r488", CubeListBuilder.create().texOffs(51, 7).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r489 = bone163.addOrReplaceChild("cube_r489", CubeListBuilder.create().texOffs(48, 61).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone164 = circle41.addOrReplaceChild("bone164", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r490 = bone164.addOrReplaceChild("cube_r490", CubeListBuilder.create().texOffs(34, 22).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r491 = bone164.addOrReplaceChild("cube_r491", CubeListBuilder.create().texOffs(51, 6).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r492 = bone164.addOrReplaceChild("cube_r492", CubeListBuilder.create().texOffs(42, 61).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle42 = r90d21.addOrReplaceChild("circle42", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone165 = circle42.addOrReplaceChild("bone165", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r493 = bone165.addOrReplaceChild("cube_r493", CubeListBuilder.create().texOffs(18, 34).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r494 = bone165.addOrReplaceChild("cube_r494", CubeListBuilder.create().texOffs(6, 51).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r495 = bone165.addOrReplaceChild("cube_r495", CubeListBuilder.create().texOffs(36, 61).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone166 = circle42.addOrReplaceChild("bone166", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r496 = bone166.addOrReplaceChild("cube_r496", CubeListBuilder.create().texOffs(30, 61).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r497 = bone166.addOrReplaceChild("cube_r497", CubeListBuilder.create().texOffs(12, 34).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r498 = bone166.addOrReplaceChild("cube_r498", CubeListBuilder.create().texOffs(51, 5).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone167 = circle42.addOrReplaceChild("bone167", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r499 = bone167.addOrReplaceChild("cube_r499", CubeListBuilder.create().texOffs(6, 34).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r500 = bone167.addOrReplaceChild("cube_r500", CubeListBuilder.create().texOffs(0, 51).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r501 = bone167.addOrReplaceChild("cube_r501", CubeListBuilder.create().texOffs(24, 61).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone168 = circle42.addOrReplaceChild("bone168", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r502 = bone168.addOrReplaceChild("cube_r502", CubeListBuilder.create().texOffs(0, 34).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r503 = bone168.addOrReplaceChild("cube_r503", CubeListBuilder.create().texOffs(48, 50).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r504 = bone168.addOrReplaceChild("cube_r504", CubeListBuilder.create().texOffs(18, 61).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d22 = ball3.addOrReplaceChild("r90d22", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.8727F, 0.0F));

		PartDefinition circle43 = r90d22.addOrReplaceChild("circle43", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone169 = circle43.addOrReplaceChild("bone169", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r505 = bone169.addOrReplaceChild("cube_r505", CubeListBuilder.create().texOffs(30, 33).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r506 = bone169.addOrReplaceChild("cube_r506", CubeListBuilder.create().texOffs(42, 50).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r507 = bone169.addOrReplaceChild("cube_r507", CubeListBuilder.create().texOffs(12, 61).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone170 = circle43.addOrReplaceChild("bone170", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r508 = bone170.addOrReplaceChild("cube_r508", CubeListBuilder.create().texOffs(6, 61).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r509 = bone170.addOrReplaceChild("cube_r509", CubeListBuilder.create().texOffs(24, 33).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r510 = bone170.addOrReplaceChild("cube_r510", CubeListBuilder.create().texOffs(36, 50).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone171 = circle43.addOrReplaceChild("bone171", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r511 = bone171.addOrReplaceChild("cube_r511", CubeListBuilder.create().texOffs(18, 33).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r512 = bone171.addOrReplaceChild("cube_r512", CubeListBuilder.create().texOffs(30, 50).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r513 = bone171.addOrReplaceChild("cube_r513", CubeListBuilder.create().texOffs(61, 4).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone172 = circle43.addOrReplaceChild("bone172", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r514 = bone172.addOrReplaceChild("cube_r514", CubeListBuilder.create().texOffs(12, 33).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r515 = bone172.addOrReplaceChild("cube_r515", CubeListBuilder.create().texOffs(24, 50).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r516 = bone172.addOrReplaceChild("cube_r516", CubeListBuilder.create().texOffs(61, 3).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle44 = r90d22.addOrReplaceChild("circle44", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone173 = circle44.addOrReplaceChild("bone173", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r517 = bone173.addOrReplaceChild("cube_r517", CubeListBuilder.create().texOffs(6, 33).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r518 = bone173.addOrReplaceChild("cube_r518", CubeListBuilder.create().texOffs(18, 50).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r519 = bone173.addOrReplaceChild("cube_r519", CubeListBuilder.create().texOffs(61, 2).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone174 = circle44.addOrReplaceChild("bone174", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r520 = bone174.addOrReplaceChild("cube_r520", CubeListBuilder.create().texOffs(61, 1).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r521 = bone174.addOrReplaceChild("cube_r521", CubeListBuilder.create().texOffs(0, 33).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r522 = bone174.addOrReplaceChild("cube_r522", CubeListBuilder.create().texOffs(12, 50).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone175 = circle44.addOrReplaceChild("bone175", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r523 = bone175.addOrReplaceChild("cube_r523", CubeListBuilder.create().texOffs(30, 32).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r524 = bone175.addOrReplaceChild("cube_r524", CubeListBuilder.create().texOffs(6, 50).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r525 = bone175.addOrReplaceChild("cube_r525", CubeListBuilder.create().texOffs(61, 0).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone176 = circle44.addOrReplaceChild("bone176", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r526 = bone176.addOrReplaceChild("cube_r526", CubeListBuilder.create().texOffs(24, 32).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r527 = bone176.addOrReplaceChild("cube_r527", CubeListBuilder.create().texOffs(0, 50).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r528 = bone176.addOrReplaceChild("cube_r528", CubeListBuilder.create().texOffs(0, 61).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d23 = ball3.addOrReplaceChild("r90d23", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6981F, 0.0F));

		PartDefinition circle45 = r90d23.addOrReplaceChild("circle45", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone177 = circle45.addOrReplaceChild("bone177", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r529 = bone177.addOrReplaceChild("cube_r529", CubeListBuilder.create().texOffs(18, 32).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r530 = bone177.addOrReplaceChild("cube_r530", CubeListBuilder.create().texOffs(48, 49).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r531 = bone177.addOrReplaceChild("cube_r531", CubeListBuilder.create().texOffs(60, 60).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone178 = circle45.addOrReplaceChild("bone178", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r532 = bone178.addOrReplaceChild("cube_r532", CubeListBuilder.create().texOffs(60, 59).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r533 = bone178.addOrReplaceChild("cube_r533", CubeListBuilder.create().texOffs(12, 32).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r534 = bone178.addOrReplaceChild("cube_r534", CubeListBuilder.create().texOffs(42, 49).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone179 = circle45.addOrReplaceChild("bone179", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r535 = bone179.addOrReplaceChild("cube_r535", CubeListBuilder.create().texOffs(6, 32).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r536 = bone179.addOrReplaceChild("cube_r536", CubeListBuilder.create().texOffs(36, 49).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r537 = bone179.addOrReplaceChild("cube_r537", CubeListBuilder.create().texOffs(60, 58).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone180 = circle45.addOrReplaceChild("bone180", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r538 = bone180.addOrReplaceChild("cube_r538", CubeListBuilder.create().texOffs(0, 32).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r539 = bone180.addOrReplaceChild("cube_r539", CubeListBuilder.create().texOffs(30, 49).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r540 = bone180.addOrReplaceChild("cube_r540", CubeListBuilder.create().texOffs(60, 57).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle46 = r90d23.addOrReplaceChild("circle46", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone181 = circle46.addOrReplaceChild("bone181", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r541 = bone181.addOrReplaceChild("cube_r541", CubeListBuilder.create().texOffs(30, 31).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r542 = bone181.addOrReplaceChild("cube_r542", CubeListBuilder.create().texOffs(24, 49).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r543 = bone181.addOrReplaceChild("cube_r543", CubeListBuilder.create().texOffs(60, 56).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone182 = circle46.addOrReplaceChild("bone182", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r544 = bone182.addOrReplaceChild("cube_r544", CubeListBuilder.create().texOffs(60, 55).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r545 = bone182.addOrReplaceChild("cube_r545", CubeListBuilder.create().texOffs(24, 31).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r546 = bone182.addOrReplaceChild("cube_r546", CubeListBuilder.create().texOffs(49, 4).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone183 = circle46.addOrReplaceChild("bone183", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r547 = bone183.addOrReplaceChild("cube_r547", CubeListBuilder.create().texOffs(18, 31).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r548 = bone183.addOrReplaceChild("cube_r548", CubeListBuilder.create().texOffs(49, 3).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r549 = bone183.addOrReplaceChild("cube_r549", CubeListBuilder.create().texOffs(60, 54).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone184 = circle46.addOrReplaceChild("bone184", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r550 = bone184.addOrReplaceChild("cube_r550", CubeListBuilder.create().texOffs(12, 31).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r551 = bone184.addOrReplaceChild("cube_r551", CubeListBuilder.create().texOffs(49, 2).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r552 = bone184.addOrReplaceChild("cube_r552", CubeListBuilder.create().texOffs(54, 60).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d24 = ball3.addOrReplaceChild("r90d24", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition circle47 = r90d24.addOrReplaceChild("circle47", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone185 = circle47.addOrReplaceChild("bone185", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r553 = bone185.addOrReplaceChild("cube_r553", CubeListBuilder.create().texOffs(6, 31).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r554 = bone185.addOrReplaceChild("cube_r554", CubeListBuilder.create().texOffs(49, 1).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r555 = bone185.addOrReplaceChild("cube_r555", CubeListBuilder.create().texOffs(60, 53).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone186 = circle47.addOrReplaceChild("bone186", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r556 = bone186.addOrReplaceChild("cube_r556", CubeListBuilder.create().texOffs(60, 52).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r557 = bone186.addOrReplaceChild("cube_r557", CubeListBuilder.create().texOffs(0, 31).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r558 = bone186.addOrReplaceChild("cube_r558", CubeListBuilder.create().texOffs(49, 0).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone187 = circle47.addOrReplaceChild("bone187", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r559 = bone187.addOrReplaceChild("cube_r559", CubeListBuilder.create().texOffs(30, 30).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r560 = bone187.addOrReplaceChild("cube_r560", CubeListBuilder.create().texOffs(48, 48).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r561 = bone187.addOrReplaceChild("cube_r561", CubeListBuilder.create().texOffs(60, 51).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone188 = circle47.addOrReplaceChild("bone188", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r562 = bone188.addOrReplaceChild("cube_r562", CubeListBuilder.create().texOffs(30, 29).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r563 = bone188.addOrReplaceChild("cube_r563", CubeListBuilder.create().texOffs(48, 47).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r564 = bone188.addOrReplaceChild("cube_r564", CubeListBuilder.create().texOffs(60, 50).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle48 = r90d24.addOrReplaceChild("circle48", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone189 = circle48.addOrReplaceChild("bone189", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r565 = bone189.addOrReplaceChild("cube_r565", CubeListBuilder.create().texOffs(30, 28).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r566 = bone189.addOrReplaceChild("cube_r566", CubeListBuilder.create().texOffs(48, 46).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r567 = bone189.addOrReplaceChild("cube_r567", CubeListBuilder.create().texOffs(60, 49).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone190 = circle48.addOrReplaceChild("bone190", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r568 = bone190.addOrReplaceChild("cube_r568", CubeListBuilder.create().texOffs(60, 48).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r569 = bone190.addOrReplaceChild("cube_r569", CubeListBuilder.create().texOffs(30, 27).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r570 = bone190.addOrReplaceChild("cube_r570", CubeListBuilder.create().texOffs(48, 45).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone191 = circle48.addOrReplaceChild("bone191", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r571 = bone191.addOrReplaceChild("cube_r571", CubeListBuilder.create().texOffs(30, 26).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r572 = bone191.addOrReplaceChild("cube_r572", CubeListBuilder.create().texOffs(48, 44).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r573 = bone191.addOrReplaceChild("cube_r573", CubeListBuilder.create().texOffs(48, 60).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone192 = circle48.addOrReplaceChild("bone192", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r574 = bone192.addOrReplaceChild("cube_r574", CubeListBuilder.create().texOffs(24, 30).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r575 = bone192.addOrReplaceChild("cube_r575", CubeListBuilder.create().texOffs(48, 43).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r576 = bone192.addOrReplaceChild("cube_r576", CubeListBuilder.create().texOffs(60, 47).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d25 = ball3.addOrReplaceChild("r90d25", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.3491F, 0.0F));

		PartDefinition circle49 = r90d25.addOrReplaceChild("circle49", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone193 = circle49.addOrReplaceChild("bone193", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r577 = bone193.addOrReplaceChild("cube_r577", CubeListBuilder.create().texOffs(18, 30).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r578 = bone193.addOrReplaceChild("cube_r578", CubeListBuilder.create().texOffs(48, 42).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r579 = bone193.addOrReplaceChild("cube_r579", CubeListBuilder.create().texOffs(60, 46).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone194 = circle49.addOrReplaceChild("bone194", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r580 = bone194.addOrReplaceChild("cube_r580", CubeListBuilder.create().texOffs(60, 45).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r581 = bone194.addOrReplaceChild("cube_r581", CubeListBuilder.create().texOffs(12, 30).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r582 = bone194.addOrReplaceChild("cube_r582", CubeListBuilder.create().texOffs(48, 41).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone195 = circle49.addOrReplaceChild("bone195", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r583 = bone195.addOrReplaceChild("cube_r583", CubeListBuilder.create().texOffs(6, 30).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r584 = bone195.addOrReplaceChild("cube_r584", CubeListBuilder.create().texOffs(48, 40).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r585 = bone195.addOrReplaceChild("cube_r585", CubeListBuilder.create().texOffs(60, 44).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone196 = circle49.addOrReplaceChild("bone196", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r586 = bone196.addOrReplaceChild("cube_r586", CubeListBuilder.create().texOffs(0, 30).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r587 = bone196.addOrReplaceChild("cube_r587", CubeListBuilder.create().texOffs(48, 39).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r588 = bone196.addOrReplaceChild("cube_r588", CubeListBuilder.create().texOffs(60, 43).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle50 = r90d25.addOrReplaceChild("circle50", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone197 = circle50.addOrReplaceChild("bone197", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r589 = bone197.addOrReplaceChild("cube_r589", CubeListBuilder.create().texOffs(24, 29).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r590 = bone197.addOrReplaceChild("cube_r590", CubeListBuilder.create().texOffs(48, 38).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r591 = bone197.addOrReplaceChild("cube_r591", CubeListBuilder.create().texOffs(60, 42).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone198 = circle50.addOrReplaceChild("bone198", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r592 = bone198.addOrReplaceChild("cube_r592", CubeListBuilder.create().texOffs(42, 60).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r593 = bone198.addOrReplaceChild("cube_r593", CubeListBuilder.create().texOffs(18, 29).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r594 = bone198.addOrReplaceChild("cube_r594", CubeListBuilder.create().texOffs(48, 37).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone199 = circle50.addOrReplaceChild("bone199", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r595 = bone199.addOrReplaceChild("cube_r595", CubeListBuilder.create().texOffs(12, 29).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r596 = bone199.addOrReplaceChild("cube_r596", CubeListBuilder.create().texOffs(48, 36).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r597 = bone199.addOrReplaceChild("cube_r597", CubeListBuilder.create().texOffs(60, 41).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone200 = circle50.addOrReplaceChild("bone200", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r598 = bone200.addOrReplaceChild("cube_r598", CubeListBuilder.create().texOffs(6, 29).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r599 = bone200.addOrReplaceChild("cube_r599", CubeListBuilder.create().texOffs(48, 35).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r600 = bone200.addOrReplaceChild("cube_r600", CubeListBuilder.create().texOffs(60, 40).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d26 = ball3.addOrReplaceChild("r90d26", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.1745F, 0.0F));

		PartDefinition circle51 = r90d26.addOrReplaceChild("circle51", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone201 = circle51.addOrReplaceChild("bone201", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r601 = bone201.addOrReplaceChild("cube_r601", CubeListBuilder.create().texOffs(0, 29).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r602 = bone201.addOrReplaceChild("cube_r602", CubeListBuilder.create().texOffs(48, 34).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r603 = bone201.addOrReplaceChild("cube_r603", CubeListBuilder.create().texOffs(60, 39).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone202 = circle51.addOrReplaceChild("bone202", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r604 = bone202.addOrReplaceChild("cube_r604", CubeListBuilder.create().texOffs(60, 38).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r605 = bone202.addOrReplaceChild("cube_r605", CubeListBuilder.create().texOffs(24, 28).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r606 = bone202.addOrReplaceChild("cube_r606", CubeListBuilder.create().texOffs(48, 33).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone203 = circle51.addOrReplaceChild("bone203", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r607 = bone203.addOrReplaceChild("cube_r607", CubeListBuilder.create().texOffs(18, 28).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r608 = bone203.addOrReplaceChild("cube_r608", CubeListBuilder.create().texOffs(48, 32).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r609 = bone203.addOrReplaceChild("cube_r609", CubeListBuilder.create().texOffs(60, 37).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone204 = circle51.addOrReplaceChild("bone204", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r610 = bone204.addOrReplaceChild("cube_r610", CubeListBuilder.create().texOffs(12, 28).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r611 = bone204.addOrReplaceChild("cube_r611", CubeListBuilder.create().texOffs(48, 31).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r612 = bone204.addOrReplaceChild("cube_r612", CubeListBuilder.create().texOffs(60, 36).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle52 = r90d26.addOrReplaceChild("circle52", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone205 = circle52.addOrReplaceChild("bone205", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r613 = bone205.addOrReplaceChild("cube_r613", CubeListBuilder.create().texOffs(6, 28).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r614 = bone205.addOrReplaceChild("cube_r614", CubeListBuilder.create().texOffs(48, 30).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r615 = bone205.addOrReplaceChild("cube_r615", CubeListBuilder.create().texOffs(36, 60).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone206 = circle52.addOrReplaceChild("bone206", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r616 = bone206.addOrReplaceChild("cube_r616", CubeListBuilder.create().texOffs(60, 35).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r617 = bone206.addOrReplaceChild("cube_r617", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r618 = bone206.addOrReplaceChild("cube_r618", CubeListBuilder.create().texOffs(48, 29).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone207 = circle52.addOrReplaceChild("bone207", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r619 = bone207.addOrReplaceChild("cube_r619", CubeListBuilder.create().texOffs(24, 27).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r620 = bone207.addOrReplaceChild("cube_r620", CubeListBuilder.create().texOffs(46, 28).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r621 = bone207.addOrReplaceChild("cube_r621", CubeListBuilder.create().texOffs(60, 34).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone208 = circle52.addOrReplaceChild("bone208", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r622 = bone208.addOrReplaceChild("cube_r622", CubeListBuilder.create().texOffs(18, 27).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r623 = bone208.addOrReplaceChild("cube_r623", CubeListBuilder.create().texOffs(46, 27).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r624 = bone208.addOrReplaceChild("cube_r624", CubeListBuilder.create().texOffs(60, 33).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition r90d27 = ball3.addOrReplaceChild("r90d27", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition circle53 = r90d27.addOrReplaceChild("circle53", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone209 = circle53.addOrReplaceChild("bone209", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r625 = bone209.addOrReplaceChild("cube_r625", CubeListBuilder.create().texOffs(12, 27).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r626 = bone209.addOrReplaceChild("cube_r626", CubeListBuilder.create().texOffs(46, 26).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r627 = bone209.addOrReplaceChild("cube_r627", CubeListBuilder.create().texOffs(60, 32).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone210 = circle53.addOrReplaceChild("bone210", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r628 = bone210.addOrReplaceChild("cube_r628", CubeListBuilder.create().texOffs(60, 31).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r629 = bone210.addOrReplaceChild("cube_r629", CubeListBuilder.create().texOffs(6, 27).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r630 = bone210.addOrReplaceChild("cube_r630", CubeListBuilder.create().texOffs(46, 25).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone211 = circle53.addOrReplaceChild("bone211", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r631 = bone211.addOrReplaceChild("cube_r631", CubeListBuilder.create().texOffs(0, 27).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r632 = bone211.addOrReplaceChild("cube_r632", CubeListBuilder.create().texOffs(43, 2).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r633 = bone211.addOrReplaceChild("cube_r633", CubeListBuilder.create().texOffs(60, 30).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone212 = circle53.addOrReplaceChild("bone212", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r634 = bone212.addOrReplaceChild("cube_r634", CubeListBuilder.create().texOffs(24, 26).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r635 = bone212.addOrReplaceChild("cube_r635", CubeListBuilder.create().texOffs(43, 1).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r636 = bone212.addOrReplaceChild("cube_r636", CubeListBuilder.create().texOffs(30, 60).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition circle54 = r90d27.addOrReplaceChild("circle54", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone213 = circle54.addOrReplaceChild("bone213", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition cube_r637 = bone213.addOrReplaceChild("cube_r637", CubeListBuilder.create().texOffs(18, 26).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r638 = bone213.addOrReplaceChild("cube_r638", CubeListBuilder.create().texOffs(43, 0).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r639 = bone213.addOrReplaceChild("cube_r639", CubeListBuilder.create().texOffs(60, 29).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone214 = circle54.addOrReplaceChild("bone214", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r640 = bone214.addOrReplaceChild("cube_r640", CubeListBuilder.create().texOffs(24, 60).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r641 = bone214.addOrReplaceChild("cube_r641", CubeListBuilder.create().texOffs(12, 26).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r642 = bone214.addOrReplaceChild("cube_r642", CubeListBuilder.create().texOffs(37, 2).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone215 = circle54.addOrReplaceChild("bone215", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r643 = bone215.addOrReplaceChild("cube_r643", CubeListBuilder.create().texOffs(6, 26).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r644 = bone215.addOrReplaceChild("cube_r644", CubeListBuilder.create().texOffs(37, 1).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r645 = bone215.addOrReplaceChild("cube_r645", CubeListBuilder.create().texOffs(18, 60).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone216 = circle54.addOrReplaceChild("bone216", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r646 = bone216.addOrReplaceChild("cube_r646", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, -0.5F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.795F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r647 = bone216.addOrReplaceChild("cube_r647", CubeListBuilder.create().texOffs(37, 0).addBox(-2.9982F, -0.5F, 0.002F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -0.5F, -5.02F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r648 = bone216.addOrReplaceChild("cube_r648", CubeListBuilder.create().texOffs(12, 60).addBox(-3.0F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.795F, 0.0F, 0.2618F, 0.0F));

		PartDefinition hider = body.addOrReplaceChild("hider", CubeListBuilder.create().texOffs(17, 69).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
		.texOffs(69, 16).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 26).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F))
		.texOffs(0, 24).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.4F))
		.texOffs(0, 22).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.6F))
		.texOffs(0, 20).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.8F))
		.texOffs(0, 18).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(1.0F))
		.texOffs(0, 16).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(1.2F))
		.texOffs(0, 14).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(1.4F))
		.texOffs(0, 12).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(1.6F))
		.texOffs(0, 10).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(1.8F))
		.texOffs(0, 8).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(2.0F))
		.texOffs(0, 6).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(2.2F))
		.texOffs(0, 4).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(2.4F))
		.texOffs(0, 2).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(2.6F))
		.texOffs(0, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(2.8F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition plate = partdefinition.addOrReplaceChild("plate", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition plate1 = plate.addOrReplaceChild("plate1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone290 = plate1.addOrReplaceChild("bone290", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r649 = bone290.addOrReplaceChild("cube_r649", CubeListBuilder.create().texOffs(25, 21).addBox(0.0F, -0.001F, -4.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.727F, 0.0F, 0.0F, 0.035F, -0.2595F, -0.1355F));

		PartDefinition cube_r650 = bone290.addOrReplaceChild("cube_r650", CubeListBuilder.create().texOffs(24, 0).addBox(-4.0159F, 0.0F, 0.0064F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.85F, 0.0F, -6.702F, 0.1309F, 0.7854F, 0.0F));

		PartDefinition cube_r651 = bone290.addOrReplaceChild("cube_r651", CubeListBuilder.create().texOffs(8, 15).addBox(-4.0F, -0.001F, 0.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.727F, 0.1309F, 0.2618F, 0.0F));

		PartDefinition bone291 = plate1.addOrReplaceChild("bone291", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r652 = bone291.addOrReplaceChild("cube_r652", CubeListBuilder.create().texOffs(20, 25).addBox(0.0F, 0.0F, -4.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.727F, 0.0F, 0.0F, 0.035F, -0.2595F, -0.1355F));

		PartDefinition cube_r653 = bone291.addOrReplaceChild("cube_r653", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0159F, -0.001F, 0.0064F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.85F, 0.0F, -6.702F, 0.1309F, 0.7854F, 0.0F));

		PartDefinition cube_r654 = bone291.addOrReplaceChild("cube_r654", CubeListBuilder.create().texOffs(0, 15).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.727F, 0.1309F, 0.2618F, 0.0F));

		PartDefinition bone292 = plate1.addOrReplaceChild("bone292", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r655 = bone292.addOrReplaceChild("cube_r655", CubeListBuilder.create().texOffs(25, 17).addBox(0.0F, -0.001F, -4.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.727F, 0.0F, 0.0F, 0.035F, -0.2595F, -0.1355F));

		PartDefinition cube_r656 = bone292.addOrReplaceChild("cube_r656", CubeListBuilder.create().texOffs(8, 20).addBox(-4.0159F, 0.0F, 0.0064F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.85F, 0.0F, -6.702F, 0.1309F, 0.7854F, 0.0F));

		PartDefinition cube_r657 = bone292.addOrReplaceChild("cube_r657", CubeListBuilder.create().texOffs(8, 10).addBox(-4.0F, -0.001F, 0.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.727F, 0.1309F, 0.2618F, 0.0F));

		PartDefinition bone293 = plate1.addOrReplaceChild("bone293", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r658 = bone293.addOrReplaceChild("cube_r658", CubeListBuilder.create().texOffs(25, 13).addBox(0.0F, 0.0F, -4.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.727F, 0.0F, 0.0F, 0.035F, -0.2595F, -0.1355F));

		PartDefinition cube_r659 = bone293.addOrReplaceChild("cube_r659", CubeListBuilder.create().texOffs(0, 20).addBox(-4.0159F, -0.001F, 0.0064F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.85F, 0.0F, -6.702F, 0.1309F, 0.7854F, 0.0F));

		PartDefinition cube_r660 = bone293.addOrReplaceChild("cube_r660", CubeListBuilder.create().texOffs(0, 10).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.727F, 0.1309F, 0.2618F, 0.0F));

		PartDefinition plate2 = plate.addOrReplaceChild("plate2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone294 = plate2.addOrReplaceChild("bone294", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r661 = bone294.addOrReplaceChild("cube_r661", CubeListBuilder.create().texOffs(10, 25).addBox(0.0F, -0.001F, -4.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.727F, 0.0F, 0.0F, -0.035F, -0.2595F, 0.1355F));

		PartDefinition cube_r662 = bone294.addOrReplaceChild("cube_r662", CubeListBuilder.create().texOffs(16, 15).addBox(-4.0159F, 0.0F, 0.0064F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.85F, 0.0F, -6.702F, -0.1309F, 0.7854F, 0.0F));

		PartDefinition cube_r663 = bone294.addOrReplaceChild("cube_r663", CubeListBuilder.create().texOffs(8, 5).addBox(-4.0F, -0.001F, 0.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.727F, -0.1309F, 0.2618F, 0.0F));

		PartDefinition bone295 = plate2.addOrReplaceChild("bone295", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r664 = bone295.addOrReplaceChild("cube_r664", CubeListBuilder.create().texOffs(25, 9).addBox(0.0F, 0.0F, -4.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.727F, 0.0F, 0.0F, -0.035F, -0.2595F, 0.1355F));

		PartDefinition cube_r665 = bone295.addOrReplaceChild("cube_r665", CubeListBuilder.create().texOffs(16, 10).addBox(-4.0159F, -0.001F, 0.0064F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.85F, 0.0F, -6.702F, -0.1309F, 0.7854F, 0.0F));

		PartDefinition cube_r666 = bone295.addOrReplaceChild("cube_r666", CubeListBuilder.create().texOffs(8, 0).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.727F, -0.1309F, 0.2618F, 0.0F));

		PartDefinition bone296 = plate2.addOrReplaceChild("bone296", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r667 = bone296.addOrReplaceChild("cube_r667", CubeListBuilder.create().texOffs(25, 5).addBox(0.0F, -0.001F, -4.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.727F, 0.0F, 0.0F, -0.035F, -0.2595F, 0.1355F));

		PartDefinition cube_r668 = bone296.addOrReplaceChild("cube_r668", CubeListBuilder.create().texOffs(16, 5).addBox(-4.0159F, 0.0F, 0.0064F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.85F, 0.0F, -6.702F, -0.1309F, 0.7854F, 0.0F));

		PartDefinition cube_r669 = bone296.addOrReplaceChild("cube_r669", CubeListBuilder.create().texOffs(0, 5).addBox(-4.0F, -0.001F, 0.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.727F, -0.1309F, 0.2618F, 0.0F));

		PartDefinition bone297 = plate2.addOrReplaceChild("bone297", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r670 = bone297.addOrReplaceChild("cube_r670", CubeListBuilder.create().texOffs(0, 25).addBox(0.0F, 0.0F, -4.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.727F, 0.0F, 0.0F, -0.035F, -0.2595F, 0.1355F));

		PartDefinition cube_r671 = bone297.addOrReplaceChild("cube_r671", CubeListBuilder.create().texOffs(16, 0).addBox(-4.0159F, -0.001F, 0.0064F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.85F, 0.0F, -6.702F, -0.1309F, 0.7854F, 0.0F));

		PartDefinition cube_r672 = bone297.addOrReplaceChild("cube_r672", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.727F, -0.1309F, 0.2618F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		plate.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}