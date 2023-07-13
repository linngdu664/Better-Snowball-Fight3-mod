package com.linngdu664.bsf.item.weapon;

import com.linngdu664.bsf.entity.BSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.nomal.IceSnowballEntity;
import com.linngdu664.bsf.entity.snowball.special.FrozenSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.LaunchFrom;
import com.linngdu664.bsf.util.LaunchFunc;
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

public class FreezingSnowballCannonItem extends SnowballCannonItem {
    @Override
    public LaunchFunc getLaunchFunc(double damageDropRate) {
        return new LaunchFunc() {
            @Override
            public LaunchFrom getLaunchFrom() {
                return LaunchFrom.FREEZING_CANNON;
            }

            @Override
            public void launchProperties(BSFSnowballEntity bsfSnowballEntity) {
                if (bsfSnowballEntity instanceof IceSnowballEntity || bsfSnowballEntity instanceof FrozenSnowballEntity) {
                    bsfSnowballEntity.setFrozenTicks(200).setBlazeDamage(bsfSnowballEntity.getBlazeDamage() + 4);
                } else {
                    bsfSnowballEntity.setFrozenTicks(140).setBlazeDamage(bsfSnowballEntity.getBlazeDamage() + 1);
                }
                bsfSnowballEntity.setBlazeDamage(bsfSnowballEntity.getBlazeDamage() * (float) damageDropRate).setPunch(damageDropRate * 1.51);
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
