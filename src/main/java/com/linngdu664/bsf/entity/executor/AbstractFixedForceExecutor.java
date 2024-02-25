package com.linngdu664.bsf.entity.executor;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractFixedForceExecutor extends AbstractForceExecutor {
    public AbstractFixedForceExecutor(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractFixedForceExecutor(EntityType<?> pEntityType, double pX, double pY, double pZ, Level pLevel, int GM, int boundaryR2, int range) {
        super(pEntityType, pX, pY, pZ, pLevel, GM, boundaryR2, range, 200);
    }

    @Override
    public void remove(@NotNull RemovalReason pReason) {
        super.remove(pReason);
        Level level = level();
        if (!level.isClientSide) {
            level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY(), this.getZ(), 10, 0, 0, 0, 0.06);
        }
    }

    @Override
    public float getSubspacePower() {
        return 3;
    }
}
