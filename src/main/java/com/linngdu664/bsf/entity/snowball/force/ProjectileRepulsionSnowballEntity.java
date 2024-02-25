package com.linngdu664.bsf.entity.snowball.force;

import com.linngdu664.bsf.entity.executor.ProjectileRepulsionExecutor;
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

public class ProjectileRepulsionSnowballEntity extends AbstractForceSnowballEntity {
    public ProjectileRepulsionSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ProjectileRepulsionSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.PROJECTILE_REPULSION_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.PROJECTILE_REPULSION_SNOWBALL.get();
    }

    @Override
    public Entity getExecutor() {
        return new ProjectileRepulsionExecutor(EntityRegister.PROJECTILE_REPULSION_EXECUTOR.get(), getX(), correctedY(), getZ(), level());
    }
}
