package com.linngdu664.bsf.entity;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.entity.snowball.force.MonsterGravitySnowballEntity;
import com.linngdu664.bsf.entity.snowball.force.MonsterRepulsionSnowballEntity;
import com.linngdu664.bsf.entity.snowball.force.ProjectileGravitySnowballEntity;
import com.linngdu664.bsf.entity.snowball.force.ProjectileRepulsionSnowballEntity;
import com.linngdu664.bsf.entity.snowball.nomal.*;
import com.linngdu664.bsf.entity.snowball.special.*;
import com.linngdu664.bsf.entity.snowball.tracking.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegister {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Main.MODID);

    public static final RegistryObject<EntityType<BSFSnowGolemEntity>> BSF_SNOW_GOLEM =
            ENTITY_TYPES.register("bsf_snow_golem", () -> EntityType.Builder.of(BSFSnowGolemEntity::new, MobCategory.MISC)
                    .sized(0.7F, 1.9F).clientTrackingRange(8).immuneTo(Blocks.POWDER_SNOW)
                    .build(new ResourceLocation(Main.MODID, "bsf_snow_golem").toString()));
//    public static final RegistryObject<EntityType<BSFSnowballEntity>> BSF_SNOWBALL =
//            ENTITY_TYPES.register("bsf_snowball", () -> EntityType.Builder.<BSFSnowballEntity>of(BSFSnowballEntity::new, MobCategory.MISC)
//                    .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
//                    .build(new ResourceLocation(Main.MODID, "bsf_snowball").toString()));

    public static final RegistryObject<EntityType<SmoothSnowballEntity>> SMOOTH_SNOWBALL = registerTool(SmoothSnowballEntity::new,"smooth_snowball");
    public static final RegistryObject<EntityType<CompactedSnowballEntity>> COMPACTED_SNOWBALL = registerTool(CompactedSnowballEntity::new,"compacted_snowball");
