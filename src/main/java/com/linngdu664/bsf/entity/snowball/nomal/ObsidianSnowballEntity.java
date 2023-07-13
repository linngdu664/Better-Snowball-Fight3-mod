package com.linngdu664.bsf.entity.snowball.nomal;

import com.linngdu664.bsf.entity.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.BSFSnowballEntity;
import com.linngdu664.bsf.entity.EntityRegister;
import com.linngdu664.bsf.entity.ILaunchAdjustment;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.util.LaunchFunc;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class ObsidianSnowballEntity extends AbstractBSFSnowballEntity {
    public ObsidianSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ObsidianSnowballEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityRegister.OBSIDIAN_SNOWBALL.get(), pX, pY, pZ, pLevel);
        this.launchAdjustment = ILaunchAdjustment.DEFAULT;
    }

    public ObsidianSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.OBSIDIAN_SNOWBALL.get(), pShooter, pLevel);
        this.launchAdjustment = launchAdjustment;
    }
//    public ObsidianSnowballEntity(LivingEntity livingEntity, Level level, LaunchFunc launchFunc) {
//        super(livingEntity, level);
//        this.setLaunchFrom(launchFunc.getLaunchFrom()).setDamage(6).setBlazeDamage(8);
//        launchFunc.launchProperties(this);
//        this.setItem(new ItemStack(ItemRegister.OBSIDIAN_SNOWBALL.get()));
//
//    }
//
//    //This is only used for dispenser
//    public ObsidianSnowballEntity(Level level, double x, double y, double z) {
//        super(level, x, y, z);
//        this.setDamage(6).setBlazeDamage(8);
//        this.setItem(new ItemStack(ItemRegister.OBSIDIAN_SNOWBALL.get()));
//    }
//
//    @Override
//    public Item getCorrespondingItem() {
//        return ItemRegister.OBSIDIAN_SNOWBALL.get();
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
        return 6;
    }

    @Override
    public float getBasicBlazeDamage() {
        return 8;
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
        return 2.25f;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.OBSIDIAN_SNOWBALL.get();
    }
}
