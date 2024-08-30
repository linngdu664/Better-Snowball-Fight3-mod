package com.linngdu664.bsf.item.block;

import com.linngdu664.bsf.registry.BlockRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
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
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class SmartSnowBlockItem extends BlockItem {
    public SmartSnowBlockItem() {
        super(BlockRegister.SMART_SNOW_BLOCK.get(), new Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        KeyMapping keySprint = Minecraft.getInstance().options.keySprint;
        if (keySprint.isDown()){
            pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block0.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
            pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block1.tooltip", null, new Object[0])).withStyle(ChatFormatting.GRAY));
            pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block2.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
            pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block3.tooltip", null, new Object[0])).withStyle(ChatFormatting.GRAY));
            pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block4.tooltip", null, new Object[0])).withStyle(ChatFormatting.GRAY));
            pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block5.tooltip", null, new Object[0])).withStyle(ChatFormatting.GRAY));
            pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block6.tooltip", null, new Object[0])).withStyle(ChatFormatting.GRAY));
            pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block7.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
            pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block8.tooltip", null, new Object[0])).withStyle(ChatFormatting.GRAY));
            pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block9.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
            pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block10.tooltip", null, new Object[0])).withStyle(ChatFormatting.GRAY));
            pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block11.tooltip", null, new Object[0])).withStyle(ChatFormatting.GRAY));
            pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block12.tooltip", null, new Object[0])).withStyle(ChatFormatting.GRAY));
            pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block13.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
            pTooltip.add(MutableComponent.create(new TranslatableContents("smart_snow_block14.tooltip", null, new Object[0])).withStyle(ChatFormatting.GRAY));
        }else{
            pTooltip.add(MutableComponent.create(new TranslatableContents("show_detail.tip", null, new Object[]{keySprint.getTranslatedKeyMessage()})).withStyle(ChatFormatting.DARK_GRAY));
        }

    }
}
