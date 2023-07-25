package com.linngdu664.bsf.registry;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.client.renderer.entity.BSFSnowGolemRenderer;
import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.entity.snowball.force.MonsterGravitySnowballEntity;
import com.linngdu664.bsf.entity.snowball.force.MonsterRepulsionSnowballEntity;
import com.linngdu664.bsf.entity.snowball.force.ProjectileGravitySnowballEntity;
import com.linngdu664.bsf.entity.snowball.force.ProjectileRepulsionSnowballEntity;
import com.linngdu664.bsf.entity.snowball.nomal.*;
import com.linngdu664.bsf.entity.snowball.special.*;
import com.linngdu664.bsf.entity.snowball.tracking.*;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegister {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Main.MODID);

    public static final RegistryObject<EntityType<BSFSnowGolemEntity>> BSF_SNOW_GOLEM =
            ENTITY_TYPES.register("bsf_snow_golem", () -> EntityType.Builder.of(BSFSnowGolemEntity::new, MobCategory.MISC)
                    .sized(0.7F, 1.9F).clientTrackingRange(8).immuneTo(Blocks.POWDER_SNOW)
                    .build(new ResourceLocation(Main.MODID, "bsf_snow_golem").toString()));
    public static final RegistryObject<EntityType<SmoothSnowballEntity>> SMOOTH_SNOWBALL = snowballRegister(SmoothSnowballEntity::new,"smooth_snowball");
    public static final RegistryObject<EntityType<CompactedSnowballEntity>> COMPACTED_SNOWBALL = snowballRegister(CompactedSnowballEntity::new,"compacted_snowball");
    public static final RegistryObject<EntityType<GlassSnowballEntity>> GLASS_SNOWBALL = snowballRegister(GlassSnowballEntity::new,"glass_snowball");
    public static final RegistryObject<EntityType<StoneSnowballEntity>> STONE_SNOWBALL = snowballRegister(StoneSnowballEntity::new,"stone_snowball");
    public static final RegistryObject<EntityType<IceSnowballEntity>> ICE_SNOWBALL = snowballRegister(IceSnowballEntity::new,"ice_snowball");
    public static final RegistryObject<EntityType<IronSnowballEntity>> IRON_SNOWBALL = snowballRegister(IronSnowballEntity::new,"iron_snowball");
    public static final RegistryObject<EntityType<GoldSnowballEntity>> GOLD_SNOWBALL = snowballRegister(GoldSnowballEntity::new,"gold_snowball");
    public static final RegistryObject<EntityType<ObsidianSnowballEntity>> OBSIDIAN_SNOWBALL = snowballRegister(ObsidianSnowballEntity::new,"obsidian_snowball");
    public static final RegistryObject<EntityType<ExplosiveSnowballEntity>> EXPLOSIVE_SNOWBALL = snowballRegister(ExplosiveSnowballEntity::new,"explosive_snowball");
    public static final RegistryObject<EntityType<SpectralSnowballEntity>> SPECTRAL_SNOWBALL = snowballRegister(SpectralSnowballEntity::new,"spectral_snowball");
    public static final RegistryObject<EntityType<FrozenSnowballEntity>> FROZEN_SNOWBALL = snowballRegister(FrozenSnowballEntity::new,"frozen_snowball");
    public static final RegistryObject<EntityType<PowderSnowballEntity>> POWDER_SNOWBALL = snowballRegister(PowderSnowballEntity::new,"powder_snowball");
    public static final RegistryObject<EntityType<LightMonsterTrackingSnowballEntity>> LIGHT_MONSTER_TRACKING_SNOWBALL = snowballRegister(LightMonsterTrackingSnowballEntity::new,"light_monster_tracking_snowball");
    public static final RegistryObject<EntityType<HeavyMonsterTrackingSnowballEntity>> HEAVY_MONSTER_TRACKING_SNOWBALL = snowballRegister(HeavyMonsterTrackingSnowballEntity::new,"heavy_monster_tracking_snowball");
    public static final RegistryObject<EntityType<ExplosiveMonsterTrackingSnowballEntity>> EXPLOSIVE_MONSTER_TRACKING_SNOWBALL = snowballRegister(ExplosiveMonsterTrackingSnowballEntity::new,"explosive_monster_tracking_snowball");
    public static final RegistryObject<EntityType<LightPlayerTrackingSnowballEntity>> LIGHT_PLAYER_TRACKING_SNOWBALL = snowballRegister(LightPlayerTrackingSnowballEntity::new,"light_player_tracking_snowball");
    public static final RegistryObject<EntityType<HeavyPlayerTrackingSnowballEntity>> HEAVY_PLAYER_TRACKING_SNOWBALL = snowballRegister(HeavyPlayerTrackingSnowballEntity::new,"heavy_player_tracking_snowball");
    public static final RegistryObject<EntityType<ExplosivePlayerTrackingSnowballEntity>> EXPLOSIVE_PLAYER_TRACKING_SNOWBALL = snowballRegister(ExplosivePlayerTrackingSnowballEntity::new,"explosive_player_tracking_snowball");
    public static final RegistryObject<EntityType<MonsterGravitySnowballEntity>> MONSTER_GRAVITY_SNOWBALL = snowballRegister(MonsterGravitySnowballEntity::new,"monster_gravity_snowball");//name is changed
    public static final RegistryObject<EntityType<ProjectileGravitySnowballEntity>> PROJECTILE_GRAVITY_SNOWBALL = snowballRegister(ProjectileGravitySnowballEntity::new,"projectile_gravity_snowball");//name is changed
    public static final RegistryObject<EntityType<MonsterRepulsionSnowballEntity>> MONSTER_REPULSION_SNOWBALL = snowballRegister(MonsterRepulsionSnowballEntity::new,"monster_repulsion_snowball");//name is changed
    public static final RegistryObject<EntityType<ProjectileRepulsionSnowballEntity>> PROJECTILE_REPULSION_SNOWBALL = snowballRegister(ProjectileRepulsionSnowballEntity::new,"projectile_repulsion_snowball");//name is changed
    public static final RegistryObject<EntityType<BlackHoleSnowballEntity>> BLACK_HOLE_SNOWBALL = snowballRegister(BlackHoleSnowballEntity::new,"black_hole_snowball");
    public static final RegistryObject<EntityType<SubspaceSnowballEntity>> SUBSPACE_SNOWBALL = snowballRegister(SubspaceSnowballEntity::new,"subspace_snowball");
    public static final RegistryObject<EntityType<EnderSnowballEntity>> ENDER_SNOWBALL = snowballRegister(EnderSnowballEntity::new,"ender_snowball");
    public static final RegistryObject<EntityType<GPSSnowballEntity>> GPS_SNOWBALL = snowballRegister(GPSSnowballEntity::new,"gps_snowball");
    public static final RegistryObject<EntityType<ExpansionSnowballEntity>> EXPANSION_SNOWBALL = snowballRegister(ExpansionSnowballEntity::new, "expansion_snowball");
    public static final RegistryObject<EntityType<ReconstructSnowballEntity>> RECONSTRUCT_SNOWBALL = snowballRegister(ReconstructSnowballEntity::new, "reconstruct_snowball");
    public static final RegistryObject<EntityType<IcicleSnowballEntity>> ICICLE_SNOWBALL = snowballRegister(IcicleSnowballEntity::new, "icicle_snowball");
    public static final RegistryObject<EntityType<CriticalFrozenSnowballEntity>> CRITICAL_FROZEN_SNOWBALL = snowballRegister(CriticalFrozenSnowballEntity::new, "critical_frozen_snowball");
    public static final RegistryObject<EntityType<ImpulseSnowballEntity>> IMPULSE_SNOWBALL = snowballRegister(ImpulseSnowballEntity::new, "impulse_snowball");
    public static final RegistryObject<EntityType<CherryBlossomSnowballEntity>> CHERRY_BLOSSOM_SNOWBALL = snowballRegister(CherryBlossomSnowballEntity::new, "cherry_blossom_snowball");
    public static final RegistryObject<EntityType<GhostSnowballEntity>> GHOST_SNOWBALL = snowballRegister(GhostSnowballEntity::new, "ghost_snowball");
    public static final RegistryObject<EntityType<SculkSnowballEntity>> SCULK_SNOWBALL = snowballRegister(SculkSnowballEntity::new, "sculk_snowball");
    public static final RegistryObject<EntityType<DuckSnowballEntity>> DUCK_SNOWBALL = snowballRegister(DuckSnowballEntity::new, "duck_snowball");

    /**
     * A tool to register snowball entity
     */
    public static <T extends Entity> RegistryObject<EntityType<T>> snowballRegister(EntityType.EntityFactory<T> pFactory, String name) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.of(pFactory, MobCategory.MISC)
                .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
                .build(new ResourceLocation(Main.MODID, name).toString()));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BSF_SNOW_GOLEM.get(), BSFSnowGolemRenderer::new);
        event.registerEntityRenderer(BLACK_HOLE_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(COMPACTED_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ENDER_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EXPLOSIVE_MONSTER_TRACKING_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EXPLOSIVE_PLAYER_TRACKING_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EXPLOSIVE_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(FROZEN_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(GLASS_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(GOLD_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(GPS_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(HEAVY_MONSTER_TRACKING_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(HEAVY_PLAYER_TRACKING_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ICE_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(IRON_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(LIGHT_MONSTER_TRACKING_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(LIGHT_PLAYER_TRACKING_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(MONSTER_GRAVITY_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(MONSTER_REPULSION_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(OBSIDIAN_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(POWDER_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(PROJECTILE_GRAVITY_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(PROJECTILE_REPULSION_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(SMOOTH_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(SPECTRAL_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(STONE_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(SUBSPACE_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EXPANSION_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(RECONSTRUCT_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ICICLE_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(CRITICAL_FROZEN_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(IMPULSE_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(CHERRY_BLOSSOM_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(GHOST_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(SCULK_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(DUCK_SNOWBALL.get(), ThrownItemRenderer::new);
    }

    @SubscribeEvent
    public static void createEntityAttribute(EntityAttributeCreationEvent event) {
        event.put(BSF_SNOW_GOLEM.get(), BSFSnowGolemEntity.setAttributes());
    }
}
