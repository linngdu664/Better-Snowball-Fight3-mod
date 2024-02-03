package com.linngdu664.bsf.event;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.client.model.BSFSnowGolemModel;
import com.linngdu664.bsf.client.model.ForceExecutorModel;
import com.linngdu664.bsf.client.model.IceSkatesModel;
import com.linngdu664.bsf.client.model.SnowFallBootsModel;
import com.linngdu664.bsf.item.snowball.force.MonsterGravitySnowballItem;
import com.linngdu664.bsf.item.snowball.force.MonsterRepulsionSnowballItem;
import com.linngdu664.bsf.item.snowball.force.ProjectileGravitySnowballItem;
import com.linngdu664.bsf.item.snowball.force.ProjectileRepulsionSnowballItem;
import com.linngdu664.bsf.item.snowball.normal.*;
import com.linngdu664.bsf.item.snowball.special.*;
import com.linngdu664.bsf.item.snowball.tracking.*;
import com.linngdu664.bsf.registry.ItemRegister;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(ItemRegister.SNOWBALL_CANNON.get(),
                    new ResourceLocation("pull"), (itemStack, world, livingEntity, num) -> {
                        if (livingEntity == null) {
                            return 0.0F;
                        } else {
                            return livingEntity.getUseItem() != itemStack ? 0.0F : (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0F;
                        }
                    });
            ItemProperties.register(ItemRegister.SNOWBALL_CANNON.get(), new ResourceLocation("pulling"), (itemStack, world, livingEntity, num)
                    -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
            ItemProperties.register(ItemRegister.FREEZING_SNOWBALL_CANNON.get(),
                    new ResourceLocation("pull"), (itemStack, world, livingEntity, num) -> {
                        if (livingEntity == null) {
                            return 0.0F;
                        } else {
                            return livingEntity.getUseItem() != itemStack ? 0.0F : (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0F;
                        }
                    });
            ItemProperties.register(ItemRegister.FREEZING_SNOWBALL_CANNON.get(), new ResourceLocation("pulling"), (itemStack, world, livingEntity, num)
                    -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
            ItemProperties.register(ItemRegister.POWERFUL_SNOWBALL_CANNON.get(),
                    new ResourceLocation("pull"), (itemStack, world, livingEntity, num) -> {
                        if (livingEntity == null) {
                            return 0.0F;
                        } else {
                            return livingEntity.getUseItem() != itemStack ? 0.0F : (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0F;
                        }
                    });
            ItemProperties.register(ItemRegister.POWERFUL_SNOWBALL_CANNON.get(), new ResourceLocation("pulling"), (itemStack, world, livingEntity, num)
                    -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
            ItemProperties.register(ItemRegister.GLOVE.get(), new ResourceLocation("using"), (itemStack, world, livingEntity, num)
                    -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
            ItemProperties.register(ItemRegister.JEDI_GLOVE.get(), new ResourceLocation("using"), (itemStack, world, livingEntity, num)
                    -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
            ItemProperties.register(ItemRegister.LARGE_SNOWBALL_TANK.get(), new ResourceLocation("snowball"), (itemStack, world, livingEntity, num) -> getSnowballId(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Main.MODID, itemStack.getTag().getString("Snowball")))));
            ItemProperties.register(ItemRegister.SNOWBALL_TANK.get(), new ResourceLocation("snowball"), (itemStack, world, livingEntity, num) -> getSnowballId(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Main.MODID, itemStack.getTag().getString("Snowball")))));
            ItemProperties.register(ItemRegister.SNOW_GOLEM_CONTAINER.get(), new ResourceLocation("has_golem"), (itemStack, world, livingEntity, num) -> itemStack.getOrCreateTag().getBoolean("HasGolem") ? 1.0F : 0.0F);
            ItemProperties.register(ItemRegister.BASIN.get(), new ResourceLocation("snow_type"), (itemStack, world, livingEntity, num) -> (itemStack.getOrCreateTag().getByte("SnowType")));
        });
    }

    private static float getSnowballId(Item item) {
        if (item instanceof CompactedSnowballItem) return 0;
        if (item instanceof CherryBlossomSnowballItem) return 1;
        if (item instanceof StoneSnowballItem) return 2;
        if (item instanceof GlassSnowballItem) return 3;
        if (item instanceof IceSnowballItem) return 4;
        if (item instanceof IronSnowballItem) return 5;
        if (item instanceof GoldSnowballItem) return 6;
        if (item instanceof ObsidianSnowballItem) return 7;
        if (item instanceof ExplosiveSnowballItem) return 8;
        if (item instanceof SpectralSnowballItem) return 9;
        if (item instanceof PowderSnowballItem) return 10;
        if (item instanceof FrozenSnowballItem) return 11;
        if (item instanceof CriticalFrozenSnowballItem) return 12;
        if (item instanceof EnderSnowballItem) return 13;
        if (item instanceof ThrustSnowballItem) return 14;
        if (item instanceof SubspaceSnowballItem) return 15;
        if (item instanceof GhostSnowballItem) return 16;
        if (item instanceof ExpansionSnowballItem) return 17;
        if (item instanceof ReconstructSnowballItem) return 18;
        if (item instanceof ImpulseSnowballItem) return 19;
        if (item instanceof BlackHoleSnowballItem) return 20;
        if (item instanceof MonsterGravitySnowballItem) return 21;
        if (item instanceof MonsterRepulsionSnowballItem) return 22;
        if (item instanceof ProjectileGravitySnowballItem) return 23;
        if (item instanceof ProjectileRepulsionSnowballItem) return 24;
        if (item instanceof LightMonsterTrackingSnowballItem) return 25;
        if (item instanceof HeavyMonsterTrackingSnowballItem) return 26;
        if (item instanceof ExplosiveMonsterTrackingSnowballItem) return 27;
        if (item instanceof LightPlayerTrackingSnowballItem) return 28;
        if (item instanceof HeavyPlayerTrackingSnowballItem) return 29;
        if (item instanceof ExplosivePlayerTrackingSnowballItem) return 30;
        if (item instanceof IcicleSnowballItem) return 31;
        return -1;
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(IceSkatesModel.LAYER_LOCATION, IceSkatesModel::createBodyLayer);
        event.registerLayerDefinition(SnowFallBootsModel.LAYER_LOCATION, SnowFallBootsModel::createBodyLayer);
        event.registerLayerDefinition(BSFSnowGolemModel.LAYER_LOCATION, BSFSnowGolemModel::createBodyLayer);
        event.registerLayerDefinition(ForceExecutorModel.LAYER_LOCATION1, ForceExecutorModel::createBodyLayer);
        event.registerLayerDefinition(ForceExecutorModel.LAYER_LOCATION2, ForceExecutorModel::createBodyLayer);
        event.registerLayerDefinition(ForceExecutorModel.LAYER_LOCATION3, ForceExecutorModel::createBodyLayer);
        event.registerLayerDefinition(ForceExecutorModel.LAYER_LOCATION4, ForceExecutorModel::createBodyLayer);
    }
}
