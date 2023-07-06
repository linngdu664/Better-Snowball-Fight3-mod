package com.linngdu664.bsf.item.tool;

import com.linngdu664.bsf.util.ItemGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public abstract class AbstractBSFEnhanceableToolItem extends Item {
    public AbstractBSFEnhanceableToolItem(Rarity rarity, int durability) {
        super(new Properties().stacksTo(1).tab(ItemGroup.MAIN).rarity(rarity).durability(durability));
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getEnchantmentValue() {
        return 1;
    }
}
