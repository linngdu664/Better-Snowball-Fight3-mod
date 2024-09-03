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

public class StoneSnowballEntity extends AbstractNormalSnowballEntity {
    public StoneSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, new BSFSnowballEntityProperties().basicDamage(2).basicBlazeDamage(4));
    }

    public StoneSnowballEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityRegister.STONE_SNOWBALL.get(), pX, pY, pZ, pLevel, new BSFSnowballEntityProperties().basicDamage(2).basicBlazeDamage(4));
    }

    public StoneSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.STONE_SNOWBALL.get(), pShooter, pLevel, new BSFSnowballEntityProperties().basicDamage(2).basicBlazeDamage(4).applyAdjustment(launchAdjustment));
    }

//    @Override
//    public float getBasicDamage() {
//        return 2;
//    }
//
//    @Override
//    public float getBasicBlazeDamage() {
//        return 4;
//    }

    @Override
    public float getSubspacePower() {
        return 1.1f;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.STONE_SNOWBALL.get();
    }
}
