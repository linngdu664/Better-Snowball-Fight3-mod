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

public class IceSnowballEntity extends AbstractNormalSnowballEntity {
    public IceSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, new BSFSnowballEntityProperties().basicDamage(3).basicBlazeDamage(6).basicFrozenTicks(40));
    }

    public IceSnowballEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityRegister.ICE_SNOWBALL.get(), pX, pY, pZ, pLevel, new BSFSnowballEntityProperties().basicDamage(3).basicBlazeDamage(6).basicFrozenTicks(40));
    }

    public IceSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.ICE_SNOWBALL.get(), pShooter, pLevel, new BSFSnowballEntityProperties().basicDamage(3).basicBlazeDamage(6).basicFrozenTicks(40).applyAdjustment(launchAdjustment));
    }

//    @Override
//    public float getBasicDamage() {
//        return 3;
//    }
//
//    @Override
//    public float getBasicBlazeDamage() {
//        return 6;
//    }
//
//    @Override
//    public int getBasicFrozenTicks() {
//        return 60;
//    }

    @Override
    public float getSubspacePower() {
        return 1.2f;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.ICE_SNOWBALL.get();
    }
}
