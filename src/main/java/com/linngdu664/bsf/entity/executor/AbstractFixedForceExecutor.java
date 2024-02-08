package com.linngdu664.bsf.entity.executor;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class AbstractFixedForceExecutor extends AbstractForceExecutor {
    public AbstractFixedForceExecutor(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractFixedForceExecutor(EntityType<?> pEntityType, double pX, double pY, double pZ, Level pLevel, int GM, int boundaryR2, int range) {
        super(pEntityType, pX, pY, pZ, pLevel, GM, boundaryR2, range, 200);
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0.06);
        }
    }

    @Override
    public float getSubspacePower() {
        return 3;
    }
}
