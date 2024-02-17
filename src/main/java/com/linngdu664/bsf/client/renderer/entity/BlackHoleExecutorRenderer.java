package com.linngdu664.bsf.client.renderer.entity;

import com.linngdu664.bsf.client.model.BlackHoleExecutorModel;
import com.linngdu664.bsf.entity.executor.BlackHoleExecutor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
@Deprecated
public class BlackHoleExecutorRenderer extends EntityRenderer<BlackHoleExecutor> {
    private static final float SIN_30 = (float) Math.sin(Math.PI / 6D);
    private static final float SIN_45 = (float) Math.sin(Math.PI / 4D);
    private static final float SIN_60 = (float) Math.sin(Math.PI / 3D);
    private final BlackHoleExecutorModel model;
    private final RenderType renderType=RenderType.entityCutoutNoCull(BlackHoleExecutorModel.LAYER_LOCATION.getModel());

    public BlackHoleExecutorRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.shadowRadius = 0.1F;
        ModelPart modelpart = pContext.bakeLayer(BlackHoleExecutorModel.LAYER_LOCATION);
        model = new BlackHoleExecutorModel<>(modelpart);
    }

    @Override
    public void render(BlackHoleExecutor pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        VertexConsumer vertexconsumer = pBuffer.getBuffer(renderType);
        int i = OverlayTexture.NO_OVERLAY;
        pPoseStack.pushPose();
        float growingSize = Math.min(pEntity.getTimer() + pPartialTick,10F);
        pPoseStack.scale(growingSize, growingSize, growingSize);
        pPoseStack.mulPose(new Quaternionf(new AxisAngle4f(30 * Mth.DEG_TO_RAD, SIN_30, 0F, SIN_60)));
        pPoseStack.mulPose(new Quaternionf(new AxisAngle4f(((pEntity.getTimer() + pPartialTick) * 30) % 360 * Mth.DEG_TO_RAD, 0, 1, 0)));

        model.getCircle().render(pPoseStack, vertexconsumer, pPackedLight, i);
        pPoseStack.mulPose(new Quaternionf(new AxisAngle4f(((pEntity.getTimer() + pPartialTick) * 10) % 360 * Mth.DEG_TO_RAD, SIN_30, 0F, SIN_60)));
//        pPoseStack.mulPose(new Quaternionf(new AxisAngle4f(((pEntity.getTimer() + pPartialTick) * 10) % 360 * Mth.DEG_TO_RAD, -SIN_45, 0F, SIN_45)));
        model.getBody().render(pPoseStack, vertexconsumer, pPackedLight, i);


        pPoseStack.popPose();


        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BlackHoleExecutor pEntity) {
        return BlackHoleExecutorModel.LAYER_LOCATION.getModel();
    }

}
