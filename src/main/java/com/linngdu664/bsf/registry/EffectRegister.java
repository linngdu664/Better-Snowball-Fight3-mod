package com.linngdu664.bsf.registry;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.effect.ColdResistanceEffect;
import com.linngdu664.bsf.effect.WeaponJamEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectRegister {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Main.MODID);
    public static final RegistryObject<MobEffect> COLD_RESISTANCE = EFFECTS.register("cold_resistance", () -> new ColdResistanceEffect(MobEffectCategory.BENEFICIAL, 0x3498db));
    public static final RegistryObject<MobEffect> WEAPON_JAM = EFFECTS.register("weapon_jam", () -> new WeaponJamEffect(MobEffectCategory.HARMFUL, 0xffffff));
}
