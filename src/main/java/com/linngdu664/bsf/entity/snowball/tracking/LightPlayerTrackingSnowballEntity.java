package com.linngdu664.bsf.entity.snowball.tracking;

import com.linngdu664.bsf.entity.EntityRegister;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.ItemRegister;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class LightPlayerTrackingSnowballEntity extends AbstractTrackingSnowballEntity {
    public LightPlayerTrackingSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public LightPlayerTrackingSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.LIGHT_PLAYER_TRACKING_SNOWBALL.get(), pShooter, pLevel);
        this.launchAdjustment = launchAdjustment;
    }
//    public LightPlayerTrackingSnowballEntity(LivingEntity livingEntity, Level level, LaunchFunc launchFunc) {
//        super(livingEntity, level);
//        this.setRange(10).setTargetClass(Player.class).setLaunchFrom(launchFunc.getLaunchFrom());
//        launchFunc.launchProperties(this);
//        this.setItem(new ItemStack(ItemRegister.LIGHT_PLAYER_TRACKING_SNOWBALL.get()));
//    }

//    @Override
//    public Item getCorrespondingItem() {
//        return ItemRegister.LIGHT_PLAYER_TRACKING_SNOWBALL.get();
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
    public double getRange() {
        return 10;
    }

    @Override
    public Class<? extends Entity> getTargetClass() {
        return Player.class;
    }

    @Override
    public boolean isLockFeet() {
        return false;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.LIGHT_PLAYER_TRACKING_SNOWBALL.get();
    }
}
