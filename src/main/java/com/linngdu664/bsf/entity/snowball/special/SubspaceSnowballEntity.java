package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.Absorbable;
import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.entity.executor.BlackHoleExecutor;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.network.SubspaceSnowballParticlesToClient;
import com.linngdu664.bsf.network.VectorInversionParticleToClient;
import com.linngdu664.bsf.particle.util.ParticleUtil;
import com.linngdu664.bsf.registry.*;
import com.linngdu664.bsf.util.BSFCommonUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.extensions.IForgePlayer;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
                if (!release) {
                    damage += damage<15?absorbable.getSubspacePower():15*absorbable.getSubspacePower()/damage;
                    blazeDamage += damage<15?absorbable.getSubspacePower():15*absorbable.getSubspacePower()/damage;
                    Vec3 vec3 = this.getDeltaMovement().scale(0.05);
                    this.push(vec3.x, vec3.y, vec3.z);
                }
                level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundRegister.SUBSPACE_SNOWBALL_CUT.get(), SoundSource.PLAYERS, 0.7F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            });
            level.getEntitiesOfClass(Snowball.class, aabb, p -> true).forEach(p -> {
                if (release) {
                    itemStackArrayList.add(p.getItem());
                }
                ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, p.getX(), p.getY(), p.getZ(), 8, 0, 0, 0, 0.05);
                p.discard();
                if (!release) {
                    damage += damage<15?1:15/damage;
                    blazeDamage += damage<15?1:15/damage;
                    Vec3 vec3 = this.getDeltaMovement().scale(0.05);
                    this.push(vec3.x, vec3.y, vec3.z);
                }
            });
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
    protected void onHitBlock(@NotNull BlockHitResult pResult) {
        super.onHitBlock(pResult);
        Vec3 location = pResult.getLocation();

        Level level = level();
        if (!level.isClientSide) {
            for (ItemStack itemStack : itemStackArrayList) {
                ItemEntity itemEntity = new ItemEntity(level, getX(), getY(), getZ(), itemStack);
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
            }
            if(!release){
                float r = damage<5?2:damage/5+1;
                List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, new AABB(location.x,location.y,location.z,location.x,location.y,location.z).inflate(r+3), EntitySelector.LIVING_ENTITY_STILL_ALIVE);
                System.out.println(new AABB(location.x,location.y,location.z,location.x,location.y,location.z).inflate(r+3));
                damageList(list,damage,r,location);

                NetworkRegister.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this), new SubspaceSnowballParticlesToClient(location.x, location.y, location.z, r,(int)(25*r)));
            }
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Vec3 location = BSFCommonUtil.getRealEntityHitPosOnMoveVectorWithHitResult(this,pResult);
        Level level = level();
        if (!release&&!level.isClientSide) {
            float r = damage<5?2:damage/5+1;
            List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, new AABB(location.x,location.y,location.z,location.x,location.y,location.z).inflate(r+3), EntitySelector.NO_SPECTATORS);
            damageList(list,damage,r,location);
            NetworkRegister.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this), new SubspaceSnowballParticlesToClient(location.x, location.y, location.z, r,(int)(25*r)));
            this.discard();
        }
    }


    private void damageList(List<? extends LivingEntity> list, float damage,float range,Vec3 location) {
        for (LivingEntity entity : list) {
            Vec3 rVec = new Vec3(entity.getX(), (entity.getBoundingBox().minY + entity.getBoundingBox().maxY) * 0.5, entity.getZ()).add(location.reverse());
            if (rVec.length() < range) {
                entity.hurt(level().damageSources().fellOutOfWorld(), (float) (damage/rVec.length()));
            }
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
