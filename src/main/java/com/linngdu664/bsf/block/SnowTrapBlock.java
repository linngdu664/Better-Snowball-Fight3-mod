package com.linngdu664.bsf.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SnowTrapBlock extends Block {
    public SnowTrapBlock() {
        super(Properties.copy(Blocks.SNOW_BLOCK));
    }

    @Override
    public void stepOn(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull Entity pEntity) {
        if (pEntity instanceof LivingEntity livingEntity && pLevel instanceof ServerLevel serverLevel) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2));
            if (livingEntity.getTicksFrozen() < 60) {
                livingEntity.setTicksFrozen(60);
            }
            pLevel.setBlock(pPos, Blocks.SNOW_BLOCK.defaultBlockState(), 3);
            double x = pPos.getX() + 0.5;
            double y = pPos.getY() + 0.5;
            double z = pPos.getZ() + 0.5;
            serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, x, y, z, 200, 0, 0, 0, 0.32);
            serverLevel.playSound(null, x, y, z, SoundEvents.PLAYER_HURT_FREEZE, SoundSource.NEUTRAL, 1.0F, 1.0F / (serverLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            serverLevel.playSound(null, x, y, z, SoundEvents.SNOW_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
        }
    }
}
