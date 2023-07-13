package com.linngdu664.bsf.item.weapon;

import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.entity.snowball.util.LaunchFrom;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PowerfulSnowballCannonItem extends SnowballCannonItem {
//    @Override
//    public LaunchFunc getLaunchFunc(double damageDropRate) {
//        return new LaunchFunc() {
//            @Override
//            public LaunchFrom getLaunchFrom() {
//                return LaunchFrom.POWERFUL_CANNON;
//            }
//
//            @Override
//            public void launchProperties(BSFSnowballEntity bsfSnowballEntity) {
//                bsfSnowballEntity.setWeaknessTicks(180).setPunch(damageDropRate * 2.5);
//            }
//        };
//    }

    @Override
    public ILaunchAdjustment getLaunchAdjustment(double damageDropRate, Item snowball) {
        return new ILaunchAdjustment() {
            @Override
            public double adjustPunch(double punch) {
                return punch + damageDropRate * 2.5;
            }

            @Override
            public int adjustWeaknessTicks(int weaknessTicks) {
                return weaknessTicks + 180;
            }

            @Override
            public int adjustFrozenTicks(int frozenTicks) {
                return frozenTicks;
            }

            @Override
            public float adjustDamage(float damage) {
                return damage;
            }

            @Override
            public float adjustBlazeDamage(float blazeDamage) {
                return blazeDamage;
            }

            @Override
            public LaunchFrom getLaunchFrom() {
                return LaunchFrom.POWERFUL_CANNON;
            }
        };
    }

    @Override
    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving, int pTimeLeft) {
        launch(pStack, pLevel, pEntityLiving, pTimeLeft, 4.0F);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_cannon5.tooltip", null, new Object[0])).withStyle(ChatFormatting.RED));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_cannon2.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_cannon3.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_cannon.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_AQUA));
    }
}
