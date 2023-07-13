package com.linngdu664.bsf.entity.snowball.nomal;

import com.linngdu664.bsf.entity.BSFSnowballEntity;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.util.LaunchFunc;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class SpectralSnowballEntity extends BSFSnowballEntity {
    public SpectralSnowballEntity(LivingEntity livingEntity, Level level, LaunchFunc launchFunc) {
        super(livingEntity, level);
        this.setLaunchFrom(launchFunc.getLaunchFrom());
        launchFunc.launchProperties(this);
        this.setItem(new ItemStack(ItemRegister.SPECTRAL_SNOWBALL.get()));
    }

    //This is only used for dispenser
    public SpectralSnowballEntity(Level level, double x, double y, double z) {
        super(level, x, y, z);
        this.setItem(new ItemStack(ItemRegister.SPECTRAL_SNOWBALL.get()));
    }

    @Override
    public Item getCorrespondingItem() {
        return ItemRegister.SPECTRAL_SNOWBALL.get();
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!level().isClientSide) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        if (entity instanceof LivingEntity livingEntity && !isCaught) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0));
        }
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
        }
    }
}
