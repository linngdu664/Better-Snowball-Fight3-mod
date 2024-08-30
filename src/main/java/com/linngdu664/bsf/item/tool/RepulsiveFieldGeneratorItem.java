package com.linngdu664.bsf.item.tool;

import com.linngdu664.bsf.entity.snowball.special.BlackHoleSnowballEntity;
import com.linngdu664.bsf.registry.ParticleRegister;
import com.linngdu664.bsf.registry.SoundRegister;
import com.linngdu664.bsf.util.BSFCommonUtil;
import net.minecraft.ChatFormatting;
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
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

import java.util.List;
import java.util.Vector;

public class RepulsiveFieldGeneratorItem extends AbstractBSFEnhanceableToolItem {
    private final Vector<Projectile> projectileVector = new Vector<>();

    public RepulsiveFieldGeneratorItem() {
        super(Rarity.UNCOMMON, 514);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLivingEntity instanceof Player player) {
            if (!pLevel.isClientSide) {
                projectileVector.clear();
                List<Projectile> list = pLevel.getEntitiesOfClass(Projectile.class, player.getBoundingBox().inflate(3.5), p -> BSFCommonUtil.vec3AngleCos(new Vec3(p.getX() - player.getX(), p.getY() - player.getEyeY(), p.getZ() - player.getZ()), Vec3.directionFromRotation(player.getXRot(), player.getYRot())) > 0.70710678F && !(p instanceof BlackHoleSnowballEntity));
                for (Projectile projectile : list) {
                    Vec3 dvVec = Vec3.directionFromRotation(player.getXRot(), player.getYRot()).scale(2);
                    projectile.push(dvVec.x, dvVec.y, dvVec.z);
                    ((ServerLevel) pLevel).sendParticles(ParticleRegister.GENERATOR_PUSH.get(), projectile.getX(), projectile.getY(), projectile.getZ(), 1, 0, 0, 0, 0);
                }
            }
            if (!player.getAbilities().instabuild) {
                pStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            ScreenshakeHandler.addScreenshake((new ScreenshakeInstance(5)).setIntensity(0.5f).setEasing(Easing.EXPO_IN_OUT));
            player.getCooldowns().addCooldown(this, getUseDuration(pStack) - pTimeCharged + 20);
            pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundRegister.FIELD_PUSH.get(), SoundSource.PLAYERS, 0.5F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
        }
    }

    /**
     * Triggered every tick to make the snowball stop
     *
     * @param pLevel                level
     * @param pLivingEntity         player
     * @param pStack                itemstack
     * @param pRemainingUseDuration remaining use duration
     */
    @Override
    public void onUseTick(@NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, @NotNull ItemStack pStack, int pRemainingUseDuration) {
        if (pRemainingUseDuration == 1) {
            this.releaseUsing(pStack, pLevel, pLivingEntity, pRemainingUseDuration);
        } else {
            if (pRemainingUseDuration == 60) {
                pLevel.playSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), SoundRegister.FIELD_START.get(), SoundSource.PLAYERS, 0.7F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            }
            List<Projectile> list = pLevel.getEntitiesOfClass(Projectile.class, pLivingEntity.getBoundingBox().inflate(3), p -> BSFCommonUtil.vec3AngleCos(p.position().subtract(pLivingEntity.getEyePosition()), Vec3.directionFromRotation(pLivingEntity.getXRot(), pLivingEntity.getYRot())) > 0.86602540F && !(p instanceof BlackHoleSnowballEntity));
            for (Projectile projectile : list) {
                if (!projectileVector.contains(projectile) && !pLevel.isClientSide) {
                    pLevel.playSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), SoundRegister.FIELD_SNOWBALL_STOP.get(), SoundSource.PLAYERS, 0.7F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                    ((ServerLevel) pLevel).sendParticles(ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), projectile.getX(), projectile.getY(), projectile.getZ(), 10, 0, 0, 0, 0.04);
                }
                projectileVector.add(projectile);
                Vec3 dvVec = projectile.getDeltaMovement().scale(-0.8);
                projectile.push(dvVec.x, dvVec.y, dvVec.z);
                if (!pLevel.isClientSide){
                    ((ServerLevel) pLevel).sendParticles(ParticleRegister.GENERATOR_FIX.get(), projectile.getX(), projectile.getY(), projectile.getZ(), 1, 0, 0, 0, 0);
                }
            }
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(itemStack);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 60;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack pStack, ItemStack pRepairCandidate) {
        return pRepairCandidate.is(Items.IRON_INGOT);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("repulsive_field_generator.tooltip", null, new Object[0])).withStyle(ChatFormatting.GRAY));
    }
}
