package com.linngdu664.bsf.item.tank.tracking;

import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.item.tank.AbstractSnowballTankItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HeavyMonsterTrackingSnowballTank extends AbstractSnowballTankItem {
    public HeavyMonsterTrackingSnowballTank() {
        super(ItemRegister.HEAVY_MONSTER_TRACKING_SNOWBALL.get());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_no_hand.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_cannon.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_machine_gun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_shotgun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("HMT_snowball.tooltip", null, new Object[0])).withStyle(ChatFormatting.GOLD));
    }
}
