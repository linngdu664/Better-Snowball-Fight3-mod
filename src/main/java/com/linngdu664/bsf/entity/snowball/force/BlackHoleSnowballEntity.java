package com.linngdu664.bsf.entity.snowball.force;

import com.linngdu664.bsf.entity.BSFSnowballEntity;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.util.LaunchFunc;
import com.linngdu664.bsf.entity.MovingAlgorithm;
import com.linngdu664.bsf.util.SoundRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class BlackHoleSnowballEntity extends BSFSnowballEntity {
    public int startTime = 20;
    public int endTime = 150;
    private int timer = 0;

    public BlackHoleSnowballEntity(LivingEntity livingEntity, Level level, LaunchFunc launchFunc) {
        super(livingEntity, level);
        this.setDamage(4).setBlazeDamage(6).setLaunchFrom(launchFunc.getLaunchFrom());
        launchFunc.launchProperties(this);
        this.setItem(new ItemStack(ItemRegister.BLACK_HOLE_SNOWBALL.get()));
        this.setNoGravity(true);
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);
        handleExplosion(6.0F);
        if (!level().isClientSide) {
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (!level.isClientSide) {
            if (timer == startTime) {
                Vec3 vec3 = this.getDeltaMovement();
                this.push(vec3.x * -0.75, vec3.y * -0.75, vec3.z * -0.75);
                ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 200, 0, 0, 0, 0.32);
                this.playSound(SoundRegister.BLACK_HOLE_START.get(), 3.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            }
            if (timer > startTime) {
                MovingAlgorithm.forceEffect(this, Entity.class, 30, 8, 8);
                ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY(), this.getZ(), 8, 0, 0, 0, 0.12);
            }
            if (timer == endTime) {
                handleExplosion(6.0F);
                this.discard();
            }
        }
        timer++;
    }

    public float getPower() {
        return 8;
    }
}
