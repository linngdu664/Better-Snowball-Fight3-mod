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

public class IronSnowballEntity extends AbstractNormalSnowballEntity {
    public IronSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, new BSFSnowballEntityProperties().basicDamage(4).basicBlazeDamage(6));
    }

    public IronSnowballEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityRegister.IRON_SNOWBALL.get(), pX, pY, pZ, pLevel, new BSFSnowballEntityProperties().basicDamage(4).basicBlazeDamage(6));
    }

    public IronSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.IRON_SNOWBALL.get(), pShooter, pLevel, new BSFSnowballEntityProperties().basicDamage(4).basicBlazeDamage(6).applyAdjustment(launchAdjustment));
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
        return 1.4f;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.IRON_SNOWBALL.get();
    }
}
