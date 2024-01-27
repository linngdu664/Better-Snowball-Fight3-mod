package com.linngdu664.bsf.entity.executor;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public abstract class ForceExecutor extends FixExecutor {
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
        forceEffect(getTargetList(), boundaryR2, GM);
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0.06);
            if (timer > 200) {
                this.discard();
            }
            timer++;
        }
    }

    public abstract List<? extends Entity> getTargetList();

    public void forceEffect(List<? extends Entity> list, double constForceRangeSqr, double GM) {
        for (Entity entity : list) {
            Vec3 rVec = new Vec3(getX() - entity.getX(), getY() - (entity.getEyeY() + entity.getY()) * 0.5, getZ() - entity.getZ());
            double r2 = rVec.lengthSqr();
            double ir2 = Mth.invSqrt(r2);
            double a;
            if (r2 > constForceRangeSqr) {
                a = GM / r2;
            } else if (r2 > 0.25) {         // default 0.25
                a = GM / constForceRangeSqr;
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

    @Override
    public float getSubspacePower() {
        return 3;
    }
}
