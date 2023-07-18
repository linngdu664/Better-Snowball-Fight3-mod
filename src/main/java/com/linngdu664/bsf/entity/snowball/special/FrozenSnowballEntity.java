package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.EntityRegister;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.ItemRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class FrozenSnowballEntity extends AbstractFrozenSnowballEntity {
    public FrozenSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public FrozenSnowballEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityRegister.FROZEN_SNOWBALL.get(), pX, pY, pZ, pLevel);
    }

    public FrozenSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.FROZEN_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        setEffects(level(), pResult, Blocks.SNOW.defaultBlockState());
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.FROZEN_SNOWBALL.get();
    }
}
