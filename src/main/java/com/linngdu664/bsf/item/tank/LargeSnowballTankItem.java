package com.linngdu664.bsf.item.tank;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

public class LargeSnowballTankItem extends SnowballTankItem {
    public LargeSnowballTankItem() {
        super(new Properties().stacksTo(1).durability(192).rarity(Rarity.UNCOMMON));
    }

    @Override
    public @NotNull Component getName(ItemStack pStack) {
        CompoundTag compoundTag = pStack.getOrCreateTag();
        if (compoundTag.contains("Snowball")) {
            return new TranslatableComponent("item.bsf.large_" + compoundTag.getString("Snowball") + "_tank");
        }
        return super.getName(pStack);
    }
}
