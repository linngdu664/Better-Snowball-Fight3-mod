package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.block.LooseSnowBlock;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.BlockRegister;
import com.linngdu664.bsf.registry.ParticleRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class AbstractConstructSnowballEntity extends AbstractBSFSnowballEntity {
    private static final EntityDataAccessor<Boolean> INVISIBLE = SynchedEntityData.defineId(AbstractConstructSnowballEntity.class, EntityDataSerializers.BOOLEAN);
    protected final Stack<BlockPos> allBlock = new Stack<>();
    protected int blockDurationTick;    // default: 20 * 4
    protected float destroyStepSize = 5;
    protected boolean inBlockDuration = false;
    private boolean inDestroying = false;


    public AbstractConstructSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel, int duration) {
        super(pEntityType, pLevel);
        this.blockDurationTick = duration;
    }

    public AbstractConstructSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment, int duration) {
        super(pEntityType, pShooter, pLevel, launchAdjustment);
        this.blockDurationTick = duration;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(INVISIBLE, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("Invisible", getInvisible());
        pCompound.putInt("BlockDurationTick", blockDurationTick);
        pCompound.putFloat("DestroyStepSize", destroyStepSize);
        pCompound.putBoolean("InBlockDuration", inBlockDuration);
        pCompound.putBoolean("InDestroying", inDestroying);

        List<Integer> tmpList = new ArrayList<>();
        for (BlockPos blockPos : allBlock) {
            tmpList.add(blockPos.getX());
            tmpList.add(blockPos.getY());
            tmpList.add(blockPos.getZ());
        }
        pCompound.putIntArray("AllBlock", tmpList);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setInvisible(pCompound.getBoolean("Invisible"));
        blockDurationTick = pCompound.getInt("BlockDurationTick");
        destroyStepSize = pCompound.getFloat("DestroyStepSize");
        inBlockDuration = pCompound.getBoolean("InBlockDuration");
        inDestroying = pCompound.getBoolean("InDestroying");
        int[] tmpArr = pCompound.getIntArray("AllBlock");
        if (tmpArr.length % 3 == 0) {
            for (int i = tmpArr.length - 1; i > 0; i -= 3) {
                allBlock.push(new BlockPos(tmpArr[i - 2], tmpArr[i - 1], tmpArr[i]));
            }
        }
    }


    public boolean getInvisible() {
        return entityData.get(INVISIBLE);
    }

    public void setInvisible(boolean invisible) {
        entityData.set(INVISIBLE, invisible);
    }

    @Override
    public void tick() {
        if (level().isClientSide && getInvisible()) {
            this.discard();
        }
        if (inBlockDuration) {
            if (--blockDurationTick < 1) {
                startDestroyBlock();
            }
            this.setDeltaMovement(0, 0, 0);
            //stopTheSnowball();
        } else if (inDestroying) {
            Level level = level();
            if (!level.isClientSide) {
                for (int i = 0; i < destroyStepSize && !allBlock.isEmpty(); i++) {
                    destroyBlock(level, allBlock.pop());
                }
                if (allBlock.isEmpty()) {
                    this.discard();

                }
            }
            this.setDeltaMovement(0, 0, 0);
            //stopTheSnowball();
        }
        super.tick();
    }

    @Override
    public void remove(@NotNull RemovalReason pReason) {
        while (!allBlock.isEmpty()) {
            destroyBlock(level(), allBlock.pop());
        }
        super.remove(pReason);
    }

    /**
     * Call this method to trigger the delay (blockDurationTick) time to delete all blocks, and makes snowball invisible.
     *
     * @param fixLocation Snowball will be pinned to this point
     */
    public void startTimingOfDiscard(Vec3 fixLocation) {
        setInvisible(true);
        if (!inBlockDuration && !inDestroying) {

            inBlockDuration = true;
            this.setNoGravity(true);
        }
//        this.fixLocation = fixLocation;
    }

    /**
     * All subclasses of Snowball must use this method to generate blocks
     *
     * @param level    level
     * @param blockPos blockPos
     */
    protected void placeAndRecordBlock(Level level, BlockPos blockPos) {
        if (level instanceof ServerLevel) {
            if (level.getBlockState(blockPos).canBeReplaced()) {
                level.setBlock(blockPos, BlockRegister.LOOSE_SNOW_BLOCK.get().defaultBlockState(), 3);
                allBlock.push(blockPos);
            }
//            if (map == null) {
//                data = serverLevel.getDataStorage().computeIfAbsent(SnowDataStorage::new, SnowDataStorage::new, "bsf_snow_data");
//                map = data.getMap();
//            }
//            map.put(SnowDataStorage.posToString(blockPos), this.getUUID());
//            data.setDirty();
        }
    }

    @Override
    protected void generateParticles() {
        if (!inBlockDuration) {
            Level level = level();
            if (!level.isClientSide) {
                ((ServerLevel) level).sendParticles(ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
            }
        }
    }


    public void startDestroyBlock() {
        inBlockDuration = false;
        inDestroying = true;
    }

    private void destroyBlock(Level level, BlockPos pos) {
        if (posIsLooseSnow(level, pos)) {
            if (level instanceof ServerLevel) {
//                BlockState blockState = level.getBlockState(pos);
                level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.SNOW_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                BlockState snow = Blocks.SNOW.defaultBlockState();
                if (snow.canSurvive(level, pos) && !posIsLooseSnow(level, pos.below())) {
                    level.setBlockAndUpdate(pos, snow);
                }
                ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, pos.getX(), pos.getY(), pos.getZ(), 5, 0, 0, 0, 0.12);
            }

        }
    }


    protected boolean posIsLooseSnow(Level level, BlockPos pos) {
        return level.getBlockState(pos).getBlock() instanceof LooseSnowBlock;
    }

}
