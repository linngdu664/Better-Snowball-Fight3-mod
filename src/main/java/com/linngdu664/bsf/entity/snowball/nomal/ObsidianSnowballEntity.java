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

public class ObsidianSnowballEntity extends AbstractNormalSnowballEntity {
    public ObsidianSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ObsidianSnowballEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityRegister.OBSIDIAN_SNOWBALL.get(), pX, pY, pZ, pLevel);
    }

    public ObsidianSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.OBSIDIAN_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
    }

    @Override
    public float getBasicDamage() {
        return 6;
    }

    @Override
    public float getBasicBlazeDamage() {
        return 8;
    }

    @Override
    public float getSubspacePower() {
        return 2.25f;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.OBSIDIAN_SNOWBALL.get();
    }
}