//    public static final RegistryObject<EntityType<CompactedSnowballEntity>> COMPACTED_SNOWBALL =
//            ENTITY_TYPES.register("compacted_snowball", () -> EntityType.Builder.<CompactedSnowballEntity>of(CompactedSnowballEntity::new, MobCategory.MISC)
//                    .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
//                    .build(new ResourceLocation(Main.MODID, "compacted_snowball").toString()));

    public static final RegistryObject<EntityType<GlassSnowballEntity>> GLASS_SNOWBALL = registerTool(GlassSnowballEntity::new,"glass_snowball");
    public static final RegistryObject<EntityType<StoneSnowballEntity>> STONE_SNOWBALL = registerTool(StoneSnowballEntity::new,"stone_snowball");
    public static final RegistryObject<EntityType<IceSnowballEntity>> ICE_SNOWBALL = registerTool(IceSnowballEntity::new,"ice_snowball");
    public static final RegistryObject<EntityType<IronSnowballEntity>> IRON_SNOWBALL = registerTool(IronSnowballEntity::new,"iron_snowball");
    public static final RegistryObject<EntityType<GoldSnowballEntity>> GOLD_SNOWBALL = registerTool(GoldSnowballEntity::new,"gold_snowball");
    public static final RegistryObject<EntityType<ObsidianSnowballEntity>> OBSIDIAN_SNOWBALL = registerTool(ObsidianSnowballEntity::new,"obsidian_snowball");
    public static final RegistryObject<EntityType<ExplosiveSnowballEntity>> EXPLOSIVE_SNOWBALL = registerTool(ExplosiveSnowballEntity::new,"explosive_snowball");
    public static final RegistryObject<EntityType<SpectralSnowballEntity>> SPECTRAL_SNOWBALL = registerTool(SpectralSnowballEntity::new,"spectral_snowball");
    public static final RegistryObject<EntityType<FrozenSnowballEntity>> FROZEN_SNOWBALL = registerTool(FrozenSnowballEntity::new,"frozen_snowball");
    public static final RegistryObject<EntityType<PowderSnowballEntity>> POWDER_SNOWBALL = registerTool(PowderSnowballEntity::new,"powder_snowball");
    public static final RegistryObject<EntityType<LightMonsterTrackingSnowballEntity>> LIGHT_MONSTER_TRACKING_SNOWBALL = registerTool(LightMonsterTrackingSnowballEntity::new,"light_monster_tracking_snowball");
    public static final RegistryObject<EntityType<HeavyMonsterTrackingSnowballEntity>> HEAVY_MONSTER_TRACKING_SNOWBALL = registerTool(HeavyMonsterTrackingSnowballEntity::new,"heavy_monster_tracking_snowball");
    public static final RegistryObject<EntityType<ExplosiveMonsterTrackingSnowballEntity>> EXPLOSIVE_MONSTER_TRACKING_SNOWBALL = registerTool(ExplosiveMonsterTrackingSnowballEntity::new,"explosive_monster_tracking_snowball");
    public static final RegistryObject<EntityType<LightPlayerTrackingSnowballEntity>> LIGHT_PLAYER_TRACKING_SNOWBALL = registerTool(LightPlayerTrackingSnowballEntity::new,"light_player_tracking_snowball");
    public static final RegistryObject<EntityType<HeavyPlayerTrackingSnowballEntity>> HEAVY_PLAYER_TRACKING_SNOWBALL = registerTool(HeavyPlayerTrackingSnowballEntity::new,"heavy_player_tracking_snowball");
    public static final RegistryObject<EntityType<ExplosivePlayerTrackingSnowballEntity>> EXPLOSIVE_PLAYER_TRACKING_SNOWBALL = registerTool(ExplosivePlayerTrackingSnowballEntity::new,"explosive_player_tracking_snowball");
    public static final RegistryObject<EntityType<MonsterGravitySnowballEntity>> MONSTER_GRAVITY_SNOWBALL = registerTool(MonsterGravitySnowballEntity::new,"monster_gravity_snowball");//name is changed
    public static final RegistryObject<EntityType<ProjectileGravitySnowballEntity>> PROJECTILE_GRAVITY_SNOWBALL = registerTool(ProjectileGravitySnowballEntity::new,"projectile_gravity_snowball");//name is changed
    public static final RegistryObject<EntityType<MonsterRepulsionSnowballEntity>> MONSTER_REPULSION_SNOWBALL = registerTool(MonsterRepulsionSnowballEntity::new,"monster_repulsion_snowball");//name is changed
    public static final RegistryObject<EntityType<ProjectileRepulsionSnowballEntity>> PROJECTILE_REPULSION_SNOWBALL = registerTool(ProjectileRepulsionSnowballEntity::new,"projectile_repulsion_snowball");//name is changed
    public static final RegistryObject<EntityType<BlackHoleSnowballEntity>> BLACK_HOLE_SNOWBALL = registerTool(BlackHoleSnowballEntity::new,"black_hole_snowball");
    public static final RegistryObject<EntityType<SubspaceSnowballEntity>> SUBSPACE_SNOWBALL = registerTool(SubspaceSnowballEntity::new,"subspace_snowball");
    public static final RegistryObject<EntityType<EnderSnowballEntity>> ENDER_SNOWBALL = registerTool(EnderSnowballEntity::new,"ender_snowball");
    public static final RegistryObject<EntityType<GPSSnowballEntity>> GPS_SNOWBALL = registerTool(GPSSnowballEntity::new,"gps_snowball");

//    public static final RegistryObject<EntityType<ExplosiveSnowballEntity>> EXPLOSIVE_SNOWBALL =
//            ENTITY_TYPES.register("explosive_snowball", () -> EntityType.Builder.<ExplosiveSnowballEntity>of(ExplosiveSnowballEntity::new, MobCategory.MISC)
//                    .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
//                    .build(new ResourceLocation(Main.MODID, "explosive_snowball").toString()));

    /**
     * A tool to register snowball entity
     */
    public static <T extends Entity> RegistryObject<EntityType<T>> registerTool(EntityType.EntityFactory<T> pFactory, String name){
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.of(pFactory, MobCategory.MISC)
                .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
                .build(new ResourceLocation(Main.MODID, name).toString()));
    }
}
