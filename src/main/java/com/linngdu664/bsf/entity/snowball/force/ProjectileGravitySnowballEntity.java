package com.linngdu664.bsf.entity.snowball.force;

import com.linngdu664.bsf.entity.executor.ProjectileGravityExecutor;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.EntityRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ProjectileGravitySnowballEntity extends AbstractForceSnowballEntity {
    public ProjectileGravitySnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ProjectileGravitySnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.PROJECTILE_GRAVITY_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.PROJECTILE_GRAVITY_SNOWBALL.get();
    }

    @Override
    public Entity getExecutor() {
        return new ProjectileGravityExecutor(EntityRegister.PROJECTILE_GRAVITY_EXECUTOR.get(), getX(), correctedY(), getZ(), level());
    }
}
