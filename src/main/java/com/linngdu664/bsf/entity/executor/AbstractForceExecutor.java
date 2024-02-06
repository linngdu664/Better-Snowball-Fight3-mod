package com.linngdu664.bsf.entity.executor;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public abstract class AbstractForceExecutor extends AbstractExecutor {
    private double GM;
    private double boundaryR2;
    protected double range;
    protected List<? extends Entity> targetList;

    public AbstractForceExecutor(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractForceExecutor(EntityType<?> pEntityType, double pX, double pY, double pZ, Level pLevel, double GM, double boundaryR2, double range, int maxTime) {
        super(pEntityType, pLevel, maxTime);
        setPos(pX, pY, pZ);
        this.range = range;
        this.boundaryR2 = boundaryR2;
        this.GM = GM;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        GM = pCompound.getDouble("GM");
        boundaryR2 = pCompound.getDouble("BoundaryR2");
        range = pCompound.getDouble("Range");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putDouble("GM", GM);
        pCompound.putDouble("BoundaryR2", boundaryR2);
        pCompound.putDouble("Range", range);
    }

    @Override
    public void tick() {
        super.tick();
        targetList = getTargetList();
        forceEffect();
    }

    protected void forceEffect() {
        for (Entity entity : targetList) {
            Vec3 rVec = new Vec3(getX() - entity.getX(), getY() - (entity.getEyeY() + entity.getY()) * 0.5, getZ() - entity.getZ());
            double r2 = rVec.lengthSqr();
            double ir2 = Mth.invSqrt(r2);
            double a;
            if (r2 > boundaryR2) {
                a = GM / r2;
            } else if (r2 > 0.25) {         // default 0.25
                a = GM / boundaryR2;
            } else {
                a = 0;
            }
            entity.push(a * rVec.x * ir2, a * rVec.y * ir2, a * rVec.z * ir2);
            //Tell client that player should move because client handles player's movement.
            if (entity instanceof ServerPlayer player) {
                player.connection.send(new ClientboundSetEntityMotionPacket(entity));
            }
        }
    }

    public abstract List<? extends Entity> getTargetList();
}
