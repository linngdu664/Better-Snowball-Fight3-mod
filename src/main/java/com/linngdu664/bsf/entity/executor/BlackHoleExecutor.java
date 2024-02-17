package com.linngdu664.bsf.entity.executor;

import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.registry.ParticleRegister;
import com.linngdu664.bsf.util.BSFConfig;
import com.linngdu664.bsf.util.BSFCommonUtil;
import com.linngdu664.bsf.util.ParticleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlackHoleExecutor extends AbstractForceExecutor {
    private int modelTicker;    //client only
    private int tmpRank;        //client only
    public static final int SPINNING_SPEED = 30;
    public static final int OBLIQUITY_RANGE = 30;
    private static final EntityDataAccessor<Integer> RANK = SynchedEntityData.defineId(BlackHoleExecutor.class, EntityDataSerializers.INT);

    //The following EntityDataAccessor they are all for client rendering, only needs to be initialized.
    private static final EntityDataAccessor<Float> ANGLE1 = SynchedEntityData.defineId(BlackHoleExecutor.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> AXIS_X = SynchedEntityData.defineId(BlackHoleExecutor.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> AXIS_Y = SynchedEntityData.defineId(BlackHoleExecutor.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> AXIS_Z = SynchedEntityData.defineId(BlackHoleExecutor.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> PROJECTION_X = SynchedEntityData.defineId(BlackHoleExecutor.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> PROJECTION_Y = SynchedEntityData.defineId(BlackHoleExecutor.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> PROJECTION_Z = SynchedEntityData.defineId(BlackHoleExecutor.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> SHAFT_X = SynchedEntityData.defineId(BlackHoleExecutor.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> SHAFT_Y = SynchedEntityData.defineId(BlackHoleExecutor.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> SHAFT_Z = SynchedEntityData.defineId(BlackHoleExecutor.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> OBLIQUITY = SynchedEntityData.defineId(BlackHoleExecutor.class, EntityDataSerializers.FLOAT);

    public BlackHoleExecutor(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BlackHoleExecutor(EntityType<?> pEntityType, double pX, double pY, double pZ, Level pLevel, Vec3 vel, int maxTime) {
        super(pEntityType, pX, pY, pZ, pLevel, maxTime);
        setRank(pLevel.random.nextInt(30, 50));
        setDeltaMovement(vel);
        setGlowingTag(true);

        // initialized shaft for client
        Vec3 forward = vel.normalize();
        Vec3 projection = new Vec3(vel.x, 0, vel.z).normalize();
        entityData.set(PROJECTION_X, (float) projection.x);
        entityData.set(PROJECTION_Y, (float) projection.y);
        entityData.set(PROJECTION_Z, (float) projection.z);
        entityData.set(ANGLE1, (float) (forward.y > 0 ? BSFCommonUtil.vec3Angle(forward, projection) : -BSFCommonUtil.vec3Angle(forward, projection)));
        Vec3 crossV = forward.lengthSqr() == 0 ? forward.cross(new Vec3(1, 0, 0)).normalize() : forward.cross(new Vec3(0, 1, 0)).normalize();
        entityData.set(AXIS_X, (float) crossV.x);
        entityData.set(AXIS_Y, (float) crossV.y);
        entityData.set(AXIS_Z, (float) crossV.z);
        float obliquity = (float) BSFCommonUtil.randDouble(pLevel.random, -OBLIQUITY_RANGE, OBLIQUITY_RANGE) * Mth.DEG_TO_RAD;
        Vec3 shaftV = crossV.cross(forward).rotateAxis(obliquity, forward.x, forward.y, forward.z);   // todo
        entityData.set(SHAFT_X, (float) shaftV.x);
        entityData.set(SHAFT_Y, (float) shaftV.y);
        entityData.set(SHAFT_Z, (float) shaftV.z);
        entityData.set(OBLIQUITY, obliquity);
    }

    public int getRank() {
        return entityData.get(RANK);
    }

    public void setRank(int rank) {
        entityData.set(RANK, rank);
        range = 6 * Math.sqrt(rank);
        GM = range * range * 0.01;
        boundaryR2 = GM;
    }

    public float getAngle1() {
        return entityData.get(ANGLE1);
    }

    public Vec3 getAxis() {
        return new Vec3(entityData.get(AXIS_X), entityData.get(AXIS_Y), entityData.get(AXIS_Z));
    }

    public Vec3 getProjection() {
        return new Vec3(entityData.get(PROJECTION_X), entityData.get(PROJECTION_Y), entityData.get(PROJECTION_Z));
    }

    public Vec3 getShaft() {
        return new Vec3(entityData.get(SHAFT_X), entityData.get(SHAFT_Y), entityData.get(SHAFT_Z));
    }

    public float getObliquity() {
        return entityData.get(OBLIQUITY);
    }

    public void merge(BlackHoleExecutor another) {
        maxTime += another.maxTime - another.timer;
        setRank((int) ((getRank() + another.getRank()) * 0.95));
        Vec3 vec3 = getDeltaMovement();
        push(-0.5 * vec3.x, -0.5 * vec3.y, -0.5 * vec3.z);
        another.discard();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(RANK, 30);
        entityData.define(ANGLE1, 0f);
        entityData.define(AXIS_X, 1F);
        entityData.define(AXIS_Y, 0F);
        entityData.define(AXIS_Z, 0F);
        entityData.define(PROJECTION_X, 1F);
        entityData.define(PROJECTION_Y, 0F);
        entityData.define(PROJECTION_Z, 0F);
        entityData.define(SHAFT_X, 0F);
        entityData.define(SHAFT_Y, 1F);
        entityData.define(SHAFT_Z, 0F);
        entityData.define(OBLIQUITY, 0f);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        entityData.set(RANK, pCompound.getInt("Rank"));
        entityData.set(ANGLE1, pCompound.getFloat("Angle1"));
        Vec3 axis = BSFCommonUtil.getVec3(pCompound, "Axis");
        entityData.set(AXIS_X, (float) axis.x);
        entityData.set(AXIS_Y, (float) axis.y);
        entityData.set(AXIS_Z, (float) axis.z);
        Vec3 projection = BSFCommonUtil.getVec3(pCompound, "Projection");
        entityData.set(PROJECTION_X, (float) projection.x);
        entityData.set(PROJECTION_Y, (float) projection.y);
        entityData.set(PROJECTION_Z, (float) projection.z);
        Vec3 shaft = BSFCommonUtil.getVec3(pCompound, "Shaft");
        entityData.set(SHAFT_X, (float) shaft.x);
        entityData.set(SHAFT_Y, (float) shaft.y);
        entityData.set(SHAFT_Z, (float) shaft.z);
        entityData.set(OBLIQUITY, pCompound.getFloat("Obliquity"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Rank", getRank());
        pCompound.putFloat("Angle1", getAngle1());
        BSFCommonUtil.putVec3(pCompound, "Axis", getAxis());
        BSFCommonUtil.putVec3(pCompound, "Projection", getProjection());
        BSFCommonUtil.putVec3(pCompound, "Shaft", getShaft());
        pCompound.putFloat("Obliquity", getObliquity());
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 vec3 = getDeltaMovement();
        Vec3 pos = getPosition(0);
        double destroyR = range / 6;
        double destroyR2 = destroyR * destroyR;
        double damageR2 = range * range * 0.01;
        float damage = (float) (range * 0.0528);
        if (!level.isClientSide) {
            if (level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && BSFConfig.blackHoleDestroy) {
                BlockPos.betweenClosedStream(getBoundingBox().inflate(destroyR))
                        .filter(p -> Vec3.atCenterOf(p).distanceToSqr(pos) < destroyR2 && level.getBlockState(p).getBlock().getExplosionResistance() <= 2400)
                        .forEach(p -> {
                            level.destroyBlock(p, BSFConfig.blackHoleDrop);
                            level.setBlockAndUpdate(p, Blocks.AIR.defaultBlockState());
                        });
            }
            targetList.stream()
                    .filter(p -> p.distanceToSqr(pos) < damageR2)
                    .forEach(p -> {
                        if (p instanceof BlackHoleExecutor blackHoleExecutor) {
                            merge(blackHoleExecutor);
                        } else {
                            p.hurt(DamageSource.OUT_OF_WORLD, damage);
                        }
                    });
        } else {
            //for modify model size
            double modelMaxT = Math.sqrt(tmpRank) * 1.7f;
            if (tmpRank > 51 && modelTicker < 2) {
                modelTicker = (int) modelMaxT;
            }
            if (modelTicker < modelMaxT) {
                modelTicker++;
            }

            tmpRank = getRank();

            //for spawn particles
            double splashSize = (double) tmpRank / 400;
            int splashNum = 9 + tmpRank / 40;
            int splashMaxV = 4 + tmpRank / 40;
            Vec3 pos1 = pos.add(splashSize, splashSize, splashSize);
            Vec3 pos2 = pos.subtract(splashSize, splashSize, splashSize);
            Vec3 shaft = getShaft();
            ParticleUtil.spawnForwardRaysParticles(level, ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), pos1, pos2, shaft, vec3, Math.min(2, splashMaxV - 1), splashMaxV, splashNum);
            ParticleUtil.spawnForwardRaysParticles(level, ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), pos1, pos2, shaft.scale(-1), vec3, Math.min(2, splashMaxV - 1), splashMaxV, splashNum);
        }
        setPos(getX() + vec3.x, getY() + vec3.y, getZ() + vec3.z);
    }

    @Override
    public void remove(@NotNull RemovalReason pReason) {
        super.remove(pReason);
        if (pReason.equals(Entity.RemovalReason.DISCARDED) && !level.isClientSide) {
            if (level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && BSFConfig.blackHoleDestroy) {
                level.explode(null, getX(), getY(), getZ(), Math.min(0.56F * (float) Math.sqrt(range) + 2.4F, 12F), Explosion.BlockInteraction.DESTROY);
            } else {
                level.explode(null, getX(), getY(), getZ(), Math.min(0.56F * (float) Math.sqrt(range) + 2.4F, 12F), Explosion.BlockInteraction.NONE);
            }
        }
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double pDistance) {
        double d0 = 96 * getViewScale();
        return pDistance < d0 * d0;
    }

    @Override
    public float getSubspacePower() {
        return 8;
    }

    @Override
    public ItemStack getSnowballItem() {
        return new ItemStack(ItemRegister.BLACK_HOLE_SNOWBALL.get());
    }

    @Override
    public List<? extends Entity> getTargetList() {
        return level.getEntities(this, getBoundingBox().inflate(range), EntitySelector.NO_CREATIVE_OR_SPECTATOR);
    }

    public int getModelTicker() {
        return modelTicker;
    }
}
