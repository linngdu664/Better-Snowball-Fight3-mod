package com.linngdu664.bsf.entity.snowball;

import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Deprecated
public abstract class AbstractFixableSnowballEntity extends AbstractBSFSnowballEntity {
    protected Vec3 fixLocation;

    public AbstractFixableSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractFixableSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(pEntityType, pShooter, pLevel, launchAdjustment);
    }

    public AbstractFixableSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (fixLocation != null) {
            pCompound.putDouble("FixLocationX", fixLocation.x);
            pCompound.putDouble("FixLocationY", fixLocation.y);
            pCompound.putDouble("FixLocationZ", fixLocation.z);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("FixLocationX")) {
            double x = pCompound.getDouble("FixLocationX");
            double y = pCompound.getDouble("FixLocationY");
            double z = pCompound.getDouble("FixLocationZ");
            fixLocation = new Vec3(x, y, z);
        }
    }

    protected void stopTheSnowball() {
        if (fixLocation != null && !level().isClientSide) {
            this.setPos(fixLocation);
            this.setDeltaMovement(0, 0, 0);
        }
    }

    protected void stopTheSnowball(Vec3 vec3) {
        this.setPos(vec3);
        this.setDeltaMovement(0, 0, 0);
    }
}
