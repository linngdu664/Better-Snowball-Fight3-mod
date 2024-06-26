package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.Absorbable;
import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.network.SubspaceSnowballParticlesToClient;
import com.linngdu664.bsf.network.VectorInversionParticleToClient;
import com.linngdu664.bsf.particle.util.ParticleUtil;
import com.linngdu664.bsf.registry.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SubspaceSnowballEntity extends AbstractBSFSnowballEntity {
    private final ArrayList<ItemStack> itemStackArrayList = new ArrayList<>();
    private boolean release = true;
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
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Timer", timer);
        pCompound.putFloat("Damage", damage);
        pCompound.putFloat("BlazeDamage", blazeDamage);
        pCompound.putBoolean("Release", release);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        timer = pCompound.getInt("Timer");
        damage = pCompound.getFloat("Damage");
        blazeDamage = pCompound.getFloat("BlazeDamage");
        release=pCompound.getBoolean("Release");
    }


    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (!level.isClientSide) {
            AABB aabb = getBoundingBox().inflate(2.5);
            level.getEntities(this, aabb, p -> p instanceof Absorbable).forEach(p -> {
                Absorbable absorbable = (Absorbable) p;
                if (release) {
                    itemStackArrayList.add(absorbable.getSnowballItem());
                }
                ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, p.getX(), p.getY(), p.getZ(), 8, 0, 0, 0, 0.05);
                p.discard();
                if (p instanceof SubspaceSnowballEntity) {
                    ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY(), this.getZ(), 16, 0, 0, 0, 0.05);
                    this.discard();
                }
                if (!release && damage < 15.0F) {
                    damage += absorbable.getSubspacePower();
                    blazeDamage += absorbable.getSubspacePower();
                }
                level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundRegister.SUBSPACE_SNOWBALL_CUT.get(), SoundSource.PLAYERS, 0.7F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            });
//            List<AbstractBSFSnowballEntity> list = level.getEntitiesOfClass(AbstractBSFSnowballEntity.class, aabb, p -> !this.equals(p));
            /*
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
            }*/
            level.getEntitiesOfClass(Snowball.class, aabb, p -> true).forEach(p -> {
                if (release) {
                    itemStackArrayList.add(p.getItem());
                }
                ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, p.getX(), p.getY(), p.getZ(), 8, 0, 0, 0, 0.05);
                p.discard();
                if (!release && damage < 15.0F) {
                    damage += 1;
                    blazeDamage += 1;
                }
            });
//            List<Snowball> list2 = level.getEntitiesOfClass(Snowball.class, aabb, p -> true);
//            for (Snowball snowball : list2) {
//                if (release) {
//                    itemStackArrayList.add(snowball.getItem());
//                }
//                ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, snowball.getX(), snowball.getY(), snowball.getZ(), 8, 0, 0, 0, 0.05);
//                snowball.discard();
//                if (!release && damage < 15.0F) {
//                    damage += 1;
//                    blazeDamage += 1;
//                }
//            }
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
            if(!release){
                NetworkRegister.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this), new SubspaceSnowballParticlesToClient(this.getX(), this.getEyeY(), this.getZ(), 2,100));
            }
//            ((ServerLevel) level).sendParticles(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(), (int) damage * 4, 0, 0, 0, 0);
//            ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), (int) damage * 4, 0, 0, 0, 0.04);
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Level level = level();
        if (!release&&!level.isClientSide) {
            NetworkRegister.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this), new SubspaceSnowballParticlesToClient(this.getX(), this.getEyeY(), this.getZ(), 2,100));
//            ((ServerLevel) level).sendParticles(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(), (int) damage * 4, 0, 0, 0, 0);
//            ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), (int) damage * 4, 0, 0, 0, 0.04);
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
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.SUBSPACE_SNOWBALL.get();
    }
}
