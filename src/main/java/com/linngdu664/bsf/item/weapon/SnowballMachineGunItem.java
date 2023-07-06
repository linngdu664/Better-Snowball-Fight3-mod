package com.linngdu664.bsf.item.weapon;

import com.linngdu664.bsf.entity.BSFSnowballEntity;
import com.linngdu664.bsf.item.tank.AbstractSnowballTankItem;
import com.linngdu664.bsf.item.tank.normal.ExplosiveSnowballTank;
import com.linngdu664.bsf.item.tank.tracking.ExplosiveMonsterTrackingSnowballTank;
import com.linngdu664.bsf.item.tank.tracking.ExplosivePlayerTrackingSnowballTank;
import com.linngdu664.bsf.util.LaunchFrom;
import com.linngdu664.bsf.util.LaunchFunc;
import com.linngdu664.bsf.util.SoundRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SnowballMachineGunItem extends AbstractBSFWeaponItem {
    //private int timer = 0;
    private float recoil;
    //private float damageChance;
    private ItemStack ammo;
    private boolean isExplosive;
    //private boolean isOnCoolDown = false;

    public SnowballMachineGunItem() {
        super(1919, Rarity.EPIC);
    }//1919

    public LaunchFunc getLaunchFunc() {
        return new LaunchFunc() {
            @Override
            public LaunchFrom getLaunchFrom() {
                return LaunchFrom.MACHINE_GUN;
            }

            @Override
            public void launchProperties(BSFSnowballEntity bsfSnowballEntity) {
                bsfSnowballEntity.setPunch(1.2);
            }
        };
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        //damageChance = 1.0F / (1.0F + EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING, stack));
        ammo = findAmmo(pPlayer, true, false);
        if (ammo != null) {
            recoil = ((AbstractSnowballTankItem) ammo.getItem()).getSnowball().getRecoil();
            isExplosive = ammo.getItem() instanceof ExplosiveSnowballTank || ammo.getItem() instanceof ExplosivePlayerTrackingSnowballTank || ammo.getItem() instanceof ExplosiveMonsterTrackingSnowballTank;
            pPlayer.startUsingItem(pUsedHand);
            return InteractionResultHolder.consume(stack);
        }
        return InteractionResultHolder.fail(stack);
    }

/*
    @SuppressWarnings("deprecation")
    @Override
    public void onUseTick(@NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, @NotNull ItemStack pStack, int pRemainingUseDuration) {
        Player player = (Player) pLivingEntity;
        float pitch = player.getXRot();
        float yaw = player.getYRot();
        boolean flag = false;
        if (!isOnCoolDown) {
            ItemStack itemStack = findAmmo(player, true, false);
            if (itemStack != null) {
                if (itemStack.getItem() instanceof ExplosiveSnowballTank || itemStack.getItem() instanceof ExplosivePlayerTrackingSnowballTank || itemStack.getItem() instanceof ExplosiveMonsterTrackingSnowballTank) {
                    flag = true;
                }
                if (timer % 3 == 0 && (!flag || timer % 6 == 0)) {
                    BSFSnowballEntity snowballEntity = ItemToEntity(itemStack.getItem(), player, pLevel, getLaunchFunc());
                    recoil = ((AbstractSnowballTankItem) itemStack.getItem()).getSnowball().getRecoil();
                    BSFShootFromRotation(snowballEntity, pitch, yaw, 2.6F, 1.0F);
                    pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundRegister.SNOWBALL_MACHINE_GUN_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                    pLevel.addFreshEntity(snowballEntity);

                    Vec3 cameraVec = Vec3.directionFromRotation(pitch, yaw);
                    // add push
                    if (pLevel.isClientSide()) {
                        player.push(-cameraVec.x * recoil * 0.25, -cameraVec.y * recoil * 0.25, -cameraVec.z * recoil * 0.25);
                    } else {
                        // add particles
                        ((ServerLevel) pLevel).sendParticles(ParticleTypes.SNOWFLAKE, player.getX() + cameraVec.x, player.getEyeY() + cameraVec.y, player.getZ() + cameraVec.z, 4, 0, 0, 0, 0.32);
                    }
                    // handle ammo consume and damage weapon.
                    consumeAmmo(itemStack, player);
                    if (pLevel.getRandom().nextFloat() <= damageChance && !player.getAbilities().instabuild) {
                        pStack.setDamageValue(pStack.getDamageValue() + 1);
                        if (pStack.getDamageValue() == 512) {
                            player.awardStat(Stats.ITEM_BROKEN.get(pStack.getItem()));
                            pStack.shrink(1);
                            pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
                        }
                    }
                }
            } else {
                recoil = 0;
            }

            // set pitch according to recoil.
            if (pitch > -90.0F && pLevel.isClientSide() && (!flag || timer % 6 < 3)) {
                player.setXRot(pitch - recoil);
            }

            // add and check cd time.
            if (itemStack != null && !pLevel.isClientSide) {
                timer += 3;
                if (flag && timer >= 60) {
                    player.getCooldowns().addCooldown(this, 60);
                    //isOnCoolDown = true;
                    timer = 120;
                } else if (timer >= 120) {
                    player.getCooldowns().addCooldown(this, 60);
                    //isOnCoolDown = true;
                }
            }
        }
    }
    */

    @SuppressWarnings("deprecation")
    @Override
    public void onUseTick(@NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, @NotNull ItemStack pStack, int pRemainingUseDuration) {
        if (pRemainingUseDuration == 1 || ammo == null || ammo.isEmpty()) {
            this.releaseUsing(pStack, pLevel, pLivingEntity, pRemainingUseDuration);
            return;
        }
        Player player = (Player) pLivingEntity;
        float pitch = player.getXRot();
        float yaw = player.getYRot();
        if (pRemainingUseDuration % 3 == 2 && (!isExplosive || pRemainingUseDuration % 6 == 5)) {
            Vec3 cameraVec = Vec3.directionFromRotation(pitch, yaw);
            if (pLevel.isClientSide()) {
                // add push
                player.push(-cameraVec.x * recoil * 0.25, -cameraVec.y * recoil * 0.25, -cameraVec.z * recoil * 0.25);
            } else {
                BSFSnowballEntity snowballEntity = ItemToEntity(ammo.getItem(), player, pLevel, getLaunchFunc());
                BSFShootFromRotation(snowballEntity, pitch, yaw, 2.6F, 1.0F);
                pLevel.addFreshEntity(snowballEntity);
                pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundRegister.SNOWBALL_MACHINE_GUN_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                // add particles
                ((ServerLevel) pLevel).sendParticles(ParticleTypes.SNOWFLAKE, player.getX() + cameraVec.x, player.getEyeY() + cameraVec.y, player.getZ() + cameraVec.z, 4, 0, 0, 0, 0.32);
                // handle ammo consume and damage weapon.
                consumeAmmo(ammo, player);
                pStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
             /*   if (pLevel.getRandom().nextFloat() <= damageChance && !player.getAbilities().instabuild) {
                    pStack.setDamageValue(pStack.getDamageValue() + 1);
                    if (pStack.getDamageValue() == 512) {
                        player.awardStat(Stats.ITEM_BROKEN.get(pStack.getItem()));
                        pStack.shrink(1);
                        pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
                    }
                }*/
            }
        }
        // set pitch according to recoil.
        if (pitch > -90.0F && pLevel.isClientSide() && (!isExplosive || pRemainingUseDuration % 6 < 3)) {
            player.setXRot(pitch - recoil);
        }
    }
/*
    @Override
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (!pLevel.isClientSide) {
            if (timer > 0) {
                timer -= 2;
            } else {
                //isOnCoolDown = false;
            }
            if (timer < 0) {
                timer = 0;
            }
        }
        //System.out.println(timer + " " + isOnCoolDown);
    }*/


    @Override
    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, int pTimeCharged) {
        pLivingEntity.playSound(SoundRegister.MACHINE_GUN_COOLING.get(), 3.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
        if (pLivingEntity instanceof Player player) {
            player.stopUsingItem();
            if (isExplosive) {
                player.getCooldowns().addCooldown(this, getUseDuration(pStack) - pTimeCharged);
            } else {
                player.getCooldowns().addCooldown(this, (getUseDuration(pStack) - pTimeCharged) / 2);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        super.getUseDuration(pStack);
        if (isExplosive) {
            return 60;
        }
        return 120;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_machine_gun1.tooltip", null, new Object[]{})).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_machine_gun2.tooltip", null, new Object[]{})).withStyle(ChatFormatting.RED));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_machine_gun.tooltip", null, new Object[]{})).withStyle(ChatFormatting.DARK_AQUA));
    }
}
