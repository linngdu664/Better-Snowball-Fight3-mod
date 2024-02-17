package com.linngdu664.bsf.client.renderer.entity;

import com.linngdu664.bsf.client.renderer.entity.layers.BSFSnowGolemHoldItemLayer;
import com.linngdu664.bsf.client.model.BSFSnowGolemModel;
import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class BSFSnowGolemRenderer extends MobRenderer<BSFSnowGolemEntity, BSFSnowGolemModel<BSFSnowGolemEntity>> {
    public BSFSnowGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new BSFSnowGolemModel<>(context.bakeLayer(BSFSnowGolemModel.LAYER_LOCATION)), 0.7f);
        this.addLayer(new BSFSnowGolemHoldItemLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BSFSnowGolemEntity pEntity) {
        switch (pEntity.getStyle()) {
            case 0 -> {
                return new ResourceLocation("bsf:textures/models/bsf_snow_golem_1.png");
            }
            case 1 -> {
                return new ResourceLocation("bsf:textures/models/bsf_snow_golem_2.png");
            }
            case 2 -> {
                return new ResourceLocation("bsf:textures/models/bsf_snow_golem_3.png");
            }
            case 3 -> {
                return new ResourceLocation("bsf:textures/models/bsf_snow_golem_4.png");
            }
            case 4 -> {
                return new ResourceLocation("bsf:textures/models/bsf_snow_golem_5.png");
            }
            case 5 -> {
                return new ResourceLocation("bsf:textures/models/bsf_snow_golem_6.png");
            }
            case 6 -> {
                return new ResourceLocation("bsf:textures/models/bsf_snow_golem_7.png");
            }
            case 7 -> {
                return new ResourceLocation("bsf:textures/models/bsf_snow_golem_8.png");
            }
            default -> {
                return new ResourceLocation("bsf:textures/models/bsf_snow_golem_9.png");
            }
        }
    }
}
