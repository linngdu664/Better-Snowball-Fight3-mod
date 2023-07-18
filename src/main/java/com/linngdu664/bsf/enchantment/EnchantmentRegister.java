package com.linngdu664.bsf.enchantment;

import com.linngdu664.bsf.Main;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentRegister {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Main.MODID);
    public static final RegistryObject<Enchantment> KINETIC_ENERGY_STORAGE = ENCHANTMENTS.register("kinetic_energy_storage", KineticEnergyStorageEnchantment::new);
    public static final RegistryObject<Enchantment> SNOW_GOLEM_EXCLUSIVE = ENCHANTMENTS.register("snow_golem_exclusive", SnowGolemExclusiveEnchantment::new);
}
