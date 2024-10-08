package com.linngdu664.bsf;

import com.linngdu664.bsf.config.ClientConfig;
import com.linngdu664.bsf.config.ServerConfig;
import com.linngdu664.bsf.registry.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.MODID)
public class Main {
    public static final String MODID = "bsf";

    public Main() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        BlockRegister.BLOCKS.register(bus);
        BlockEntityRegister.BLOCK_ENTITIES.register(bus);
        ItemRegister.ITEMS.register(bus);
        SoundRegister.SOUNDS.register(bus);
        ParticleRegister.PARTICLES.register(bus);
        EffectRegister.EFFECTS.register(bus);
        EnchantmentRegister.ENCHANTMENTS.register(bus);
        EntityRegister.ENTITY_TYPES.register(bus);
        CreativeTabRegister.CREATIVE_TABS.register(bus);
    }
}
