package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.executor.PowderExecutor;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.Fixable;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.EntityRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.registry.ParticleRegister;
import com.linngdu664.bsf.registry.SoundRegister;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class PowderSnowballEntity extends AbstractBSFSnowballEntity implements Fixable {
//    private boolean isStart = false;
//    private int timer = 0;

    public PowderSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public PowderSnowballEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityRegister.POWDER_SNOWBALL.get(), pX, pY, pZ, pLevel);
    }

    public PowderSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.POWDER_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
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

    @Override
    protected void onHitBlock(@NotNull BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);
        Level level = level();
        ((ServerLevel) level).sendParticles(ParticleRegister.BIG_LONG_TIME_SNOWFLAKE.get(), this.getX(), this.getY(), this.getZ(), 25, 0, 0, 0, 0.4);
        level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundRegister.POWDER_SNOWBALL.get(), SoundSource.PLAYERS, 0.3F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
        level.addFreshEntity(getExecutor());
        discard();
//        isStart = true;
//        if (!level.isClientSide) {
//            this.fixLocation = new Vec3(this.getX(), this.getY(), this.getZ());
//            stopTheSnowball();
//            this.setNoGravity(true);
//        }
    }


//    @Override
//    public void tick() {
//        Level level = level();
//        if (!level.isClientSide) {
//            if (isStart) {
//                stopTheSnowball();
//                ((ServerLevel) level).sendParticles(ParticleRegister.BIG_LONG_TIME_SNOWFLAKE.get(), this.getX(), this.getY(), this.getZ(), 8, 0, 0, 0, 0.2);
//                if (timer > 200) {
//                    this.discard();
//                }
//            }
//        }
//        timer++;
//        super.tick();
//    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (isCaught) {
            this.discard();
        }
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.POWDER_SNOWBALL.get();
    }

    @Override
    public Entity getExecutor() {
        return new PowderExecutor(EntityRegister.POWDER_EXECUTOR.get(), getX(), getY(), getZ(), level());
    }
}
