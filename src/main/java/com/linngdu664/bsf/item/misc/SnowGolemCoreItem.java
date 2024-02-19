package com.linngdu664.bsf.item.misc;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SnowGolemCoreItem extends Item {
    private final int coolDown;
    private final String[] hoverText;
    private final ChatFormatting[] chatFormats;

    public SnowGolemCoreItem(int coolDown, String[] hoverText, ChatFormatting[] chatFormats) {
        super(new Properties().rarity(Rarity.UNCOMMON));
        this.coolDown = coolDown;
        this.hoverText = hoverText;
        this.chatFormats = chatFormats;
    }

    public int getCoolDown() {
        return coolDown;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        for (int i = 0, size = hoverText.length; i < size; i++) {
            pTooltipComponents.add(new TranslatableComponent(hoverText[i]).withStyle(chatFormats[i]));
        }
    }
}
