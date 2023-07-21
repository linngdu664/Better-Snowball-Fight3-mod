package com.linngdu664.bsf.entity.snowball.nomal;

import com.linngdu664.bsf.entity.EntityRegister;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.util.SoundRegister;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class DuckSnowballEntity extends AbstractBSFSnowballEntity {
    public DuckSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public DuckSnowballEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityRegister.DUCK_SNOWBALL.get(), pX, pY, pZ, pLevel);
    }

    public DuckSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.DUCK_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        Level level = level();
        if (!level.isClientSide && !isCaught) {
            discard();
            level.playSound(null, getX(), getY(), getZ(), SoundRegister.DUCK.get(), SoundSource.NEUTRAL, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
        }
    }

    @Override
    public boolean canBeCaught() {
        return true;
    }

    @Override
    public float getBasicDamage() {
        return Float.MIN_NORMAL;
    }

    @Override
    public float getBasicBlazeDamage() {
        return 3;
    }

    @Override
    public int getBasicWeaknessTicks() {
        return 0;
    }

    @Override
    public int getBasicFrozenTicks() {
        return 0;
    }

    @Override
    public double getBasicPunch() {
        return 0;
    }

    @Override
    public float getSubspacePower() {
        return 1;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.DUCK_SNOWBALL.get();
    }
}