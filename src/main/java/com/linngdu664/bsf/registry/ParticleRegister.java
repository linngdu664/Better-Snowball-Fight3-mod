package com.linngdu664.bsf.registry;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.particle.BigLongTimeSnowflake;
import com.linngdu664.bsf.particle.Impulse;
import com.linngdu664.bsf.particle.ShortTimeSnowflake;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleRegister {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "bsf");
    public static final RegistryObject<SimpleParticleType> SHORT_TIME_SNOWFLAKE = PARTICLES.register("short_time_snowflake", (Lazy<SimpleParticleType>) () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> BIG_LONG_TIME_SNOWFLAKE = PARTICLES.register("big_long_time_snowflake", (Lazy<SimpleParticleType>) () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> IMPULSE = PARTICLES.register("impulse", (Lazy<SimpleParticleType>) () -> new SimpleParticleType(false));

    @SubscribeEvent
    public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
//        event.register(ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), ShortTimeSnowflake.Provider::new);
//        event.register(ParticleRegister.BIG_LONG_TIME_SNOWFLAKE.get(), BigLongTimeSnowflake.Provider::new);
        ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;
        particleEngine.register(ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), ShortTimeSnowflake.Provider::new);
        particleEngine.register(ParticleRegister.BIG_LONG_TIME_SNOWFLAKE.get(), BigLongTimeSnowflake.Provider::new);
        particleEngine.register(ParticleRegister.IMPULSE.get(), Impulse.Provider::new);
    }
}
