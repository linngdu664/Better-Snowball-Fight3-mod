package com.linngdu664.bsf.enchantment;

import com.linngdu664.bsf.item.weapon.SnowballCannonItem;
import com.linngdu664.bsf.item.weapon.SnowballShotgunItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SnowGolemExclusiveEnchantment extends Enchantment {
    public SnowGolemExclusiveEnchantment(EquipmentSlot... slots) {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.WEAPON, slots);
    }

    @Override
    public int getMinCost(int pLevel) {
        return 1;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return 6;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof SnowballCannonItem || item instanceof SnowballShotgunItem;
    }
}
