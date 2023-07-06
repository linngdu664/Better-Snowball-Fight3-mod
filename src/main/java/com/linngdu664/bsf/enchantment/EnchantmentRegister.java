package com.linngdu664.bsf.enchantment;

import com.linngdu664.bsf.Main;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentRegister {
    public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Main.MODID);
    public static final RegistryObject<Enchantment> KINETIC_ENERGY_STORAGE = REGISTRY.register("kinetic_energy_storage", KineticEnergyStorageEnchantment::new);
    public static final RegistryObject<Enchantment> SNOW_GOLEM_EXCLUSIVE = REGISTRY.register("snow_golem_exclusive", SnowGolemExclusiveEnchantment::new);
}
