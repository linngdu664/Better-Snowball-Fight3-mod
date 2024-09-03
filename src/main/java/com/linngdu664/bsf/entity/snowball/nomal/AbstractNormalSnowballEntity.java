package com.linngdu664.bsf.entity.snowball.nomal;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractNormalSnowballEntity extends AbstractBSFSnowballEntity {
    public AbstractNormalSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel, BSFSnowballEntityProperties pProperties) {
        super(pEntityType, pLevel, pProperties);
    }

    public AbstractNormalSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel, BSFSnowballEntityProperties pProperties) {
        super(pEntityType, pX, pY, pZ, pLevel, pProperties);
    }

    public AbstractNormalSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel, BSFSnowballEntityProperties pProperties) {
        super(pEntityType, pShooter, pLevel, pProperties);
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!level().isClientSide) {
            this.discard();
        }
    }
}
