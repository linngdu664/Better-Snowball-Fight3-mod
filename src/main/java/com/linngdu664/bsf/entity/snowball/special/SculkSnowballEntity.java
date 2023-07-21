package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.EntityRegister;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.util.BSFMthUtil;
import com.linngdu664.bsf.util.SoundRegister;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class SculkSnowballEntity extends AbstractBSFSnowballEntity {
    public SculkSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setItem(new ItemStack(ItemRegister.SCULK_SNOWBALL.get()));
    }

    public SculkSnowballEntity(LivingEntity pShooter, Level pLevel) {
        super(EntityRegister.SCULK_SNOWBALL.get(), pShooter, pLevel);
        this.setItem(new ItemStack(ItemRegister.SCULK_SNOWBALL.get()));
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!level().isClientSide) {
            discard();
            level().playSound(null, getX(), getY(), getZ(), SoundRegister.MEME[BSFMthUtil.randInt(0, 25)].get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
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
        return 2;
    }

    @Override
    public float getSubspacePower() {
        return 1;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.COMPACTED_SNOWBALL.get();
    }
}
