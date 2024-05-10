package com.linngdu664.bsf.item.tool;

import com.linngdu664.bsf.network.ForwardConeParticlesToClient;
import com.linngdu664.bsf.network.ForwardRaysParticlesToClient;
import com.linngdu664.bsf.network.ToggleMovingSoundToClient;
import com.linngdu664.bsf.registry.NetworkRegister;
import com.linngdu664.bsf.registry.ParticleRegister;
import com.linngdu664.bsf.registry.SoundRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ColdCompressionJetEngineItem extends AbstractBSFEnhanceableToolItem {
    public static final int STARTUP_DURATION = 24;

    public ColdCompressionJetEngineItem() {
        super(Rarity.RARE, 400);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        BlockPos blockPos1 = new BlockPos((int) pEntity.getX() - 1, (int) pEntity.getY(), (int) pEntity.getZ() - 1);
        if (!pLevel.isClientSide && (pLevel.getBlockState(blockPos1).is(BlockTags.SNOW) || pLevel.getBlockState(blockPos1.below()).is(BlockTags.SNOW)) && pStack.getDamageValue() > 0 && pLevel.getRandom().nextFloat() < 0.55f) {
            if (pIsSelected) {
                pStack.setDamageValue(Math.max(pStack.getDamageValue() - 2, 0));
                NetworkRegister.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> pEntity), new ForwardRaysParticlesToClient(pEntity.position().add(-0.5, 0, -0.5), pEntity.position().add(0.5, 0, 0.5), new Vec3(0, 1, 0), 0.1, 0.15, 4));
            } else {
                pStack.setDamageValue(Math.max(pStack.getDamageValue() - 1, 0));
                NetworkRegister.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> pEntity), new ForwardRaysParticlesToClient(pEntity.position().add(-0.5, 0, -0.5), pEntity.position().add(0.5, 0, 0.5), new Vec3(0, 1, 0), 0.1, 0.15, 2));
            }
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (stack.getDamageValue() == stack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(stack);
        }
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onUseTick(@NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, @NotNull ItemStack pStack, int pRemainingUseDuration) {
        if (pStack.getDamageValue() == pStack.getMaxDamage() - 1) {
            pLivingEntity.stopUsingItem();
            return;
        }
        int i = this.getUseDuration(pStack) - pRemainingUseDuration;
        Vec3 vec3 = Vec3.directionFromRotation(pLivingEntity.getXRot(), pLivingEntity.getYRot());
        Vec3 particlesPos = pLivingEntity.getEyePosition();
        if (i == 0 && !pLevel.isClientSide) {
            NetworkRegister.PACKET_HANDLER.send(PacketDistributor.DIMENSION.with(pLevel::dimension), new ToggleMovingSoundToClient(pLivingEntity, SoundRegister.COLD_COMPRESSION_JET_ENGINE_STARTUP1.get(), ToggleMovingSoundToClient.PLAY_ONCE));
            NetworkRegister.PACKET_HANDLER.send(PacketDistributor.DIMENSION.with(pLevel::dimension), new ToggleMovingSoundToClient(pLivingEntity, SoundRegister.COLD_COMPRESSION_JET_ENGINE_STARTUP2.get(), ToggleMovingSoundToClient.PLAY_LOOP));
        }
        pStack.hurtAndBreak(1, pLivingEntity, p -> {});
        if (i < STARTUP_DURATION) {
            if (!pLevel.isClientSide) {
                Vec3 newPos = particlesPos.add(vec3.reverse());
                ((ServerLevel) pLevel).sendParticles(ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), newPos.x, newPos.y, newPos.z, 1, 0, 0, 0, 0.04);
            }
            return;
        }
        if (i == STARTUP_DURATION) {
            Vec3 aVec = vec3.scale(2);
            pLivingEntity.push(aVec.x, aVec.y, aVec.z);
            if (!pLevel.isClientSide) {
                NetworkRegister.PACKET_HANDLER.send(PacketDistributor.DIMENSION.with(pLevel::dimension), new ToggleMovingSoundToClient(pLivingEntity, SoundRegister.COLD_COMPRESSION_JET_ENGINE_STARTUP2.get(), ToggleMovingSoundToClient.STOP_LOOP));
                NetworkRegister.PACKET_HANDLER.send(PacketDistributor.DIMENSION.with(pLevel::dimension), new ToggleMovingSoundToClient(pLivingEntity, SoundRegister.COLD_COMPRESSION_JET_ENGINE_STARTUP3.get(), ToggleMovingSoundToClient.PLAY_ONCE));
                NetworkRegister.PACKET_HANDLER.send(PacketDistributor.DIMENSION.with(pLevel::dimension), new ToggleMovingSoundToClient(pLivingEntity, SoundRegister.COLD_COMPRESSION_JET_ENGINE_STARTUP4.get(), ToggleMovingSoundToClient.PLAY_LOOP));
                NetworkRegister.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> pLivingEntity), new ForwardConeParticlesToClient(particlesPos, vec3.reverse().scale(0.5), 5F, 10, 0.2F, 0));
            }
        } else {
            Vec3 aVec = vec3.scale(0.2);
            pLivingEntity.push(aVec.x, aVec.y, aVec.z);
            List<LivingEntity> list = pLevel.getEntitiesOfClass(LivingEntity.class, pLivingEntity.getBoundingBox().inflate(2), p -> !pLivingEntity.equals(p));
            for (LivingEntity entity : list) {
                if (entity.getTicksFrozen() < 100) {
                    entity.setTicksFrozen(100);
                }
                if (pLivingEntity instanceof Player player) {
                    entity.hurt(pLevel.damageSources().playerAttack(player), Float.MIN_VALUE);
                }
            }
            if (vec3.y > 0) {
                pLivingEntity.resetFallDistance();
            }
        }
        if (!pLevel.isClientSide) {
            NetworkRegister.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> pLivingEntity), new ForwardConeParticlesToClient(particlesPos, vec3.reverse(), 2F, 60, 0.5F, 0));
        }
    }

    @Override
    public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
        Level level = entity.level();
        if (!level.isClientSide) {
            NetworkRegister.PACKET_HANDLER.send(PacketDistributor.DIMENSION.with(() -> entity.level().dimension()), new ToggleMovingSoundToClient(entity, SoundRegister.COLD_COMPRESSION_JET_ENGINE_STARTUP5.get(), ToggleMovingSoundToClient.PLAY_ONCE));
            NetworkRegister.PACKET_HANDLER.send(PacketDistributor.DIMENSION.with(() -> entity.level().dimension()), new ToggleMovingSoundToClient(entity, SoundRegister.COLD_COMPRESSION_JET_ENGINE_STARTUP4.get(), ToggleMovingSoundToClient.STOP_LOOP));
            NetworkRegister.PACKET_HANDLER.send(PacketDistributor.DIMENSION.with(() -> entity.level().dimension()), new ToggleMovingSoundToClient(entity, SoundRegister.COLD_COMPRESSION_JET_ENGINE_STARTUP2.get(), ToggleMovingSoundToClient.STOP_LOOP));
        }
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 72000;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.getItem().equals(newStack.getItem());
    }
}
