package com.linngdu664.bsf.item.weapon;

import com.linngdu664.bsf.entity.snowball.nomal.SmoothSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.entity.snowball.util.LaunchFrom;
import com.linngdu664.bsf.registry.EffectRegister;
import com.linngdu664.bsf.registry.ParticleRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SnowmanInHandItem extends Item {
    private static final ILaunchAdjustment LAUNCH_ADJUSTMENT = new ILaunchAdjustment() {
        @Override
        public double adjustPunch(double punch) {
            return punch + 1;
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
            return LaunchFrom.SNOWMAN_IN_HAND;
        }
    };


    public SnowmanInHandItem() {
        super(new Properties().stacksTo(1).durability(256).rarity(Rarity.EPIC));
    }

    @Override
    public void onUseTick(@NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, @NotNull ItemStack pStack, int pRemainingUseDuration) {
        if (pLivingEntity instanceof Player player) {

            HitResult hitResult = player.pick(3, 0, false);
            BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
            Block block = player.level().getBlockState(blockPos).getBlock();

            float pitch = player.getXRot();
            float yaw = player.getYRot();
            Vec3 cameraVec = Vec3.directionFromRotation(pitch, yaw);

            if ((block == Blocks.SNOW_BLOCK || block == Blocks.SNOW || block == Blocks.POWDER_SNOW)) {//charge
                pStack.setDamageValue(pStack.getDamageValue() - 1);
                if (!pLevel.isClientSide()) {
                    pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOW_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                    ((ServerLevel) pLevel).sendParticles(ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), player.getX() + cameraVec.x * 0.5, player.getEyeY() + cameraVec.y * 0.5, player.getZ() + cameraVec.z * 0.5, 1, 0, 0, 0, 0.04);
                }
            } else if (pStack.getDamageValue() < pStack.getMaxDamage() - 1) {//attack
                if (pLevel.isClientSide()) {
                    player.push(-cameraVec.x * 0.025, -cameraVec.y * 0.025, -cameraVec.z * 0.025);
                } else {
                    for (int i = 0; i < 3; i++) {
                        SmoothSnowballEntity snowballEntity = new SmoothSnowballEntity(player, pLevel, LAUNCH_ADJUSTMENT);
                        if (player.isShiftKeyDown()) {
                            snowballEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1, 10.0F);
                        } else {
                            snowballEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1, 15.0F);
                        }

                        pLevel.addFreshEntity(snowballEntity);
                    }
                    pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
                    pStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
                }
            }
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer.hasEffect(EffectRegister.WEAPON_JAM.get())) {
            return InteractionResultHolder.fail(stack);
        }
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 1200;
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 25;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowman_in_hand.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowman_in_hand1.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowman_in_hand2.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_AQUA));
    }
}
