package com.linngdu664.bsf.entity.executor;

import com.linngdu664.bsf.entity.Absorbable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class AbstractExecutor extends Entity implements Absorbable {
    protected int timer;    // both client and server
    protected int maxTime;

    public AbstractExecutor(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractExecutor(EntityType<?> pEntityType, Level pLevel, int maxTime) {
        this(pEntityType, pLevel);
        this.maxTime = maxTime;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        timer = pCompound.getInt("Timer");
        maxTime = pCompound.getInt("MaxTime");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("Timer", timer);
        pCompound.putInt("MaxTime", maxTime);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide && timer == maxTime) {
            discard();
        } else {
            timer++;
        }
    }

    public int getTimer() {
        return timer;
    }
}
