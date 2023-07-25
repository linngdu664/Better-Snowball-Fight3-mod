package com.linngdu664.bsf.enchantment;

import com.linngdu664.bsf.registry.ItemRegister;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class KineticEnergyStorageEnchantment extends Enchantment {
    public KineticEnergyStorageEnchantment(EquipmentSlot... slots) {
        super(Rarity.COMMON, EnchantmentCategory.ARMOR_FEET, slots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int pLevel) {
        return pLevel * 10 - 5;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 10;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        Item item = stack.getItem();
        return item == ItemRegister.SNOW_FALL_BOOTS.get();
    }
}
