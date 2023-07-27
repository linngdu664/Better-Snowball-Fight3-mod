package com.linngdu664.bsf.entity.snowball.force;

import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.EntityRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProjectileRepulsionSnowballEntity extends AbstractForceSnowballEntity {
    public ProjectileRepulsionSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ProjectileRepulsionSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.PROJECTILE_REPULSION_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
    }

    @Override
    double getRange() {
        return 15;
    }

    @Override
    double getGM() {
        return -2;
    }

    @Override
    double getBoundaryR2() {
        return 2;
    }

    @Override
    List<? extends Entity> getTargetList() {
        return level().getEntitiesOfClass(Projectile.class, getBoundingBox().inflate(getRange()), (p) -> true);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.PROJECTILE_REPULSION_SNOWBALL.get();
    }
}
