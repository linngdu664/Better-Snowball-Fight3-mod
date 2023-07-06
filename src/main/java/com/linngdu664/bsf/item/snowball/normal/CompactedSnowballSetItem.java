package com.linngdu664.bsf.item.snowball.normal;

import com.linngdu664.bsf.entity.snowball.nomal.CompactedSnowballEntity;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CompactedSnowballSetItem extends AbstractBSFSnowballItem {
    public CompactedSnowballSetItem() {
        super(Rarity.COMMON);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!pLevel.isClientSide) {
            CompactedSnowballEntity snowballEntity1 = new CompactedSnowballEntity(pPlayer, pLevel, getLaunchFunc(getSnowballDamageRate(pPlayer)));
            CompactedSnowballEntity snowballEntity2 = new CompactedSnowballEntity(pPlayer, pLevel, getLaunchFunc(getSnowballDamageRate(pPlayer)));
            CompactedSnowballEntity snowballEntity3 = new CompactedSnowballEntity(pPlayer, pLevel, getLaunchFunc(getSnowballDamageRate(pPlayer)));
            snowballEntity1.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, getSnowballSlowdownRate(pPlayer), 10.0F);
            snowballEntity2.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, getSnowballSlowdownRate(pPlayer), 10.0F);
            snowballEntity3.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, getSnowballSlowdownRate(pPlayer), 10.0F);
            pLevel.addFreshEntity(snowballEntity1);
            pLevel.addFreshEntity(snowballEntity2);
            pLevel.addFreshEntity(snowballEntity3);
        }
        if (!pPlayer.getAbilities().instabuild) {
            itemStack.shrink(1);
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }

    @Override
    public boolean canBeLaunchedByMachineGun() {
        return false;
    }

    @Override
    public boolean canBeLaunchedByNormalWeapon() {
        return false;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_hand.tooltip")).withStyle(ChatFormatting.DARK_GREEN));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_no_cannon.tooltip")).withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_no_machine_gun.tooltip")).withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_no_shotgun.tooltip")).withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("compacted_snowball_set.tooltip")).withStyle(ChatFormatting.DARK_AQUA));
    }
}
