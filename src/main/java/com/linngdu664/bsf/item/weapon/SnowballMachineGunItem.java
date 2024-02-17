package com.linngdu664.bsf.item.weapon;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.entity.snowball.util.LaunchFrom;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import com.linngdu664.bsf.registry.EffectRegister;
import com.linngdu664.bsf.registry.SoundRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SnowballMachineGunItem extends AbstractBSFWeaponItem {
    public static final int TYPE_FLAG = 4;
    private double recoil;
    private ItemStack ammo;
    private boolean isExplosive;


    public SnowballMachineGunItem() {
        super(1919, Rarity.EPIC, TYPE_FLAG);
    }//1919

    @Override
    public ILaunchAdjustment getLaunchAdjustment(double damageDropRate, Item snowball) {
        return new ILaunchAdjustment() {
            @Override
            public double adjustPunch(double punch) {
                return punch + 1.2;
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
                return damage;
            }

            @Override
            public float adjustBlazeDamage(float blazeDamage) {
                return blazeDamage;
            }

            @Override
            public LaunchFrom getLaunchFrom() {
                return LaunchFrom.MACHINE_GUN;
            }
        };
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        CompoundTag tag = stack.getOrCreateTag();
        ammo = getAmmo(pPlayer, stack);
        if (ammo != null && !pPlayer.hasEffect(EffectRegister.WEAPON_JAM.get()) && !tag.getBoolean("IsCoolDown")) {
            String path = ammo.getOrCreateTag().getString("Snowball");
            recoil = ((AbstractBSFSnowballItem) ForgeRegistries.ITEMS.getValue(new ResourceLocation(Main.MODID, path))).getMachineGunRecoil();
            isExplosive = path.contains("explosive");
            if (isExplosive) {
                tag.putInt("Timer", (tag.getInt("Timer") / 6 + 1) * 6); // ceil to multiple of 6
            } else {
                tag.putInt("Timer", (tag.getInt("Timer") / 3 + 1) * 3); // ceil to multiple of 3
            }
            pPlayer.startUsingItem(pUsedHand);
            return InteractionResultHolder.consume(stack);
        }
        return InteractionResultHolder.fail(stack);
    }

    @Override
    public void onUseTick(@NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, @NotNull ItemStack pStack, int pRemainingUseDuration) {
        CompoundTag tag = pStack.getOrCreateTag();
        int timer = tag.getInt("Timer");
        if (timer >= 360) {
            pLivingEntity.playSound(SoundRegister.MACHINE_GUN_COOLING.get(), 3.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            tag.putBoolean("IsCoolDown", true);
            this.releaseUsing(pStack, pLevel, pLivingEntity, pRemainingUseDuration);
            return;
        } else if (ammo == null || ammo.isEmpty() || !ammo.getOrCreateTag().contains("Snowball") || pLivingEntity.hasEffect(EffectRegister.WEAPON_JAM.get())) {
            this.releaseUsing(pStack, pLevel, pLivingEntity, pRemainingUseDuration);
            return;
        }
        Player player = (Player) pLivingEntity;
        float pitch = player.getXRot();
        float yaw = player.getYRot();
        if (timer % 9 == 0 && (!isExplosive || timer % 36 == 0)) {
            Vec3 cameraVec = Vec3.directionFromRotation(pitch, yaw);
            if (pLevel.isClientSide()) {
                // add push
                player.push(-cameraVec.x * recoil * 0.25, -cameraVec.y * recoil * 0.25, -cameraVec.z * recoil * 0.25);
            } else {
                AbstractBSFSnowballEntity snowballEntity = ItemToEntity(ammo, player, pLevel, getLaunchAdjustment(1, ammo.getItem()));
                BSFShootFromRotation(snowballEntity, pitch, yaw, 2.6F, 1.0F);
                pLevel.addFreshEntity(snowballEntity);
                pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundRegister.SNOWBALL_MACHINE_GUN_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                // add particles
                ((ServerLevel) pLevel).sendParticles(ParticleTypes.SNOWFLAKE, player.getX() + cameraVec.x, player.getEyeY() + cameraVec.y, player.getZ() + cameraVec.z, 4, 0, 0, 0, 0.32);
                // handle ammo consume and damage weapon.
                consumeAmmo(ammo, player);
                pStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
            }
        }
        // set pitch according to recoil.
        if (pitch > -90.0F && pLevel.isClientSide() && (!isExplosive || timer % 36 < 18)) {
            player.setXRot(pitch - (float) recoil);
        }
        tag.putInt("Timer", isExplosive ? timer + 6 : timer + 3);
    }
/*
    @Override
    public void onUseTick(@NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, @NotNull ItemStack pStack, int pRemainingUseDuration) {
        if (pRemainingUseDuration == 1 || ammo == null || ammo.isEmpty() || !ammo.getOrCreateTag().contains("Snowball") || pLivingEntity.hasEffect(EffectRegister.WEAPON_JAM.get())) {
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
                AbstractBSFSnowballEntity snowballEntity = ItemToEntity(ammo, player, pLevel, getLaunchAdjustment(1, ammo.getItem()));
                BSFShootFromRotation(snowballEntity, pitch, yaw, 2.6F, 1.0F);
                pLevel.addFreshEntity(snowballEntity);
                pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundRegister.SNOWBALL_MACHINE_GUN_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                // add particles
                ((ServerLevel) pLevel).sendParticles(ParticleTypes.SNOWFLAKE, player.getX() + cameraVec.x, player.getEyeY() + cameraVec.y, player.getZ() + cameraVec.z, 4, 0, 0, 0, 0.32);
                // handle ammo consume and damage weapon.
                consumeAmmo(ammo, player);
                pStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
            }
        }
        // set pitch according to recoil.
        if (pitch > -90.0F && pLevel.isClientSide() && (!isExplosive || pRemainingUseDuration % 6 < 3)) {
            player.setXRot(pitch - (float) recoil);
        }
    }*/

    @Override
    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLivingEntity instanceof Player player) {
            player.stopUsingItem();
//            if (isExplosive) {
//                player.getCooldowns().addCooldown(this, (getUseDuration(pStack) - pTimeCharged) * 3 + 10);
//            } else {
//                player.getCooldowns().addCooldown(this, (int) ((getUseDuration(pStack) - pTimeCharged) * 1.5 + 10));
//            }
            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }

    @Override
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (pEntity instanceof Player player && !pStack.equals(player.getUseItem())) {
            CompoundTag tag = pStack.getOrCreateTag();
            int timer = tag.getInt("Timer");
            if (timer > 0) {
                if (timer > 2) {
                    tag.putInt("Timer", timer - 2);
                } else {
                    tag.putInt("Timer", 0);
                    tag.putBoolean("IsCoolDown", false);
                }
            }
        }
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public boolean isAllowBulkedSnowball() {
        return false;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 72000;
//        if (isExplosive) {
//            return 60;
//        }
//        return 120;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.getItem().equals(newStack.getItem());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_machine_gun1.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_machine_gun2.tooltip", null, new Object[0])).withStyle(ChatFormatting.RED));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_machine_gun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_AQUA));
    }
}
