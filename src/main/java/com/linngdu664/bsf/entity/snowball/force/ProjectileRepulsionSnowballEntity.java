package com.linngdu664.bsf.entity.snowball.force;

import com.linngdu664.bsf.entity.EntityRegister;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.ItemRegister;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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
    public boolean canBeCaught() {
        return true;
    }

    @Override
    public float getBasicDamage() {
        return Float.MIN_NORMAL;
    }

    @Override
    public float getBasicBlazeDamage() {
        return 3;
    }

    @Override
    public int getBasicWeaknessTicks() {
        return 0;
    }

    @Override
    public int getBasicFrozenTicks() {
        return 0;
    }

    @Override
    public double getBasicPunch() {
        return 0;
    }

    @Override
    public float getSubspacePower() {
        return 3;
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
        return level().getEntitiesOfClass(Mob.class, getBoundingBox().inflate(getRange()), (p) -> true);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.PROJECTILE_REPULSION_SNOWBALL.get();
    }
}
