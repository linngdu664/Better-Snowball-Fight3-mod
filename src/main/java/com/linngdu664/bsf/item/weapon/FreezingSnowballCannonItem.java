package com.linngdu664.bsf.item.weapon;

import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.entity.snowball.util.LaunchFrom;
import com.linngdu664.bsf.item.snowball.normal.IceSnowballItem;
import com.linngdu664.bsf.item.snowball.special.FrozenSnowballItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FreezingSnowballCannonItem extends SnowballCannonItem {
    public FreezingSnowballCannonItem() {
        super(Rarity.RARE);
    }

    @Override
    public ILaunchAdjustment getLaunchAdjustment(double damageDropRate, Item snowball) {
        return new ILaunchAdjustment() {
            @Override
            public double adjustPunch(double punch) {
                return punch + damageDropRate * 1.51;
            }

            @Override
            public int adjustWeaknessTicks(int weaknessTicks) {
                return weaknessTicks;
            }

            @Override
            public int adjustFrozenTicks(int frozenTicks) {
                return frozenTicks + 140;
            }

            @Override
            public float adjustDamage(float damage) {
                return damage;
            }

            @Override
            public float adjustBlazeDamage(float blazeDamage) {
                if (snowball instanceof IceSnowballItem || snowball instanceof FrozenSnowballItem) {
                    return blazeDamage + 4;
                }
                return blazeDamage + 1;
            }

            @Override
            public LaunchFrom getLaunchFrom() {
                return LaunchFrom.FREEZING_CANNON;
            }
        };
    }


    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_cannon4.tooltip", null, new Object[0])).withStyle(ChatFormatting.AQUA));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_cannon2.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_cannon3.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_cannon.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_AQUA));
    }
}
