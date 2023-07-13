package com.linngdu664.bsf.entity;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.entity.snowball.nomal.CompactedSnowballEntity;
import com.linngdu664.bsf.entity.snowball.nomal.ExplosiveSnowballEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
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
    public static final RegistryObject<EntityType<BSFSnowballEntity>> BSF_SNOWBALL =
            ENTITY_TYPES.register("bsf_snowball", () -> EntityType.Builder.<BSFSnowballEntity>of(BSFSnowballEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
                    .build(new ResourceLocation(Main.MODID, "bsf_snowball").toString()));
    public static final RegistryObject<EntityType<CompactedSnowballEntity>> COMPACTED_SNOWBALL =
            ENTITY_TYPES.register("compacted_snowball", () -> EntityType.Builder.<CompactedSnowballEntity>of(CompactedSnowballEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
                    .build(new ResourceLocation(Main.MODID, "compacted_snowball").toString()));
    public static final RegistryObject<EntityType<ExplosiveSnowballEntity>> EXPLOSIVE_SNOWBALL =
            ENTITY_TYPES.register("explosive_snowball", () -> EntityType.Builder.<ExplosiveSnowballEntity>of(ExplosiveSnowballEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
                    .build(new ResourceLocation(Main.MODID, "explosive_snowball").toString()));
}
