package com.linngdu664.bsf.entity.executor;

import com.linngdu664.bsf.entity.Absorbable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class FixExecutor extends Entity implements Absorbable {
    protected int timer;

    public FixExecutor(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public FixExecutor(EntityType<?> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pLevel);
        setPos(pX, pY, pZ);
        setNoGravity(true);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        timer = pCompound.getInt("Timer");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("Timer", timer);
    }

    @Override
    public void tick() {
        setDeltaMovement(0, 0, 0);
        super.tick();
    }

    public int getTimer() {
        return timer;
    }
}
