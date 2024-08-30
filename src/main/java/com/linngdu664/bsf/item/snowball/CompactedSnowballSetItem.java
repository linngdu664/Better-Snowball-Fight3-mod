package com.linngdu664.bsf.item.snowball;

import com.linngdu664.bsf.entity.snowball.nomal.CompactedSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.entity.snowball.util.LaunchFrom;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CompactedSnowballSetItem extends Item {
    public CompactedSnowballSetItem() {
        super(new Properties().stacksTo(16).rarity(Rarity.COMMON));
    }

    private float getSnowballDamageRate(Player player) {
        float reDamageRate = 1;
        if (player.hasEffect(MobEffects.WEAKNESS)) {
            reDamageRate -= switch (player.getEffect(MobEffects.WEAKNESS).getAmplifier()) {
                case 0 -> 0.25f;
                case 1 -> 0.5f;
                default -> 0.75f;
            };
        }
        if (player.hasEffect(MobEffects.DAMAGE_BOOST)) {
            if (player.getEffect(MobEffects.DAMAGE_BOOST).getAmplifier() == 0) {
                reDamageRate += 0.15F;
            } else {
                reDamageRate += 0.3F;
            }
        }
        return reDamageRate;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!pLevel.isClientSide) {
            ILaunchAdjustment launchAdjustment = new ILaunchAdjustment() {
                @Override
                public double adjustPunch(double punch) {
                    return punch;
                }

                @Override
                public int adjustWeaknessTicks(int weaknessTicks) {
                    return weaknessTicks;
                }

                @Override
                public int adjustFrozenTicks(int frozenTicks) {
                    return frozenTicks;
                }

                @Override
                public float adjustDamage(float damage) {
                    return damage * getSnowballDamageRate(pPlayer);
                }

                @Override
                public float adjustBlazeDamage(float blazeDamage) {
                    return blazeDamage * getSnowballDamageRate(pPlayer);
                }

                @Override
                public LaunchFrom getLaunchFrom() {
                    return LaunchFrom.HAND;
                }
            };
            float slowdownRate = (float) Math.exp(-0.005 * pPlayer.getTicksFrozen());
            CompactedSnowballEntity snowballEntity1 = new CompactedSnowballEntity(pPlayer, pLevel, launchAdjustment);
            CompactedSnowballEntity snowballEntity2 = new CompactedSnowballEntity(pPlayer, pLevel, launchAdjustment);
            CompactedSnowballEntity snowballEntity3 = new CompactedSnowballEntity(pPlayer, pLevel, launchAdjustment);
            snowballEntity1.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, slowdownRate, 10.0F);
            snowballEntity2.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, slowdownRate, 10.0F);
            snowballEntity3.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, slowdownRate, 10.0F);
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
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_hand.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_no_cannon.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_no_machine_gun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_no_shotgun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_RED));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("compacted_snowball_set.tooltip", null, new Object[0])).withStyle(ChatFormatting.GRAY));
    }
}
