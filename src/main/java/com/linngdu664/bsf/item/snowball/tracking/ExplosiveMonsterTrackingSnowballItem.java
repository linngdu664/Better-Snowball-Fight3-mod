package com.linngdu664.bsf.item.snowball.tracking;

import com.linngdu664.bsf.entity.BSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.tracking.ExplosiveMonsterTrackingSnowballEntity;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import com.linngdu664.bsf.util.LaunchFunc;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExplosiveMonsterTrackingSnowballItem extends AbstractBSFSnowballItem {
    public ExplosiveMonsterTrackingSnowballItem() {
        super(Rarity.RARE);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer.isShiftKeyDown()) {
            ItemStack newStack = new ItemStack(ItemRegister.EXPLOSIVE_PLAYER_TRACKING_SNOWBALL.get(), itemStack.getCount());
            pPlayer.setItemInHand(pUsedHand, newStack);
        } else {
            storageInTank(pPlayer, itemStack, ItemRegister.EXPLOSIVE_MONSTER_TRACKING_SNOWBALL_TANK.get());
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }

    @Override
    public BSFSnowballEntity getCorrespondingEntity(Level level, LivingEntity livingEntity, LaunchFunc launchFunc) {
        return new ExplosiveMonsterTrackingSnowballEntity(livingEntity, level, launchFunc);
    }

    @Override
    public float getRecoil() {
        return 0.12F;
    }

    @Override
    public double getPushRank() {
        return 0.42;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_no_hand.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_cannon.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_machine_gun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_shotgun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("can_change.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("EMT_snowball.tooltip", null, new Object[0])).withStyle(ChatFormatting.GOLD));
    }
}
