package com.linngdu664.bsf.entity.executor;

import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.registry.ParticleRegister;
import com.linngdu664.bsf.util.BSFConfig;
import com.linngdu664.bsf.util.ParticleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlackHoleExecutor extends AbstractForceExecutor {
    private static final Vec3 SPLASH_VEC3_1 = new Vec3(-3, 6, 1.732050807568877);
    private static final Vec3 SPLASH_VEC3_2 = new Vec3(3, -6, -1.732050807568877);
    private static final EntityDataAccessor<Integer> RANK = SynchedEntityData.defineId(BlackHoleExecutor.class, EntityDataSerializers.INT);

    public BlackHoleExecutor(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BlackHoleExecutor(EntityType<?> pEntityType, double pX, double pY, double pZ, Level pLevel, Vec3 vel, int maxTime) {
        super(pEntityType, pX, pY, pZ, pLevel, maxTime);
        setRank(pLevel.random.nextInt(30, 50));
        setDeltaMovement(vel);
        setGlowingTag(true);
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
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        entityData.set(RANK, pCompound.getInt("Rank"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Rank", getRank());
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
        if (!level.isClientSide) {
            if (level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && BSFConfig.destroyMode) {
                BlockPos.betweenClosedStream(getBoundingBox().inflate(destroyR))
                        .filter(p -> p.getCenter().distanceToSqr(pos) < destroyR2 && level.getBlockState(p).getBlock().getExplosionResistance() < 1200)
                        .forEach(p -> {
                            BlockState blockState = level.getBlockState(p);
                            BlockEntity blockentity = blockState.hasBlockEntity() ? level.getBlockEntity(p) : null;
                            Block.dropResources(blockState, level, p, blockentity, null, ItemStack.EMPTY);
                            level.setBlockAndUpdate(p, Blocks.AIR.defaultBlockState());
                        });
            }
            targetList.stream()
                    .filter(p -> p.distanceToSqr(pos) < damageR2)
                    .forEach(p -> {
                        if (p instanceof BlackHoleExecutor blackHoleExecutor) {
                            merge(blackHoleExecutor);
                        } else {
                            p.hurt(level.damageSources().fellOutOfWorld(), 2);
                        }
                    });
        } else {
            int rank = getRank();
            double splashSize = (double) rank / 400;
            int splashNum = 9 + rank / 40;
            int splashMaxV = 4 + rank / 40;
            Vec3 pos1 = pos.add(splashSize, splashSize, splashSize);
            Vec3 pos2 = pos.subtract(splashSize, splashSize, splashSize);
            ParticleUtil.spawnForwardRaysParticles(level, ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), pos1, pos2, SPLASH_VEC3_1, vec3, Math.min(2, splashMaxV - 1), splashMaxV, splashNum);
            ParticleUtil.spawnForwardRaysParticles(level, ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), pos1, pos2, SPLASH_VEC3_2, vec3, Math.min(2, splashMaxV - 1), splashMaxV, splashNum);
        }
        setPos(getX() + vec3.x, getY() + vec3.y, getZ() + vec3.z);
    }

    @Override
    public void remove(@NotNull RemovalReason pReason) {
        super.remove(pReason);
        Level level = level();
        if (pReason.equals(Entity.RemovalReason.DISCARDED) && !level.isClientSide) {
            if (level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && BSFConfig.destroyMode) {
                level.explode(null, getX(), getY(), getZ(), Math.min(0.56F * (float) Math.sqrt(range) + 2.4F, 12F), Level.ExplosionInteraction.TNT);
            } else {
                level.explode(null, getX(), getY(), getZ(), Math.min(0.56F * (float) Math.sqrt(range) + 2.4F, 12F), Level.ExplosionInteraction.NONE);
            }
        }
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
