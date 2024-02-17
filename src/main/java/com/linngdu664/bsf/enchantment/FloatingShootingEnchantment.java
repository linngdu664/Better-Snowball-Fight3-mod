package com.linngdu664.bsf.enchantment;

import com.linngdu664.bsf.item.weapon.SnowballCannonItem;
import com.linngdu664.bsf.item.weapon.SnowballMachineGunItem;
import com.linngdu664.bsf.item.weapon.SnowmanInHandItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FloatingShootingEnchantment extends Enchantment {
    public FloatingShootingEnchantment(EquipmentSlot... slots) {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.WEAPON, slots);
    }

    @Override
    public int getMinCost(int pLevel) {
        return 31;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return 36;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof SnowballCannonItem || item instanceof SnowballMachineGunItem || item instanceof SnowmanInHandItem || item instanceof BowItem;
    }
}
