package com.linngdu664.bsf.entity.snowball.force;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.Fixable;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractForceSnowballEntity extends AbstractBSFSnowballEntity implements Fixable {
//    public boolean isStart = false;
//    private int timer = 0;

    public AbstractForceSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractForceSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(pEntityType, pShooter, pLevel, launchAdjustment);
    }

//    @Override
//    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
//        super.addAdditionalSaveData(pCompound);
//        pCompound.putInt("Timer", timer);
//        pCompound.putBoolean("IsStart", isStart);
//    }
//
//    @Override
//    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
//        super.readAdditionalSaveData(pCompound);
//        timer = pCompound.getInt("Timer");
//        isStart = pCompound.getBoolean("IsStart");
//    }

    /*
    @Override
    public void tick() {
        Level level = level();

        if (!level.isClientSide) {
            if (isStart) {
                stopTheSnowball();
                ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0.06);
                if (timer > 200) {
                    this.discard();
                }
            }
            forceEffect(getTargetList(), getBoundaryR2(), getGM());
        }
        timer++;
        super.tick();
    }*/

    @Override
    protected void onHitBlock(@NotNull BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);
        level().addFreshEntity(getExecutor());
        discard();
//        isStart = true;
//        this.fixLocation = new Vec3(this.getX(), this.getY(), this.getZ());
//        stopTheSnowball();
//        this.setNoGravity(true);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (isCaught) {
            this.discard();
        }
    }

    @Override
    public float getSubspacePower() {
        return 3;
    }

//    abstract double getRange();
//
//    abstract List<? extends Entity> getTargetList();
//
//    abstract double getGM();
//
//    abstract double getBoundaryR2();
}
