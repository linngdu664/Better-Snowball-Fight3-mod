package com.linngdu664.bsf.util;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;

public class BSFTiers {
    public static final ForgeTier EMERALD = new ForgeTier(3, 1561, 8.0F, 3.0F, 10, Tags.Blocks.STORAGE_BLOCKS_EMERALD, () -> Ingredient.of(Items.EMERALD));
}
