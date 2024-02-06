package com.linngdu664.bsf.entity.snowball.tracking;

import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.util.BSFMthUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public abstract class AbstractMonsterTrackingSnowballEntity extends AbstractTrackingSnowballEntity {
    public AbstractMonsterTrackingSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractMonsterTrackingSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(pEntityType, pShooter, pLevel, launchAdjustment);
    }

    @Override
    public Entity getTarget() {
        Level level = level();
        Vec3 velocity = getDeltaMovement();
        List<Mob> list = level.getEntitiesOfClass(Mob.class, getBoundingBox().inflate(getRange()), (p) -> p instanceof Enemy && BSFMthUtil.vec3AngleCos(velocity, p.getPosition(0).subtract(getPosition(0))) > 0.5);
        return level.getNearestEntity(list, TargetingConditions.DEFAULT, null, getX(), getY(), getZ());
    }
}
