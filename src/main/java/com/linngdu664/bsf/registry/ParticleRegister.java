package com.linngdu664.bsf.registry;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.particle.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.SonicBoomParticle;
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
    public static final RegistryObject<SimpleParticleType> GENERATOR_FIX = PARTICLES.register("generator_fix", (Lazy<SimpleParticleType>) () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> GENERATOR_PUSH = PARTICLES.register("generator_push", (Lazy<SimpleParticleType>) () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> MONSTER_GRAVITY_EXECUTOR_ASH = PARTICLES.register("monster_gravity_executor_ash", (Lazy<SimpleParticleType>) () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> MONSTER_REPULSION_EXECUTOR_ASH = PARTICLES.register("monster_repulsion_executor_ash", (Lazy<SimpleParticleType>) () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> PROJECTILE_GRAVITY_EXECUTOR_ASH = PARTICLES.register("projectile_gravity_executor_ash", (Lazy<SimpleParticleType>) () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> PROJECTILE_REPULSION_EXECUTOR_ASH = PARTICLES.register("projectile_repulsion_executor_ash", (Lazy<SimpleParticleType>) () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> VECTOR_INVERSION_RED = PARTICLES.register("vector_inversion_red", (Lazy<SimpleParticleType>) () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> VECTOR_INVERSION_PURPLE = PARTICLES.register("vector_inversion_purple", (Lazy<SimpleParticleType>) () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SUBSPACE_SNOWBALL_HIT_PARTICLE = PARTICLES.register("subspace_snowball_hit_particle", (Lazy<SimpleParticleType>) () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SUBSPACE_SNOWBALL_ATTACK_TRACE = PARTICLES.register("subspace_snowball_attack_trace", (Lazy<SimpleParticleType>) () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SUBSPACE_SNOWBALL_RELEASE_TRACE = PARTICLES.register("subspace_snowball_release_trace", (Lazy<SimpleParticleType>) () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> IMPLOSION_SNOWBALL_CANNON = PARTICLES.register("implosion_snowball_cannon", (Lazy<SimpleParticleType>) () -> new SimpleParticleType(false));

    @SubscribeEvent
    public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
        ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;
        particleEngine.register(ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), ShortTimeSnowflake.Provider::new);
        particleEngine.register(ParticleRegister.BIG_LONG_TIME_SNOWFLAKE.get(), BigLongTimeSnowflake.Provider::new);
        particleEngine.register(ParticleRegister.IMPULSE.get(), SonicBoomParticle.Provider::new);
        particleEngine.register(ParticleRegister.GENERATOR_FIX.get(), GeneratorFix.Provider::new);
        particleEngine.register(ParticleRegister.GENERATOR_PUSH.get(), GeneratorPush.Provider::new);
        particleEngine.register(ParticleRegister.MONSTER_GRAVITY_EXECUTOR_ASH.get(), MonsterGravityExecutorAsh.Provider::new);
        particleEngine.register(ParticleRegister.MONSTER_REPULSION_EXECUTOR_ASH.get(), MonsterRepulsionExecutorAsh.Provider::new);
        particleEngine.register(ParticleRegister.PROJECTILE_GRAVITY_EXECUTOR_ASH.get(), ProjectileGravityExecutorAsh.Provider::new);
        particleEngine.register(ParticleRegister.PROJECTILE_REPULSION_EXECUTOR_ASH.get(), ProjectileRepulsionExecutorAsh.Provider::new);
        particleEngine.register(ParticleRegister.VECTOR_INVERSION_RED.get(), VectorInversionParticle.ProviderRed::new);
        particleEngine.register(ParticleRegister.VECTOR_INVERSION_PURPLE.get(), VectorInversionParticle.ProviderPurple::new);
        particleEngine.register(ParticleRegister.SUBSPACE_SNOWBALL_HIT_PARTICLE.get(), SubspaceSnowballHitParticle.Provider::new);
        particleEngine.register(ParticleRegister.SUBSPACE_SNOWBALL_ATTACK_TRACE.get(), SubspaceSnowballAttackTraceParticle.Provider::new);
        particleEngine.register(ParticleRegister.SUBSPACE_SNOWBALL_RELEASE_TRACE.get(), SubspaceSnowballReleaseTraceParticle.Provider::new);
        particleEngine.register(ParticleRegister.IMPLOSION_SNOWBALL_CANNON.get(), ImplosionSnowballCannonParticle.Provider::new);
    }
}
