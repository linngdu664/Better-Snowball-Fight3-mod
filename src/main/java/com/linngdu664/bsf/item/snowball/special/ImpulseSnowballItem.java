package com.linngdu664.bsf.item.snowball.special;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.special.ImpulseSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import com.linngdu664.bsf.item.weapon.SnowballCannonItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ImpulseSnowballItem extends AbstractBSFSnowballItem {
    public ImpulseSnowballItem() {
        super(Rarity.RARE, new SnowballProperties().idForTank(19).allowLaunchTypeFlag(AbstractBSFSnowballItem.HAND_TYPE_FLAG | SnowballCannonItem.TYPE_FLAG));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        return throwOrStorage(pPlayer, pLevel, pUsedHand, 1.25F, 15);
    }

    @Override
    public AbstractBSFSnowballEntity getCorrespondingEntity(Level level, LivingEntity livingEntity, ILaunchAdjustment launchAdjustment) {
        return new ImpulseSnowballEntity(livingEntity, level, launchAdjustment);
    }

//    @Override
//    public int getTypeFlag() {
//        return AbstractBSFSnowballItem.HAND_TYPE_FLAG | SnowballCannonItem.TYPE_FLAG;
//    }

    @Override
    public void addLastTips(List<Component> pTooltipComponents) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("impulse_snowball.tooltip", null, new Object[0])).withStyle(ChatFormatting.GRAY));
    }
}
