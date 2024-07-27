package com.linngdu664.bsf.entity.snowball.tracking;

import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.EntityRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.util.BSFCommonUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class ExplosivePlayerTrackingSnowballEntity extends AbstractPlayerTrackingSnowballEntity {
    public ExplosivePlayerTrackingSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ExplosivePlayerTrackingSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.EXPLOSIVE_PLAYER_TRACKING_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!level().isClientSide && !isCaught) {
            handleExplosion(1.5F, BSFCommonUtil.getRealHitPosOnMoveVecWithHitResult(this, pResult));
        }
    }

    @Override
    public float getBasicDamage() {
        return 3;
    }

    @Override
    public float getBasicBlazeDamage() {
        return 5;
    }

    @Override
    public float getSubspacePower() {
        return 3.25F;
    }

    @Override
    public boolean isLockFeet() {
        return false;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.EXPLOSIVE_PLAYER_TRACKING_SNOWBALL.get();
    }
}
