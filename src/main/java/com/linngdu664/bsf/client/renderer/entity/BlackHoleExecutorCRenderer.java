package com.linngdu664.bsf.client.renderer.entity;

import com.linngdu664.bsf.client.model.BlackHoleExecutorCModel;
import com.linngdu664.bsf.entity.executor.BlackHoleExecutor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BlackHoleExecutorCRenderer extends EntityRenderer<BlackHoleExecutor> {
    private final BlackHoleExecutorCModel model;
    private final RenderType renderType = RenderType.entityCutoutNoCull(BlackHoleExecutorCModel.LAYER_LOCATION.getModel());
    private static final Vector3f VEC1 = new Vector3f(0, 1, 0);

    public BlackHoleExecutorCRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.shadowRadius = 0.1F;
        ModelPart modelpart = pContext.bakeLayer(BlackHoleExecutorCModel.LAYER_LOCATION);
        model = new BlackHoleExecutorCModel<>(modelpart);
    }

    @Override
    public void render(BlackHoleExecutor pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        VertexConsumer vertexconsumer = pBuffer.getBuffer(renderType);
        int i = OverlayTexture.NO_OVERLAY;
        pPoseStack.pushPose();
        float growingSize = Math.min(pEntity.getModelTicker() + pPartialTick, (float) Math.sqrt(pEntity.getRank()) * 1.7f);

        pPoseStack.scale(growingSize, growingSize, growingSize);
        pPoseStack.mulPose(new Quaternion(pEntity.getAxis(), pEntity.getAngle1(), false));

//        pPoseStack.mulPose(new Quaternionf(new AxisAngle4f(30 * Mth.DEG_TO_RAD, BSFMthUtil.SIN_30, 0F, BSFMthUtil.SIN_60)));
        pPoseStack.mulPose(new Quaternion(pEntity.getProjection(), pEntity.getObliquity(), false));
        pPoseStack.mulPose(new Quaternion(VEC1, ((pEntity.getTimer() + pPartialTick) * BlackHoleExecutor.SPINNING_SPEED) % 360, true));

        model.getPlate().render(pPoseStack, vertexconsumer, pPackedLight, i);
        pPoseStack.scale(0.6f, 0.6f, 0.6f);
//        pPoseStack.mulPose(new Quaternionf(new AxisAngle4f(((pEntity.getTimer() + pPartialTick) * 10) % 360 * Mth.DEG_TO_RAD, SIN_30, 0F, SIN_60)));
        model.getBody().render(pPoseStack, vertexconsumer, pPackedLight, i);


        pPoseStack.popPose();


        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BlackHoleExecutor pEntity) {
        return BlackHoleExecutorCModel.LAYER_LOCATION.getModel();
    }

}
