package com.linngdu664.bsf.entity.executor;

import com.linngdu664.bsf.entity.Forceable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class ForceExecutor extends FixExecutor implements Forceable {
    private int GM;
    private int boundaryR2;
    protected int range;

    public ForceExecutor(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        setNoGravity(true);
    }

    public ForceExecutor(EntityType<?> pEntityType, double pX, double pY, double pZ, Level pLevel, int GM, int boundaryR2, int range) {
        super(pEntityType, pLevel);
        setPos(pX, pY, pZ);
        setNoGravity(true);
        this.range = range;
        this.boundaryR2 = boundaryR2;
        this.GM = GM;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        GM = pCompound.getInt("GM");
        boundaryR2 = pCompound.getInt("BoundaryR2");
        range = pCompound.getInt("Range");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("GM", GM);
        pCompound.putInt("BoundaryR2", boundaryR2);
        pCompound.putInt("Range", range);
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        forceEffect(this, getTargetList(), boundaryR2, GM);
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0.06);
            if (timer > 200) {
                this.discard();
            }
        }
        timer++;
    }

    public abstract List<? extends Entity> getTargetList();

    @Override
    public float getSubspacePower() {
        return 3;
    }
}
