package com.linngdu664.bsf.entity.executor;

import com.linngdu664.bsf.entity.Absorbable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractExecutor extends Entity implements Absorbable {
    protected int timer;    // both client and server
    protected int maxTime;  // server only

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
        if (!level.isClientSide && timer == maxTime) {
            discard();
        } else {
            timer++;
        }
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    public int getTimer() {
        return timer;
    }
}
