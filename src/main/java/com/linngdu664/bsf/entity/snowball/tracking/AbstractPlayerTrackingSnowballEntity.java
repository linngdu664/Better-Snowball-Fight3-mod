package com.linngdu664.bsf.entity.snowball.tracking;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.util.BSFMthUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public abstract class AbstractPlayerTrackingSnowballEntity extends AbstractTrackingSnowballEntity {
    public AbstractPlayerTrackingSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractPlayerTrackingSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(pEntityType, pShooter, pLevel, launchAdjustment);
    }

    @Override
    public double getRange() {
        return 10;
    }

    @Override
    public Entity getTarget() {
        Vec3 velocity = getDeltaMovement();
        Level level = level();
        Entity owner = getOwner();
        AABB aabb = getBoundingBox().inflate(getRange());
        List<Player> list = level.getEntitiesOfClass(Player.class, aabb, (p) -> !p.isSpectator() && !p.equals(owner) && !(owner instanceof BSFSnowGolemEntity golem && p.equals(golem.getOwner())) && BSFMthUtil.vec3AngleCos(velocity, new Vec3(p.getX() - getX(), p.getY() - getY(), p.getZ() - getZ())) > 0.5);
        Entity entity = level.getNearestEntity(list, TargetingConditions.DEFAULT, null, getX(), getY(), getZ());
        if (entity == null) {
            List<BSFSnowGolemEntity> list1 = level.getEntitiesOfClass(BSFSnowGolemEntity.class, aabb, (p) -> p.getTarget() != null && (owner instanceof BSFSnowGolemEntity golem && p.getTarget().equals(golem.getOwner()) || !(owner instanceof BSFSnowGolemEntity) && p.getTarget().equals(owner)) && BSFMthUtil.vec3AngleCos(velocity, new Vec3(p.getX() - getX(), p.getY() - getY(), p.getZ() - getZ())) > 0.5);
            return level.getNearestEntity(list1, TargetingConditions.DEFAULT, null, getX(), getY(), getZ());
        }
        return entity;
    }
}
