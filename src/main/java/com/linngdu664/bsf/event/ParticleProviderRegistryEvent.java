package com.linngdu664.bsf.event;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.particle.BigLongTimeSnowflake;
import com.linngdu664.bsf.particle.Impulse;
import com.linngdu664.bsf.particle.ParticleRegister;
import com.linngdu664.bsf.particle.ShortTimeSnowflake;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleProviderRegistryEvent {
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
