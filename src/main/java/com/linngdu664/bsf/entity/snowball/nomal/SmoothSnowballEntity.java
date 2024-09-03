package com.linngdu664.bsf.entity.snowball.nomal;

import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.EntityRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SmoothSnowballEntity extends AbstractNormalSnowballEntity {
    public SmoothSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, new BSFSnowballEntityProperties());
    }

    public SmoothSnowballEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityRegister.SMOOTH_SNOWBALL.get(), pX, pY, pZ, pLevel, new BSFSnowballEntityProperties());
    }

    public SmoothSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.SMOOTH_SNOWBALL.get(), pShooter, pLevel, new BSFSnowballEntityProperties().applyAdjustment(launchAdjustment));
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.SMOOTH_SNOWBALL.get();
    }
}
