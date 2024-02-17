package com.linngdu664.bsf.item.block;

import com.linngdu664.bsf.registry.BlockRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SmartSnowBlockItem extends BlockItem {
    public SmartSnowBlockItem() {
        super(BlockRegister.SMART_SNOW_BLOCK.get(), new Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block0.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block1.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block2.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block3.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block4.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block5.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block6.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block7.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_AQUA));
    }
}
