package com.linngdu664.bsf.event;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.client.model.*;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import com.linngdu664.bsf.item.snowball.force.MonsterGravitySnowballItem;
import com.linngdu664.bsf.item.snowball.force.MonsterRepulsionSnowballItem;
import com.linngdu664.bsf.item.snowball.force.ProjectileGravitySnowballItem;
import com.linngdu664.bsf.item.snowball.force.ProjectileRepulsionSnowballItem;
import com.linngdu664.bsf.item.snowball.normal.*;
import com.linngdu664.bsf.item.snowball.special.*;
import com.linngdu664.bsf.item.snowball.tracking.*;
import com.linngdu664.bsf.item.tool.ColdCompressionJetEngineItem;
import com.linngdu664.bsf.registry.ItemRegister;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    public static final KeyMapping CYCLE_MOVE_AMMO_NEXT = new KeyMapping("key.bsf.ammo_switch_next", GLFW.GLFW_KEY_H, "key.categories.misc");
    public static final KeyMapping CYCLE_MOVE_AMMO_PREV = new KeyMapping("key.bsf.ammo_switch_prev", GLFW.GLFW_KEY_G, "key.categories.misc");

    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        event.register(CYCLE_MOVE_AMMO_NEXT);
        event.register(CYCLE_MOVE_AMMO_PREV);
    }

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(ItemRegister.COLD_COMPRESSION_JET_ENGINE.get(),
                    new ResourceLocation("sc_xxx"), (itemStack, world, livingEntity, num) -> ((float) itemStack.getMaxDamage() - itemStack.getDamageValue()) / itemStack.getMaxDamage());
            ItemProperties.register(ItemRegister.COLD_COMPRESSION_JET_ENGINE.get(),
                    new ResourceLocation("sc_starting"), (itemStack, world, livingEntity, num) -> {
                        if (livingEntity == null || livingEntity.getUseItem() != itemStack) {
                            return 0.0F;
                        } else {
                            float pct = (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / ColdCompressionJetEngineItem.STARTUP_DURATION;
                            return pct > 1.4f ? 2.0f : pct;
                        }
                    });
            ItemProperties.register(ItemRegister.IMPLOSION_SNOWBALL_CANNON.get(),
                    new ResourceLocation("cooling"), (itemStack, world, livingEntity, num) -> {
                        if (livingEntity instanceof Player player) {
                            return player.getCooldowns().getCooldownPercent(itemStack.getItem(), 1);
                        }else{
                            return 0;
                        }
                    });
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
            ItemProperties.register(ItemRegister.LARGE_SNOWBALL_TANK.get(), new ResourceLocation("snowball"), (itemStack, world, livingEntity, num) -> {
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Main.MODID, itemStack.getOrCreateTag().getString("Snowball")));
                if (item instanceof AbstractBSFSnowballItem snowballItem) {
                    return snowballItem.getIdForTank();
                }
                return -1;
            });
            ItemProperties.register(ItemRegister.SNOWBALL_TANK.get(), new ResourceLocation("snowball"), (itemStack, world, livingEntity, num) -> {
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Main.MODID, itemStack.getOrCreateTag().getString("Snowball")));
                if (item instanceof AbstractBSFSnowballItem snowballItem) {
                    return snowballItem.getIdForTank();
                }
                return -1;
            });
            ItemProperties.register(ItemRegister.SNOW_GOLEM_CONTAINER.get(), new ResourceLocation("has_golem"), (itemStack, world, livingEntity, num) -> itemStack.getOrCreateTag().getBoolean("HasGolem") ? 1.0F : 0.0F);
            ItemProperties.register(ItemRegister.BASIN.get(), new ResourceLocation("snow_type"), (itemStack, world, livingEntity, num) -> (itemStack.getOrCreateTag().getByte("SnowType")));
        });
    }
    // current max id for tank is 31 (icicle snowball)

//    private static float getSnowballId(Item item) {
//        if (item instanceof CompactedSnowballItem) return 0;
//        if (item instanceof CherryBlossomSnowballItem) return 1;
//        if (item instanceof StoneSnowballItem) return 2;
//        if (item instanceof GlassSnowballItem) return 3;
//        if (item instanceof IceSnowballItem) return 4;
//        if (item instanceof IronSnowballItem) return 5;
//        if (item instanceof GoldSnowballItem) return 6;
//        if (item instanceof ObsidianSnowballItem) return 7;
//        if (item instanceof ExplosiveSnowballItem) return 8;
//        if (item instanceof SpectralSnowballItem) return 9;
//        if (item instanceof PowderSnowballItem) return 10;
//        if (item instanceof FrozenSnowballItem) return 11;
//        if (item instanceof CriticalFrozenSnowballItem) return 12;
//        if (item instanceof EnderSnowballItem) return 13;
//        if (item instanceof ThrustSnowballItem) return 14;
//        if (item instanceof SubspaceSnowballItem) return 15;
//        if (item instanceof GhostSnowballItem) return 16;
//        if (item instanceof ExpansionSnowballItem) return 17;
//        if (item instanceof ReconstructSnowballItem) return 18;
//        if (item instanceof ImpulseSnowballItem) return 19;
//        if (item instanceof BlackHoleSnowballItem) return 20;
//        if (item instanceof MonsterGravitySnowballItem) return 21;
//        if (item instanceof MonsterRepulsionSnowballItem) return 22;
//        if (item instanceof ProjectileGravitySnowballItem) return 23;
//        if (item instanceof ProjectileRepulsionSnowballItem) return 24;
//        if (item instanceof LightMonsterTrackingSnowballItem) return 25;
//        if (item instanceof HeavyMonsterTrackingSnowballItem) return 26;
//        if (item instanceof ExplosiveMonsterTrackingSnowballItem) return 27;
//        if (item instanceof LightPlayerTrackingSnowballItem) return 28;
//        if (item instanceof HeavyPlayerTrackingSnowballItem) return 29;
//        if (item instanceof ExplosivePlayerTrackingSnowballItem) return 30;
//        if (item instanceof IcicleSnowballItem) return 31;
//        return -1;
//    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(IceSkatesModel.LAYER_LOCATION, IceSkatesModel::createBodyLayer);
        event.registerLayerDefinition(SnowFallBootsModel.LAYER_LOCATION, SnowFallBootsModel::createBodyLayer);
        event.registerLayerDefinition(BSFSnowGolemModel.LAYER_LOCATION, BSFSnowGolemModel::createBodyLayer);
        event.registerLayerDefinition(FixedForceExecutorModel.LAYER_LOCATION1, FixedForceExecutorModel::createBodyLayer);
        event.registerLayerDefinition(FixedForceExecutorModel.LAYER_LOCATION2, FixedForceExecutorModel::createBodyLayer);
        event.registerLayerDefinition(FixedForceExecutorModel.LAYER_LOCATION3, FixedForceExecutorModel::createBodyLayer);
        event.registerLayerDefinition(FixedForceExecutorModel.LAYER_LOCATION4, FixedForceExecutorModel::createBodyLayer);
        event.registerLayerDefinition(BlackHoleExecutorCModel.LAYER_LOCATION, BlackHoleExecutorCModel::createBodyLayer);
    }
}
