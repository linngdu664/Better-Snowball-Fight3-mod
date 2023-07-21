package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.EntityRegister;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.util.SoundRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SubspaceSnowballEntity extends AbstractBSFSnowballEntity {
    private boolean release = true;
    private final ArrayList<ItemStack> itemStackArrayList = new ArrayList<>();
    private int timer = 0;
    private float damage = Float.MIN_NORMAL;
    private float blazeDamage = 3F;

    public SubspaceSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setNoGravity(true);
    }

    public SubspaceSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment, boolean release) {
        super(EntityRegister.SUBSPACE_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
        this.release = release;
        this.setNoGravity(true);
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (!level.isClientSide) {
            AABB aabb = getBoundingBox().inflate(2.5);
            List<AbstractBSFSnowballEntity> list = level.getEntitiesOfClass(AbstractBSFSnowballEntity.class, aabb, p -> !this.equals(p));
            for (AbstractBSFSnowballEntity snowball : list) {
                if (release && !(snowball instanceof GPSSnowballEntity)) {
                    itemStackArrayList.add(snowball.getItem());
                }
                ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, snowball.getX(), snowball.getY(), snowball.getZ(), 8, 0, 0, 0, 0.05);
                snowball.discard();
                if (snowball instanceof SubspaceSnowballEntity) {
                    ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY(), this.getZ(), 16, 0, 0, 0, 0.05);
                    this.discard();
                }
                if (!release && damage < 15.0F) {
                    damage += snowball.getSubspacePower();
                    blazeDamage += snowball.getSubspacePower();
                }
                level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundRegister.SUBSPACE_SNOWBALL_CUT.get(), SoundSource.PLAYERS, 0.7F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            }
            List<Snowball> list2 = level.getEntitiesOfClass(Snowball.class, aabb, p -> true);
            for (Snowball snowball : list2) {
                if (release) {
                    itemStackArrayList.add(snowball.getItem());
                }
                ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, snowball.getX(), snowball.getY(), snowball.getZ(), 8, 0, 0, 0, 0.05);
                snowball.discard();
                if (!release && damage < 15.0F) {
                    damage += 1;
                    blazeDamage += 1;
                }
            }
            if (timer == 150) {
                for (ItemStack itemStack : itemStackArrayList) {
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
            for (ItemStack itemStack : itemStackArrayList) {
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
    @Override
    public boolean canBeCaught() {
        return false;
    }

    @Override
    public float getBasicDamage() {
        return damage;
    }

    @Override
    public float getBasicBlazeDamage() {
        return blazeDamage;
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
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.SUBSPACE_SNOWBALL.get();
    }
}
