package com.linngdu664.bsf.item.weapon;

import com.linngdu664.bsf.entity.snowball.special.GPSSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.EffectRegister;
import com.linngdu664.bsf.registry.SoundRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TargetLocatorItem extends AbstractBSFWeaponItem {
    public static final int TYPE_FLAG = 32;

    public TargetLocatorItem() {
        super(514, Rarity.UNCOMMON, TYPE_FLAG);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer.hasEffect(EffectRegister.WEAPON_JAM.get())) {
            return InteractionResultHolder.fail(itemStack);
        }
        if (!pLevel.isClientSide) {
            if (pPlayer.isShiftKeyDown()) {
                CompoundTag compoundTag = itemStack.getOrCreateTag();
                compoundTag.remove("ID");
                itemStack.setTag(compoundTag);
                pPlayer.displayClientMessage(MutableComponent.create(new TranslatableContents("targeted_clear.tip", null, new Object[0])), false);
                pPlayer.getItemInHand(pUsedHand).setHoverName(MutableComponent.create(new TranslatableContents("item.bsf.target_locator", null, new Object[0])));
                pPlayer.awardStat(Stats.ITEM_USED.get(this));
            } else {
                ItemStack stack = getAmmo(pPlayer, itemStack);
                if (stack != null || pPlayer.isCreative()) {
                    pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundRegister.SNOWBALL_CANNON_SHOOT.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
                    GPSSnowballEntity snowballEntity = new GPSSnowballEntity(pPlayer, pLevel, itemStack);
                    snowballEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 2.0F, 1.0F);
                    pLevel.addFreshEntity(snowballEntity);
                    itemStack.hurtAndBreak(1, pPlayer, p -> p.broadcastBreakEvent(pUsedHand));
                    if (stack != null) {
                        consumeAmmo(stack, pPlayer);
                    }
                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("target_locator.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("target_locator1.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("target_locator2.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
    }

    @Override
    public ILaunchAdjustment getLaunchAdjustment(double damageDropRate, Item snowball) {
        return null;
    }

    @Override
    public boolean isAllowBulkedSnowball() {
        return true;
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 1;
    }
}
