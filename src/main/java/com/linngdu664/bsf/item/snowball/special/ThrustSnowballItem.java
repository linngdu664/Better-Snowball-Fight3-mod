package com.linngdu664.bsf.item.snowball.special;

import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import com.linngdu664.bsf.item.weapon.ImplosionSnowballCannonItem;
import com.linngdu664.bsf.item.weapon.SnowballShotgunItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ThrustSnowballItem extends AbstractBSFSnowballItem {
    public ThrustSnowballItem() {
        super(Rarity.COMMON, new SnowballProperties()
                .idForTank(14)
                .allowLaunchTypeFlag(SnowballShotgunItem.TYPE_FLAG | ImplosionSnowballCannonItem.TYPE_FLAG)
                .shotgunPushRank(0.38)
        );
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        storageInTank(pPlayer);
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }

//    @Override
//    public int getTypeFlag() {
//        return SnowballShotgunItem.TYPE_FLAG | ImplosionSnowballCannonItem.TYPE_FLAG;
//    }
//
//    @Override
//    public double getShotgunPushRank() {
//        return 0.38;
//    }

    @Override
    public void addUsageTips(List<Component> pTooltipComponents) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("thrust_snowball.tooltip", null, new Object[0])).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void addLastTips(List<Component> pTooltipComponents) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("thrust_snowball1.tooltip", null, new Object[0])).withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("thrust_snowball2.tooltip", null, new Object[0])).withStyle(ChatFormatting.GRAY));
    }
}
