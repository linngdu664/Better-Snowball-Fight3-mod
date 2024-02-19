package com.linngdu664.bsf.item.block;

import com.linngdu664.bsf.registry.BlockRegister;
import com.linngdu664.bsf.util.BSFCreativeModeTab;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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
        // to fking "register" our creative tab
        super(BlockRegister.SMART_SNOW_BLOCK.get(), new Properties().rarity(Rarity.UNCOMMON).tab(BSFCreativeModeTab.BSF_GROUP));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        pTooltip.add(new TranslatableComponent("smart_snow_block0.tooltip").withStyle(ChatFormatting.BLUE));
        pTooltip.add(new TranslatableComponent("smart_snow_block1.tooltip").withStyle(ChatFormatting.BLUE));
        pTooltip.add(new TranslatableComponent("smart_snow_block2.tooltip").withStyle(ChatFormatting.BLUE));
        pTooltip.add(new TranslatableComponent("smart_snow_block3.tooltip").withStyle(ChatFormatting.BLUE));
        pTooltip.add(new TranslatableComponent("smart_snow_block4.tooltip").withStyle(ChatFormatting.BLUE));
        pTooltip.add(new TranslatableComponent("smart_snow_block5.tooltip").withStyle(ChatFormatting.BLUE));
        pTooltip.add(new TranslatableComponent("smart_snow_block6.tooltip").withStyle(ChatFormatting.BLUE));
        pTooltip.add(new TranslatableComponent("smart_snow_block7.tooltip").withStyle(ChatFormatting.BLUE));
        pTooltip.add(new TranslatableComponent("smart_snow_block.tooltip").withStyle(ChatFormatting.DARK_AQUA));
    }
}
