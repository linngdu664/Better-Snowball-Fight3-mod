package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.registry.EntityRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.registry.SoundRegister;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
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
    private int soundId;

    public SculkSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, new BSFSnowballEntityProperties().basicPunch(2));
        this.setItem(new ItemStack(ItemRegister.SCULK_SNOWBALL.get()));
        this.soundId = -1;
    }

    public SculkSnowballEntity(LivingEntity pShooter, Level pLevel, int soundId) {
        super(EntityRegister.SCULK_SNOWBALL.get(), pShooter, pLevel, new BSFSnowballEntityProperties().basicPunch(2));
        this.setItem(new ItemStack(ItemRegister.SCULK_SNOWBALL.get()));
        this.soundId = soundId;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("SoundId", soundId);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        soundId = pCompound.getInt("SoundId");
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        Level level = level();
        if (!level.isClientSide && !isCaught) {
            discard();
            ((ServerLevel) level).sendParticles(new ShriekParticleOption(0), this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) level).sendParticles(new ShriekParticleOption(5), this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) level).sendParticles(new ShriekParticleOption(10), this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
            if (soundId == -1) {
                level.playSound(null, getX(), getY(), getZ(), SoundRegister.MEME[level.random.nextInt(0, 64)].get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
            } else {
                level.playSound(null, getX(), getY(), getZ(), SoundRegister.MEME[soundId].get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
        }
    }

//    @Override
//    public double getBasicPunch() {
//        return 2;
//    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.COMPACTED_SNOWBALL.get();
    }
}
