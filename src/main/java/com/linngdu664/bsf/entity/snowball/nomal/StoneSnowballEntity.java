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

public class StoneSnowballEntity extends AbstractBSFSnowballEntity {
    public StoneSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public StoneSnowballEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityRegister.STONE_SNOWBALL.get(), pX, pY, pZ, pLevel);
    }

    public StoneSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.STONE_SNOWBALL.get(), pShooter, pLevel);
        this.launchAdjustment = launchAdjustment;
    }
//    public StoneSnowballEntity(LivingEntity livingEntity, Level level, LaunchFunc launchFunc) {
//        super(livingEntity, level);
//        this.setPunch(2.0).setLaunchFrom(launchFunc.getLaunchFrom()).setDamage(2).setBlazeDamage(4);
//        launchFunc.launchProperties(this);
//        this.setItem(new ItemStack(ItemRegister.STONE_SNOWBALL.get()));
//    }
//
//    //This is only used for dispenser
//    public StoneSnowballEntity(Level level, double x, double y, double z) {
//        super(level, x, y, z);
//        this.setDamage(2).setBlazeDamage(4);
//        this.setItem(new ItemStack(ItemRegister.STONE_SNOWBALL.get()));
//    }
//
//    @Override
//    public Item getCorrespondingItem() {
//        return ItemRegister.STONE_SNOWBALL.get();
//    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!level().isClientSide) {
            this.discard();
        }
    }
    @Override
    public boolean canBeCaught() {
        return true;
    }

    @Override
    public float getBasicDamage() {
        return 2;
    }

    @Override
    public float getBasicBlazeDamage() {
        return 4;
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
        return 1.1f;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.STONE_SNOWBALL.get();
    }
}
