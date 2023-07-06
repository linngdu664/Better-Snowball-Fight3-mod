package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.BSFSnowballEntity;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.particle.ParticleRegister;
import com.linngdu664.bsf.util.LaunchFunc;
import com.linngdu664.bsf.util.SoundRegister;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class PowderSnowballEntity extends BSFSnowballEntity {
    private boolean isStart = false;
    private int timer = 0;

    public PowderSnowballEntity(LivingEntity livingEntity, Level level, LaunchFunc launchFunc) {
        super(livingEntity, level);
        this.setLaunchFrom(launchFunc.getLaunchFrom());
        launchFunc.launchProperties(this);
        this.setItem(new ItemStack(ItemRegister.POWDER_SNOWBALL.get()));
    }

    //This is only used for dispenser
    public PowderSnowballEntity(Level level, double x, double y, double z) {
        super(level, x, y, z);
        this.setItem(new ItemStack(ItemRegister.SMOOTH_SNOWBALL.get()));
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);
        Level level = level();
        isStart = true;
        if (!level.isClientSide) {
            Vec3 vec3 = this.getDeltaMovement();
            this.push(-vec3.x, -vec3.y, -vec3.z);
            this.setNoGravity(true);
            ((ServerLevel) level).sendParticles(ParticleRegister.BIG_LONG_TIME_SNOWFLAKE.get(), this.getX(), this.getY(), this.getZ(), 25, 0, 0, 0, 0.4);
            level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundRegister.POWDER_SNOWBALL.get(), SoundSource.PLAYERS, 0.3F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
        }
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (!level.isClientSide) {
            if (isStart) {
                Vec3 vec3 = this.getDeltaMovement();
                this.push(-vec3.x, -vec3.y, -vec3.z);
                ((ServerLevel) level).sendParticles(ParticleRegister.BIG_LONG_TIME_SNOWFLAKE.get(), this.getX(), this.getY(), this.getZ(), 8, 0, 0, 0, 0.2);
                if (timer > 200) {
                    this.discard();
                }
            }
        }
        timer++;
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (isCaught) {
            this.discard();
        }
    }

    @Override
    public Item getCorrespondingItem() {
        return ItemRegister.POWDER_SNOWBALL.get();
    }
}
