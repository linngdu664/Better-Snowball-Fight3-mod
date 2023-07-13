package com.linngdu664.bsf.entity.snowball.nomal;

import com.linngdu664.bsf.entity.EntityRegister;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.ItemRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class IceSnowballEntity extends AbstractBSFSnowballEntity {
    public IceSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public IceSnowballEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityRegister.ICE_SNOWBALL.get(), pX, pY, pZ, pLevel);
    }

    public IceSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.ICE_SNOWBALL.get(), pShooter, pLevel);
        this.launchAdjustment = launchAdjustment;
    }
//    public IceSnowballEntity(LivingEntity livingEntity, Level level, LaunchFunc launchFunc) {
//        super(livingEntity, level);
//        this.setFrozenTicks(60).setLaunchFrom(launchFunc.getLaunchFrom()).setDamage(3).setBlazeDamage(6);
//        launchFunc.launchProperties(this);
//        this.setItem(new ItemStack(ItemRegister.ICE_SNOWBALL.get()));
//    }
//
//    //This is only used for dispenser
//    public IceSnowballEntity(Level level, double x, double y, double z) {
//        super(level, x, y, z);
//        this.setFrozenTicks(60).setDamage(3).setBlazeDamage(6);
//        this.setItem(new ItemStack(ItemRegister.ICE_SNOWBALL.get()));
//    }
//
//    @Override
//    public Item getCorrespondingItem() {
//        return ItemRegister.ICE_SNOWBALL.get();
//    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!level().isClientSide) {
            this.discard();
        }
    }

//    public float getPower() {
//        return 1.2f;
//    }

    @Override
    public boolean canBeCaught() {
        return true;
    }

    @Override
    public float getBasicDamage() {
        return 3;
    }

    @Override
    public float getBasicBlazeDamage() {
        return 6;
    }

    @Override
    public int getBasicWeaknessTicks() {
        return 0;
    }

    @Override
    public int getBasicFrozenTicks() {
        return 60;
    }

    @Override
    public double getBasicPunch() {
        return 0;
    }

    @Override
    public float getSubspacePower() {
        return 1.2f;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.ICE_SNOWBALL.get();
    }
}
