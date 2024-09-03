package com.linngdu664.bsf.entity.executor;

import com.linngdu664.bsf.config.ServerConfig;
import com.linngdu664.bsf.particle.util.ParticleUtil;
import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.registry.ParticleRegister;
import com.linngdu664.bsf.registry.SoundRegister;
import com.linngdu664.bsf.util.BSFCommonUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;

public class BlackHoleExecutor extends AbstractForceExecutor {
    private int tmpRank;        //client only
    public static final int SPINNING_SPEED = 30;
    public static final int OBLIQUITY_RANGE = 30;
    private static final EntityDataAccessor<Integer> RANK = SynchedEntityData.defineId(BlackHoleExecutor.class, EntityDataSerializers.INT);

    //The following EntityDataAccessor they are all for client rendering, only needs to be initialized.
    private static final EntityDataAccessor<Float> ANGLE1 = SynchedEntityData.defineId(BlackHoleExecutor.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Vector3f> AXIS = SynchedEntityData.defineId(BlackHoleExecutor.class, EntityDataSerializers.VECTOR3);
    private static final EntityDataAccessor<Vector3f> PROJECTION = SynchedEntityData.defineId(BlackHoleExecutor.class, EntityDataSerializers.VECTOR3);
    private static final EntityDataAccessor<Vector3f> SHAFT = SynchedEntityData.defineId(BlackHoleExecutor.class, EntityDataSerializers.VECTOR3);
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
        Vector3f forward = vel.toVector3f().normalize();
        Vector3f projection = new Vector3f(forward.x, 0, forward.z).normalize();
        entityData.set(PROJECTION, projection);
        entityData.set(ANGLE1, forward.y > 0 ? forward.angle(projection) : -forward.angle(projection));
        Vector3f crossV = forward.lengthSquared() == 0 ? new Vector3f(forward).cross(1, 0, 0).normalize() : new Vector3f(forward).cross(0, 1, 0).normalize();
        entityData.set(AXIS, crossV);
        float obliquity = (float) BSFCommonUtil.randDouble(pLevel.random, -OBLIQUITY_RANGE, OBLIQUITY_RANGE) * Mth.DEG_TO_RAD;
        entityData.set(SHAFT, new Vector3f(crossV).cross(forward).rotateAxis(obliquity, forward.x, forward.y, forward.z));
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

    public Vector3f getAxis() {
        return entityData.get(AXIS);
    }

    public Vector3f getProjection() {
        return entityData.get(PROJECTION);
    }

    public Vector3f getShaft() {
        return entityData.get(SHAFT);
    }

    public float getObliquity() {
        return entityData.get(OBLIQUITY);
    }

    public void merge(BlackHoleExecutor another) {
        setMaxTime(getMaxTime() + another.getMaxTime() - another.getTimer());
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
        entityData.define(AXIS, new Vector3f(1, 0, 0));
        entityData.define(PROJECTION, new Vector3f(1, 0, 0));
        entityData.define(SHAFT, new Vector3f(0, 1, 0));
        entityData.define(OBLIQUITY, 0f);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        entityData.set(RANK, pCompound.getInt("Rank"));
        entityData.set(ANGLE1, pCompound.getFloat("Angle1"));
        entityData.set(AXIS, BSFCommonUtil.getVec3(pCompound, "Axis"));
        entityData.set(PROJECTION, BSFCommonUtil.getVec3(pCompound, "Projection"));
        entityData.set(SHAFT, BSFCommonUtil.getVec3(pCompound, "Shaft"));
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
        Level level = level();
        Vec3 vec3 = getDeltaMovement();
        Vec3 pos = getPosition(0);
        double destroyR = range / 6;
        double destroyR2 = destroyR * destroyR;
        double damageR2 = range * range * 0.01;
        float damage = (float) (range * 0.0528);
        if (!level.isClientSide) {
            if (getTimer() % 20 == 0) {
                playSound(SoundRegister.BLACK_HOLE_AMBIENCE.get(), 12.0F, 1.0F);
            }
            if (level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && ServerConfig.BLACK_HOLE_DESTROY.getConfigValue()) {
                BlockPos.betweenClosedStream(getBoundingBox().inflate(destroyR))
                        .filter(p -> p.getCenter().distanceToSqr(pos) < destroyR2 && level.getBlockState(p).getBlock().getExplosionResistance() <= 2400)
                        .forEach(p -> {
                            level.destroyBlock(p, ServerConfig.BLACK_HOLE_DROP.getConfigValue());
                            level.setBlockAndUpdate(p, Blocks.AIR.defaultBlockState());
                        });
            }
            targetList.stream()
                    .filter(p -> p.distanceToSqr(pos) < damageR2)
                    .forEach(p -> {
                        if (p instanceof BlackHoleExecutor blackHoleExecutor) {
                            merge(blackHoleExecutor);
                        } else if (p instanceof FallingBlockEntity) {
                            p.discard();
                        } else {
                            p.hurt(level.damageSources().fellOutOfWorld(), damage);
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
            Vector3f shaft = getShaft();
            ParticleUtil.spawnForwardRaysParticles(level, ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), pos1, pos2, new Vec3(shaft), vec3, Math.min(2, splashMaxV - 1), splashMaxV, splashNum);
            ParticleUtil.spawnForwardRaysParticles(level, ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), pos1, pos2, new Vec3(shaft).scale(-1), vec3, Math.min(2, splashMaxV - 1), splashMaxV, splashNum);
        }
        setPos(getX() + vec3.x, getY() + vec3.y, getZ() + vec3.z);
    }

    @Override
    public void remove(@NotNull RemovalReason pReason) {
        super.remove(pReason);
        Level level = level();
        if (pReason.equals(Entity.RemovalReason.DISCARDED) && !level.isClientSide) {
            if (level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && ServerConfig.BLACK_HOLE_DESTROY.getConfigValue()) {
                level.explode(null, getX(), getY(), getZ(), Math.min(0.56F * (float) Math.sqrt(range) + 2.4F, 12F), Level.ExplosionInteraction.TNT);
            } else {
                level.explode(null, getX(), getY(), getZ(), Math.min(0.56F * (float) Math.sqrt(range) + 2.4F, 12F), Level.ExplosionInteraction.NONE);
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
        return level().getEntities(this, getBoundingBox().inflate(range), EntitySelector.NO_CREATIVE_OR_SPECTATOR);
    }
}
