package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.BlockRegister;
import com.linngdu664.bsf.registry.EntityRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class CriticalFrozenSnowballEntity extends AbstractFrozenSnowballEntity {
    public CriticalFrozenSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public CriticalFrozenSnowballEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityRegister.CRITICAL_FROZEN_SNOWBALL.get(), pX, pY, pZ, pLevel);
    }

    public CriticalFrozenSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.CRITICAL_FROZEN_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
    }


    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        setEffects(level(), pResult, BlockRegister.CRITICAL_SNOW.get().defaultBlockState());
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.CRITICAL_FROZEN_SNOWBALL.get();
    }
}
