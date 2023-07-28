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

import java.util.Stack;

public abstract class AbstractConstructSnowballEntity extends AbstractBSFSnowballEntity {
    private static final EntityDataAccessor<Boolean> INVISIBLE = SynchedEntityData.defineId(AbstractConstructSnowballEntity.class, EntityDataSerializers.BOOLEAN);
    protected int blockDurationTick = 20*4;

    protected int destroyStepSize = 5;
    protected boolean inBlockDuration = false;

    private boolean inDestroying = false;

    private Vec3 fixLocation;

    protected Stack<BlockPos> allBlock = new Stack<>();



    public AbstractConstructSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractConstructSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(pEntityType, pShooter, pLevel, launchAdjustment);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(INVISIBLE,false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("invisible",getInvisible());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setInvisible(pCompound.getBoolean("invisible"));
    }

    public boolean getInvisible(){
        return entityData.get(INVISIBLE);
    }
    public void setInvisible(boolean invisible){
        entityData.set(INVISIBLE,invisible);
    }

    @Override
    public void tick() {
        if (level().isClientSide&&getInvisible()){
            this.discard();
        }
        if (inBlockDuration){
            if (--blockDurationTick<1){
                startDestroyBlock();
            }
            stopTheSnowball();
        }else if (inDestroying){
            Level level = level();
            if (!level.isClientSide){
                for (int i = 0; i < destroyStepSize && !allBlock.isEmpty(); i++) {
                    destroyBlock(level,allBlock.pop());
                }
                if (allBlock.isEmpty()){
                    this.discard();
                }
            }
        }
        super.tick();
    }

    @Override
    public void remove(RemovalReason pReason) {
        while (!allBlock.isEmpty()) {
            destroyBlock(level(),allBlock.pop());
        }
        super.remove(pReason);
    }

    /**
     * Call this method to trigger the delay (blockDurationTick) time to delete all blocks, and makes snowball invisible.
     * @param fixLocation Snowball will be pinned to this point
     */
    public void startTimingOfDiscard(Vec3 fixLocation){
        setInvisible(true);
        if (!inBlockDuration&&!inDestroying){
            this.fixLocation=fixLocation;
            inBlockDuration=true;
            this.setNoGravity(true);
        }


    }

    /**
     * All subclasses of Snowball must use this method to generate blocks
     * @param level
     * @param blockPos
     */
    protected void placeAndRecordBlock(Level level, BlockPos blockPos){
        allBlock.push(blockPos);
        level.setBlock(blockPos, BlockRegister.LOOSE_SNOW_BLOCK.get().defaultBlockState(), 3);
    }
    @Override
    protected void generateParticles() {
        if (!inBlockDuration){
            Level level = level();
            if (!level.isClientSide) {
                ((ServerLevel) level).sendParticles(ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
            }
        }
    }


    public void startDestroyBlock(){
        inBlockDuration=false;
        inDestroying=true;
    }
    private void destroyBlock(Level level,BlockPos pos){
        if (posIsLooseSnow(level,pos)){
            BlockState blockState = level.getBlockState(pos);
            if (blockState.getValue(LooseSnowBlock.FROZEN)==1){
                level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL, 0.4F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            }else{
                level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.SNOW_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            }
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            BlockState snow = Blocks.SNOW.defaultBlockState();
            if (blockState.canBeReplaced() && snow.canSurvive(level, pos) && !posIsLooseSnow(level,pos.below())) {
                level.setBlockAndUpdate(pos, snow);
            }

            ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, pos.getX(), pos.getY(), pos.getZ(), 5, 0, 0, 0, 0.12);
        }
    }

    /**
     *It is forbidden to call when fixLocation is not set
     */
    protected void stopTheSnowball() {
        this.setPos(fixLocation);
        this.setDeltaMovement(0,0,0);
    }
    protected void stopTheSnowball(Vec3 vec3) {
        this.setPos(vec3);
        this.setDeltaMovement(0,0,0);
    }
    protected boolean posIsLooseSnow(Level level,BlockPos pos){
        return level.getBlockState(pos).getBlock() instanceof LooseSnowBlock;
    }

}
