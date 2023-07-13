package com.linngdu664.bsf.item.snowball.special;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.special.EnderSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
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
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnderSnowballItem extends AbstractBSFSnowballItem {
    public EnderSnowballItem() {
        super(Rarity.UNCOMMON);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        return throwOrStorage(pPlayer, pLevel, ItemRegister.ENDER_SNOWBALL_TANK.get(), pUsedHand, 1.125F, 20);
//        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
//        if (!storageInTank(pPlayer, itemStack, ItemRegister.ENDER_SNOWBALL_TANK.get())) {
//            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
//            if (!pLevel.isClientSide) {
//                EnderSnowballEntity snowballEntity = new EnderSnowballEntity(pPlayer, pLevel, getLaunchFunc(getSnowballDamageRate(pPlayer)));
//                snowballEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.125F * getSnowballSlowdownRate(pPlayer), 1.0F);
//                pLevel.addFreshEntity(snowballEntity);
//            }
//            if (!pPlayer.getAbilities().instabuild) {
//                itemStack.shrink(1);
//                pPlayer.getCooldowns().addCooldown(this, 20);
//            }
//        }
//        pPlayer.awardStat(Stats.ITEM_USED.get(this));
//        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }

    @Override
    public AbstractBSFSnowballEntity getCorrespondingEntity(Level level, LivingEntity livingEntity, ILaunchAdjustment launchAdjustment) {
        return new EnderSnowballEntity(livingEntity, level, launchAdjustment);
    }

    @Override
    public double getPushRank() {
        return 0.12;
    }

    @Override
    public boolean canBeLaunchedByMachineGun() {
        return false;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_hand.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_cannon.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_no_machine_gun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_shotgun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("ender_snowball.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_AQUA));
    }
}
