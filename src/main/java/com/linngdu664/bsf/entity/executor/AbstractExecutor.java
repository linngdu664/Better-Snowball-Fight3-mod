package com.linngdu664.bsf.entity.executor;

import com.linngdu664.bsf.entity.Absorbable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class AbstractExecutor extends Entity implements Absorbable {
    private static final EntityDataAccessor<Integer> MAX_TIME = SynchedEntityData.defineId(AbstractExecutor.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TIMER = SynchedEntityData.defineId(AbstractExecutor.class, EntityDataSerializers.INT);

    public AbstractExecutor(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractExecutor(EntityType<?> pEntityType, Level pLevel, int maxTime) {
        this(pEntityType, pLevel);
        entityData.set(MAX_TIME, maxTime);
    }

    public int getTimer() {
        return entityData.get(TIMER);
    }

    public int getMaxTime() {
        return entityData.get(MAX_TIME);
    }

    public void setMaxTime(int maxTime) {
        entityData.set(MAX_TIME, maxTime);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(TIMER, 0);
        entityData.define(MAX_TIME, 0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        entityData.set(TIMER, pCompound.getInt("Timer"));
        entityData.set(MAX_TIME, pCompound.getInt("MaxTime"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("Timer", entityData.get(TIMER));
        pCompound.putInt("MaxTime", entityData.get(MAX_TIME));
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (entityData.get(TIMER).equals(entityData.get(MAX_TIME))) {
                discard();
            } else {
                entityData.set(TIMER, entityData.get(TIMER) + 1);
            }
        }
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double pDistance) {
        double d0 = 32 * getViewScale();
        return pDistance < d0 * d0;
    }
}
