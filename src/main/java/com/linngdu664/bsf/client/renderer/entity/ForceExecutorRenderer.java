package com.linngdu664.bsf.client.renderer.entity;


import com.linngdu664.bsf.client.model.ForceExecutorModel;
import com.linngdu664.bsf.entity.executor.ForceExecutor;
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
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;

public class ForceExecutorRenderer extends EntityRenderer<ForceExecutor>  {
    private final int layerType;
    private static final float SIN_45 = (float)Math.sin((Math.PI / 4D));
    private final ForceExecutorModel model;
    private final RenderType renderType;

    public ForceExecutorRenderer(EntityRendererProvider.Context pContext, int layerType) {
        super(pContext);
        this.layerType=layerType;
        this.shadowRadius=0.1F;
        this.renderType =  RenderType.entityCutoutNoCull(getTexture());
        ModelPart modelpart = pContext.bakeLayer(new ModelLayerLocation(getTexture(),"main"));
        model=new ForceExecutorModel<>(modelpart);
    }

    @Override
    public void render(ForceExecutor pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        VertexConsumer vertexconsumer = pBuffer.getBuffer(renderType);
        int i = OverlayTexture.NO_OVERLAY;
        pPoseStack.scale(0.5F, 0.5F, 0.5F);
        pPoseStack.pushPose();


        pPoseStack.mulPose(new Quaternionf(new AxisAngle4f(((pEntity.getTimer()+pPartialTick)*10)%360 * Mth.DEG_TO_RAD, SIN_45, 0F, SIN_45)));
        model.getCircle1().render(pPoseStack,vertexconsumer,pPackedLight,i);

        pPoseStack.mulPose(new Quaternionf(new AxisAngle4f(((pEntity.getTimer()+pPartialTick)*20)%360 * Mth.DEG_TO_RAD, -SIN_45, 0F, SIN_45)));
        model.getCircle2().render(pPoseStack,vertexconsumer,pPackedLight,i);

        pPoseStack.mulPose(new Quaternionf(new AxisAngle4f(((pEntity.getTimer()+pPartialTick)*30)%360 * Mth.DEG_TO_RAD, SIN_45, 0F, -SIN_45)));
        model.getBb_main().render(pPoseStack,vertexconsumer,pPackedLight,i);

        pPoseStack.popPose();



        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ForceExecutor pEntity) {
        return getTexture();
    }
    private ResourceLocation getTexture(){
        switch (layerType) {
            case 0 -> {
                return new ResourceLocation("bsf:textures/models/monster_gravity_executor.png");
            }
            case 1 -> {
                return new ResourceLocation("bsf:textures/models/monster_repulsion_executor.png");
            }
            case 2 -> {
                return new ResourceLocation("bsf:textures/models/projectile_gravity_executor.png");
            }
            default -> {
                return new ResourceLocation("bsf:textures/models/projectile_repulsion_executor.png");
            }
        }
    }


}
