package com.linngdu664.bsf.item.snowball;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.entity.snowball.util.LaunchFrom;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.item.tank.SnowballTankItem;
import com.linngdu664.bsf.item.weapon.SnowballCannonItem;
import com.linngdu664.bsf.item.weapon.SnowballMachineGunItem;
import com.linngdu664.bsf.item.weapon.SnowballShotgunItem;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractBSFSnowballItem extends Item {
    public static final int HAND_TYPE_FLAG = 1;
    public AbstractBSFSnowballItem(Rarity rarity) {
        super(new Properties().stacksTo(16).rarity(rarity));
    }
    public ILaunchAdjustment getLaunchAdjustment(float playerBadEffectRate) {
        return new ILaunchAdjustment() {
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
                return damage * playerBadEffectRate;
            }

            @Override
            public float adjustBlazeDamage(float blazeDamage) {
                return blazeDamage * playerBadEffectRate;
            }

            @Override
            public LaunchFrom getLaunchFrom() {
                return LaunchFrom.HAND;
            }
        };
    }

    /**
     * Handle the storage of the snowballs.
     *
     * @param pPlayer   The player who uses snowball.
     * @param itemStack The snowball itemstack.
     * @return If the method stores snowballs in the tank successfully, it will return true, else return false.
     */
    public boolean storageInTank(Player pPlayer, ItemStack itemStack) {
        if (pPlayer.getOffhandItem().getItem() == ItemRegister.EMPTY_SNOWBALL_STORAGE_TANK.get()) {
            pPlayer.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(getTank()));
            pPlayer.getOffhandItem().setDamageValue(96 - pPlayer.getMainHandItem().getCount());
            if (!pPlayer.getAbilities().instabuild) {
                itemStack.shrink(pPlayer.getMainHandItem().getCount());
            }
            return true;
        }
        if (pPlayer.getOffhandItem().getItem() instanceof SnowballTankItem tank && this.equals(tank.getSnowball()) && pPlayer.getOffhandItem().getDamageValue() != 0) {
            if (pPlayer.getOffhandItem().getDamageValue() >= pPlayer.getMainHandItem().getCount()) {
                pPlayer.getOffhandItem().setDamageValue(pPlayer.getOffhandItem().getDamageValue() - pPlayer.getMainHandItem().getCount());
                if (!pPlayer.getAbilities().instabuild) {
                    itemStack.shrink(pPlayer.getMainHandItem().getCount());
                }
            } else {
                if (!pPlayer.getAbilities().instabuild) {
                    itemStack.shrink(pPlayer.getOffhandItem().getDamageValue());
                }
                pPlayer.getOffhandItem().setDamageValue(0);
            }
            return true;
        }
        return false;
    }

    public InteractionResultHolder<ItemStack> throwOrStorage(Player pPlayer, Level pLevel, InteractionHand pUsedHand, float velocity, int coolDown) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!storageInTank(pPlayer, itemStack)) {
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!pLevel.isClientSide) {
                AbstractBSFSnowballEntity snowballEntity = getCorrespondingEntity(pLevel, pPlayer, getLaunchAdjustment(getSnowballDamageRate(pPlayer)));
                snowballEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, velocity * getSnowballSlowdownRate(pPlayer), 1.0F);
                pLevel.addFreshEntity(snowballEntity);
            }
            if (!pPlayer.getAbilities().instabuild) {
                itemStack.shrink(1);
                if (coolDown != 0) {
                    pPlayer.getCooldowns().addCooldown(this, coolDown);
                }
            }
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }

    // 1.005^(-ticks)
    public float getSnowballSlowdownRate(Player player) {
        return (float) Math.exp(-0.005 * player.getTicksFrozen());
    }

    public float getSnowballDamageRate(Player player) {
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

    public void generateWeaponTips(List<Component> pTooltipComponents) {
        int typeFlag = getTypeFlag();
        if ((typeFlag & HAND_TYPE_FLAG) == 0) {
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_no_hand.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_RED));
        } else {
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_hand.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
        }
        if ((typeFlag & SnowballCannonItem.TYPE_FLAG) == 0) {
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_no_cannon.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_RED));
        } else {
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_cannon.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
        }
        if ((typeFlag & SnowballMachineGunItem.TYPE_FLAG) == 0) {
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_no_machine_gun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_RED));
        } else {
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_machine_gun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
        }
        if ((typeFlag & SnowballShotgunItem.TYPE_FLAG) == 0) {
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_no_shotgun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_RED));
        } else {
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_shotgun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
        }
    }

    public void addUsageTips(List<Component> pTooltipComponents) {}

    public void addLastTips(List<Component> pTooltipComponents) {}

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        generateWeaponTips(pTooltipComponents);
        addUsageTips(pTooltipComponents);
        addLastTips(pTooltipComponents);
    }

    /**
     * You must override this fucking method if you want to launch the snowball by weapons.
     *
     * @param level        Level.
     * @param livingEntity The entity who throws/launches the snowball.
     * @return The corresponding entity.
     */
    public abstract AbstractBSFSnowballEntity getCorrespondingEntity(Level level, LivingEntity livingEntity, ILaunchAdjustment launchAdjustment);
    public abstract int getTypeFlag();
    public abstract Item getTank();

    public Item getLargeTank() {
        return null;
    }

    public double getMachineGunRecoil() {
        return 0.075;
    }

    public double getShotgunPushRank() {
        return 0.1;
    }
}
