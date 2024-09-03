package com.linngdu664.bsf.entity.snowball.nomal;

import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.EntityRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class CherryBlossomSnowballEntity extends AbstractNormalSnowballEntity {
    public CherryBlossomSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, new BSFSnowballEntityProperties());
    }

    public CherryBlossomSnowballEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityRegister.CHERRY_BLOSSOM_SNOWBALL.get(), pX, pY, pZ, pLevel, new BSFSnowballEntityProperties());
    }

    public CherryBlossomSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.CHERRY_BLOSSOM_SNOWBALL.get(), pShooter, pLevel, new BSFSnowballEntityProperties().applyAdjustment(launchAdjustment));
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        Level level = level();
        if (!level.isClientSide && !isCaught) {
            ((ServerLevel) level).sendParticles(ParticleTypes.CHERRY_LEAVES, getX(), getY(), getZ(), 48, 1, 1, 1, 0);
        }
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (level.isClientSide) {
            level.addParticle(ParticleTypes.CHERRY_LEAVES, xo, yo + 0.1, zo, 0, 0, 0);
        }
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.CHERRY_BLOSSOM_SNOWBALL.get();
    }
}
