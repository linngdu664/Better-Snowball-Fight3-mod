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

public class HeavyMonsterTrackingSnowballEntity extends AbstractMonsterTrackingSnowballEntity {
    public HeavyMonsterTrackingSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, new BSFSnowballEntityProperties().basicDamage(4).basicBlazeDamage(6), false);
    }

    public HeavyMonsterTrackingSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.HEAVY_MONSTER_TRACKING_SNOWBALL.get(), pShooter, pLevel, new BSFSnowballEntityProperties().basicDamage(4).basicBlazeDamage(6).applyAdjustment(launchAdjustment), false);
    }

//    @Override
//    public float getBasicDamage() {
//        return 4;
//    }
//
//    @Override
//    public float getBasicBlazeDamage() {
//        return 6;
//    }

    @Override
    public float getSubspacePower() {
        return 1.5F;
    }

//    @Override
//    public boolean isLockFeet() {
//        return false;
//    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.HEAVY_MONSTER_TRACKING_SNOWBALL.get();
    }
}
