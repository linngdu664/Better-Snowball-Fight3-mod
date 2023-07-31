package com.linngdu664.bsf.entity.snowball;

import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractFixableSnowballEntity extends AbstractBSFSnowballEntity{
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
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (fixLocation != null) {
            pCompound.putDouble("fixLocationX", fixLocation.x);
            pCompound.putDouble("fixLocationY", fixLocation.y);
            pCompound.putDouble("fixLocationZ", fixLocation.z);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("fixLocationX")) {
            double x = pCompound.getDouble("fixLocationX");
            double y = pCompound.getDouble("fixLocationY");
            double z = pCompound.getDouble("fixLocationZ");
            fixLocation = new Vec3(x, y, z);
        }
    }

    protected void stopTheSnowball() {
        if (fixLocation!=null && !level().isClientSide){
            this.setPos(fixLocation);
            this.setDeltaMovement(0, 0, 0);
        }

    }
    protected void stopTheSnowball(Vec3 vec3) {
        this.setPos(vec3);
        this.setDeltaMovement(0, 0, 0);
    }
}
