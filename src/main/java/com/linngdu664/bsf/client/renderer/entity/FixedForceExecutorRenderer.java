package com.linngdu664.bsf.client.renderer.entity;


import com.linngdu664.bsf.client.model.FixedForceExecutorModel;
import com.linngdu664.bsf.entity.executor.AbstractFixedForceExecutor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
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

public class FixedForceExecutorRenderer extends EntityRenderer<AbstractFixedForceExecutor> {
    private final FixedForceExecutorLayerType layerType;
    private static final float SIN_45 = (float) Math.sin(Math.PI / 4D);
    private final FixedForceExecutorModel model;
    private final RenderType renderType;

    public FixedForceExecutorRenderer(EntityRendererProvider.Context pContext, FixedForceExecutorLayerType layerType) {
        super(pContext);
        this.layerType = layerType;
        this.shadowRadius = 0.1F;
        this.renderType = RenderType.entityCutoutNoCull(getTexture());
        ModelPart modelpart = pContext.bakeLayer(new ModelLayerLocation(getTexture(), "main"));
        model = new FixedForceExecutorModel<>(modelpart);
    }

    @Override
    public void render(AbstractFixedForceExecutor pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        VertexConsumer vertexconsumer = pBuffer.getBuffer(renderType);
        int i = OverlayTexture.NO_OVERLAY;
        pPoseStack.scale(1F, 1F, 1F);
        pPoseStack.pushPose();


        pPoseStack.mulPose(new Quaternionf(new AxisAngle4f(((pEntity.getTimer() + pPartialTick) * 10) % 360 * Mth.DEG_TO_RAD, SIN_45, 0F, SIN_45)));
        model.getCircle1().render(pPoseStack, vertexconsumer, pPackedLight, i);

        pPoseStack.mulPose(new Quaternionf(new AxisAngle4f(((pEntity.getTimer() + pPartialTick) * 20) % 360 * Mth.DEG_TO_RAD, -SIN_45, 0F, SIN_45)));
        model.getCircle2().render(pPoseStack, vertexconsumer, pPackedLight, i);

        pPoseStack.mulPose(new Quaternionf(new AxisAngle4f(((pEntity.getTimer() + pPartialTick) * 30) % 360 * Mth.DEG_TO_RAD, SIN_45, 0F, -SIN_45)));
        model.getBb_main().render(pPoseStack, vertexconsumer, pPackedLight, i);

        pPoseStack.popPose();


        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AbstractFixedForceExecutor pEntity) {
        return getTexture();
    }

    private ResourceLocation getTexture() {
        switch (layerType) {
            case MONSTER_GRAVITY -> {
                return new ResourceLocation("bsf:textures/models/monster_gravity_executor.png");
            }
            case MONSTER_REPULSION -> {
                return new ResourceLocation("bsf:textures/models/monster_repulsion_executor.png");
            }
            case PROJECTILE_GRAVITY -> {
                return new ResourceLocation("bsf:textures/models/projectile_gravity_executor.png");
            }
            default -> {
                return new ResourceLocation("bsf:textures/models/projectile_repulsion_executor.png");
            }
        }
    }


}
