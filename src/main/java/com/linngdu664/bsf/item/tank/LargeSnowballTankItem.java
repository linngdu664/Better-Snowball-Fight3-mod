package com.linngdu664.bsf.item.tank;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
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
        if (compoundTag.contains("snowball")) {
            return MutableComponent.create(new TranslatableContents("item.bsf.large_" + compoundTag.getString("snowball") + "_tank", null, new Object[0]));
        }
        return super.getName(pStack);
    }

//    @Override
//    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
//        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Main.MODID, pStack.getOrCreateTag().getString("snowball")));
//        if (item instanceof AbstractBSFSnowballItem snowballItem) {
//            snowballItem.generateWeaponTips(pTooltipComponents);
//            snowballItem.addLastTips(pTooltipComponents);
//        } else {
//            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_storage_tank1.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
//            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_storage_tank2.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
//            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_storage_tank3.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
//            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_storage_tank.tooltip", null, new Object[0])).withStyle(ChatFormatting.GRAY));
//        }
//    }
}
