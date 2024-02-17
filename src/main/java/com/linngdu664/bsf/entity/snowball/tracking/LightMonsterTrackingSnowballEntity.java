package com.linngdu664.bsf.entity.snowball.tracking;

import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.EntityRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class LightMonsterTrackingSnowballEntity extends AbstractMonsterTrackingSnowballEntity {
    public LightMonsterTrackingSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public LightMonsterTrackingSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.LIGHT_MONSTER_TRACKING_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
    }

    @Override
    public boolean isLockFeet() {
        return false;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.LIGHT_MONSTER_TRACKING_SNOWBALL.get();
    }
}
