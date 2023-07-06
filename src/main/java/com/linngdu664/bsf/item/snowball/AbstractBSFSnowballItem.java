package com.linngdu664.bsf.item.snowball;

import com.linngdu664.bsf.entity.BSFSnowballEntity;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.util.ItemGroup;
import com.linngdu664.bsf.util.LaunchFrom;
import com.linngdu664.bsf.util.LaunchFunc;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

import java.util.Objects;

public abstract class AbstractBSFSnowballItem extends Item {
    public AbstractBSFSnowballItem(Rarity rarity) {
        super(new Properties().tab(ItemGroup.MAIN).stacksTo(16).rarity(rarity));
    }

    public LaunchFunc getLaunchFunc(float playerBadEffectRate) {
        return new LaunchFunc() {
            @Override
            public LaunchFrom getLaunchFrom() {
                return LaunchFrom.HAND;
            }

            @Override
            public void launchProperties(BSFSnowballEntity bsfSnowballEntity) {
                bsfSnowballEntity.setBlazeDamage(bsfSnowballEntity.getBlazeDamage() * playerBadEffectRate).setDamage(bsfSnowballEntity.getDamage() * playerBadEffectRate);
            }
        };
    }

    /**
     * Handle the storage of the snowballs.
     *
     * @param pPlayer   The player who uses snowball.
     * @param itemStack The snowball itemstack.
     * @param tank      The storage tank with the specific type.
     * @return If the method stores snowballs in the tank successfully, it will return true, else return false.
     */
    public boolean storageInTank(Player pPlayer, ItemStack itemStack, Item tank) {
        if (pPlayer.getOffhandItem().getItem() == ItemRegister.EMPTY_SNOWBALL_STORAGE_TANK.get()) {
            pPlayer.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(tank));
            pPlayer.getOffhandItem().setDamageValue(96 - pPlayer.getMainHandItem().getCount());
            if (!pPlayer.getAbilities().instabuild) {
                itemStack.shrink(pPlayer.getMainHandItem().getCount());
            }
            return true;
        }
        if (pPlayer.getOffhandItem().getItem() == tank && pPlayer.getOffhandItem().getDamageValue() != 0) {
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

    // 1.005^(-ticks)
    public float getSnowballSlowdownRate(Player player) {
        return (float) Math.exp(-0.005 * player.getTicksFrozen());
    }

    public float getSnowballDamageRate(Player player) {
        float reDamageRate = 1;
        if (player.hasEffect(MobEffects.WEAKNESS)) {
            reDamageRate -= switch (Objects.requireNonNull(player.getEffect(MobEffects.WEAKNESS)).getAmplifier()) {
                case 0 -> 0.25f;
                case 1 -> 0.5f;
                default -> 0.75f;
            };
        }
        if (player.hasEffect(MobEffects.DAMAGE_BOOST)) {
            if (Objects.requireNonNull(player.getEffect(MobEffects.DAMAGE_BOOST)).getAmplifier() == 0) {
                reDamageRate += 0.15F;
            } else {
                reDamageRate += 0.3F;
            }
        }
        return reDamageRate;
    }

    /**
     * You must override this fucking method if you want to launch the snowball by weapons.
     *
     * @param level        Level.
     * @param livingEntity The entity who throws/launches the snowball.
     * @param launchFunc   The launch func.
     * @return The corresponding entity.
     */
    public abstract BSFSnowballEntity getCorrespondingEntity(Level level, LivingEntity livingEntity, LaunchFunc launchFunc);

    public boolean canBeLaunchedByMachineGun() {
        return true;
    }

    public boolean canBeLaunchedByNormalWeapon() {
        return true;
    }

    public float getRecoil() {
        return 0.075F;
    }

    public double getPushRank() {
        return 0.1;
    }
}
