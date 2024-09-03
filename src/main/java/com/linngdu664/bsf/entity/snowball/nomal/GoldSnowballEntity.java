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

public class GoldSnowballEntity extends AbstractNormalSnowballEntity {
    public GoldSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, new BSFSnowballEntityProperties().basicDamage(5).basicBlazeDamage(7));
    }

    public GoldSnowballEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityRegister.GOLD_SNOWBALL.get(), pX, pY, pZ, pLevel, new BSFSnowballEntityProperties().basicDamage(5).basicBlazeDamage(7));
    }

    public GoldSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.GOLD_SNOWBALL.get(), pShooter, pLevel, new BSFSnowballEntityProperties().basicDamage(5).basicBlazeDamage(7).applyAdjustment(launchAdjustment));
    }

//    @Override
//    public float getBasicDamage() {
//        return 5;
//    }
//
//    @Override
//    public float getBasicBlazeDamage() {
//        return 7;
//    }

    @Override
    public float getSubspacePower() {
        return 1.5f;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.GOLD_SNOWBALL.get();
    }
}
