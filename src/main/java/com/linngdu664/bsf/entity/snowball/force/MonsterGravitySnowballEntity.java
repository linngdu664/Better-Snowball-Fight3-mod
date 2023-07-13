package com.linngdu664.bsf.entity.snowball.force;

import com.linngdu664.bsf.entity.EntityRegister;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.ItemRegister;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MonsterGravitySnowballEntity extends AbstractForceSnowballEntity {
//    public MonsterGravitySnowballEntity(LivingEntity livingEntity, Level level, LaunchFunc launchFunc) {
//        super(livingEntity, level);
//        this.setRange(15).setTargetClass(Monster.class).setGM(2).setBoundaryR2(2).setLaunchFrom(launchFunc.getLaunchFrom());
//        launchFunc.launchProperties(this);
//        this.setItem(new ItemStack(ItemRegister.MONSTER_GRAVITY_SNOWBALL.get()));
//    }

    public MonsterGravitySnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public MonsterGravitySnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.MONSTER_GRAVITY_SNOWBALL.get(), pShooter, pLevel);
        this.launchAdjustment = launchAdjustment;
    }

//    @Override
//    public Item getCorrespondingItem() {
//        return ItemRegister.MONSTER_GRAVITY_SNOWBALL.get();
//    }

//    @Override
//    protected void onHit(@NotNull HitResult pResult) {
//        super.onHit(pResult);
//        if (!level.isClientSide) {
//            this.discard();
//        }
//    }


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
        return 3;
    }

    @Override
    double getRange() {
        return 15;
    }

    @Override
    Class<? extends Entity> getTargetClass() {
        return Monster.class;
    }

    @Override
    double getGM() {
        return 2;
    }

    @Override
    double getBoundaryR2() {
        return 2;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.MONSTER_GRAVITY_SNOWBALL.get();
    }
}
