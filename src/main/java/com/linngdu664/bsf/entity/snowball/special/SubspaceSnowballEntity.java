package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.BSFSnowballEntity;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.util.LaunchFunc;
import com.linngdu664.bsf.util.SoundRegister;
import com.linngdu664.bsf.util.TargetGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Vector;

public class SubspaceSnowballEntity extends BSFSnowballEntity {
    private final boolean release;
    private final Vector<ItemStack> ItemStackVector = new Vector<>();
    private int timer = 0;

    public SubspaceSnowballEntity(LivingEntity livingEntity, Level level, LaunchFunc launchFunc, boolean canRelease) {
        super(livingEntity, level);
        this.setLaunchFrom(launchFunc.getLaunchFrom());
        launchFunc.launchProperties(this);
        this.setItem(new ItemStack(ItemRegister.SUBSPACE_SNOWBALL.get()));
        this.setNoGravity(true);
        this.release = canRelease;
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (!level.isClientSide) {
            List<BSFSnowballEntity> list = TargetGetter.getTargetList(this, BSFSnowballEntity.class, 2.5);
            for (BSFSnowballEntity snowball : list) {
                if (release) {
                    ItemStackVector.add(new ItemStack(snowball instanceof GPSSnowballEntity ? null : snowball.getItem().getItem()));
                }
                ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, snowball.getX(), snowball.getY(), snowball.getZ(), 8, 0, 0, 0, 0.05);
                snowball.discard();
                if (snowball instanceof SubspaceSnowballEntity) {
                    ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY(), this.getZ(), 16, 0, 0, 0, 0.05);
                    this.discard();
                }
                if (!release && damage < 15.0F) {
                    damage += snowball.getPower();
                    blazeDamage += snowball.getPower();
                }
                level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundRegister.SUBSPACE_SNOWBALL_CUT.get(), SoundSource.PLAYERS, 0.7F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            }
            List<Snowball> list2 = TargetGetter.getTargetList(this, Snowball.class, 2.5);
            for (Snowball snowball : list2) {
                if (release) {
                    ItemStackVector.add(snowball.getItem());
                }
                ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, snowball.getX(), snowball.getY(), snowball.getZ(), 8, 0, 0, 0, 0.05);
                snowball.discard();
                if (!release && damage < 15.0F) {
                    damage += 1;
                    blazeDamage += 1;
                }
            }
            if (timer == 150) {
                for (ItemStack itemStack : ItemStackVector) {
                    ItemEntity itemEntity = new ItemEntity(level, getX(), getY(), getZ(), itemStack);
                    itemEntity.setDefaultPickUpDelay();
                    level.addFreshEntity(itemEntity);
                }
                ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY(), this.getZ(), 16, 0, 0, 0, 0.05);
                this.discard();
            }
            timer++;
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);
        Level level = level();
        if (!level.isClientSide) {
            for (ItemStack itemStack : ItemStackVector) {
                ItemEntity itemEntity = new ItemEntity(level, getX(), getY(), getZ(), itemStack);
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
            }
            ((ServerLevel) level).sendParticles(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(), (int) damage * 4, 0, 0, 0, 0);
            ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), (int) damage * 4, 0, 0, 0, 0.04);
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Level level = level();
        if (!release && !level.isClientSide) {
            ((ServerLevel) level).sendParticles(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(), (int) damage * 4, 0, 0, 0, 0);
            ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), (int) damage * 4, 0, 0, 0, 0.04);
            this.discard();
        }
    }
}
