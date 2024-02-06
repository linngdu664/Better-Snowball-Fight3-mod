package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.executor.BlackHoleExecutor;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.EntityRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.registry.SoundRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class BlackHoleSnowballEntity extends AbstractBSFSnowballEntity {
    public int startTime = 20;
    public int endTime = 150;
    private int timer = 0;

    public BlackHoleSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setNoGravity(true);
    }

    public BlackHoleSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.BLACK_HOLE_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
        this.setNoGravity(true);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("StartTime", startTime);
        pCompound.putInt("EndTime", endTime);
        pCompound.putInt("Timer", timer);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        startTime = pCompound.getInt("StartTime");
        endTime = pCompound.getInt("EndTime");
        timer = pCompound.getInt("Timer");
    }

//    @Override
//    protected void onHitBlock(@NotNull BlockHitResult p_37258_) {
//        super.onHitBlock(p_37258_);
//        handleExplosion(6.0F);
//        if (!level().isClientSide) {
//            this.discard();
//        }
//    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        startBlackHole();
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (!level.isClientSide) {
            if (timer == startTime) {
                startBlackHole();
//                Vec3 vec3 = this.getDeltaMovement();
//                this.push(vec3.x * -0.75, vec3.y * -0.75, vec3.z * -0.75);
//                ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 200, 0, 0, 0, 0.32);
//                this.playSound(SoundRegister.BLACK_HOLE_START.get(), 3.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            }
//            if (timer > startTime) {
//                forceEffect(this, level.getEntitiesOfClass(Entity.class, getBoundingBox().inflate(30), EntitySelector.NO_CREATIVE_OR_SPECTATOR), 8, 8);
//                ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY(), this.getZ(), 8, 0, 0, 0, 0.12);
//            }
//            if (timer == endTime) {
//                handleExplosion(6.0F);
//                this.discard();
//            }
        }
        timer++;
    }

    private void startBlackHole() {
        discard();
        Level level = level();
        Vec3 vec3 = getDeltaMovement();
        push(vec3.x * -0.75, vec3.y * -0.75, vec3.z * -0.75);
        ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 200, 0, 0, 0, 0.32);
        playSound(SoundRegister.BLACK_HOLE_START.get(), 3.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
        level.addFreshEntity(new BlackHoleExecutor(EntityRegister.POWDER_EXECUTOR.get(), getX(), getY(), getZ(), level(), getDeltaMovement(), endTime - startTime));
    }

    @Override
    public boolean canBeCaught() {
        return false;
    }

    @Override
    public float getBasicDamage() {
        return 4;
    }

    @Override
    public float getBasicBlazeDamage() {
        return 6;
    }

    @Override
    public float getSubspacePower() {
        return 8;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.BLACK_HOLE_SNOWBALL.get();
    }
}
