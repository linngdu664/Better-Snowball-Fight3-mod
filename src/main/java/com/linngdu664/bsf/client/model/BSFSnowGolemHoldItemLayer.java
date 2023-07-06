package com.linngdu664.bsf.client.model;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class BSFSnowGolemHoldItemLayer extends RenderLayer<BSFSnowGolemEntity, BSFSnowGolemModel<BSFSnowGolemEntity>> {
    public BSFSnowGolemHoldItemLayer(RenderLayerParent<BSFSnowGolemEntity, BSFSnowGolemModel<BSFSnowGolemEntity>> pRenderer) {
        super(pRenderer);
    }

    @Override
    public void render(@NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, BSFSnowGolemEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        ItemStack itemstack = pLivingEntity.getWeapon();
        if (itemstack != ItemStack.EMPTY) {
            pMatrixStack.pushPose();
            pMatrixStack.translate(-0.05, 0.2, -0.8);
//            pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(pLivingEntity.getWeaponAng()));
            pMatrixStack.mulPose(new Quaternionf(new AxisAngle4f(pLivingEntity.getWeaponAng() * Mth.DEG_TO_RAD, 1F, 0F, 0F)));
            Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().renderItem(pLivingEntity, itemstack, ItemTransforms.TransformType.HEAD, false, pMatrixStack, pBuffer, pPackedLight);
            pMatrixStack.popPose();
        }
        itemstack = pLivingEntity.getAmmo();
        if (itemstack != ItemStack.EMPTY) {
            pMatrixStack.pushPose();
            Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().renderItem(pLivingEntity, itemstack, ItemTransforms.TransformType.HEAD, false, pMatrixStack, pBuffer, pPackedLight);
            pMatrixStack.popPose();
        }
    }
}
