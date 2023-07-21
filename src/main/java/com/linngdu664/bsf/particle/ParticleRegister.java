package com.linngdu664.bsf.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleRegister {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "bsf");
    public static final RegistryObject<SimpleParticleType> SHORT_TIME_SNOWFLAKE = PARTICLES.register("short_time_snowflake", (Lazy<SimpleParticleType>) () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> BIG_LONG_TIME_SNOWFLAKE = PARTICLES.register("big_long_time_snowflake", (Lazy<SimpleParticleType>) () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> IMPULSE = PARTICLES.register("impulse", (Lazy<SimpleParticleType>) () -> new SimpleParticleType(false));
}
