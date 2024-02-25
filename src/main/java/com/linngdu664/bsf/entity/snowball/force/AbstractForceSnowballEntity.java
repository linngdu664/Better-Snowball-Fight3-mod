package com.linngdu664.bsf.entity.snowball.force;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.SoundRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractForceSnowballEntity extends AbstractBSFSnowballEntity {
    public AbstractForceSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractForceSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(pEntityType, pShooter, pLevel, launchAdjustment);
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);
        Level level = level();
        if (!level.isClientSide){
            playSound(SoundRegister.FORCE_EXECUTOR_START.get(), 3.0F, 1.0F);
            level.addFreshEntity(getExecutor());
            discard();
//        isStart = true;
//        this.fixLocation = new Vec3(this.getX(), this.getY(), this.getZ());
//        stopTheSnowball();
//        this.setNoGravity(true);
        }
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

    public abstract Entity getExecutor();

    public double correctedY() {
        BlockPos blockPos = new BlockPos(Mth.floor(getX()), Mth.floor(getY() - 0.5), Mth.floor(getZ()));
        return level().getBlockState(blockPos).canBeReplaced() ? getY() : blockPos.getY() + 1.5;
    }
}
