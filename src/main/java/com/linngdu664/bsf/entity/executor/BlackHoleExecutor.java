package com.linngdu664.bsf.entity.executor;

import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.registry.ParticleRegister;
import com.linngdu664.bsf.util.BSFConfig;
import com.linngdu664.bsf.util.ParticleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlackHoleExecutor extends AbstractForceExecutor {
    public BlackHoleExecutor(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BlackHoleExecutor(EntityType<?> pEntityType, double pX, double pY, double pZ, Level pLevel, Vec3 vel, int maxTime) {
        super(pEntityType, pX, pY, pZ, pLevel, 8, 8, 30, maxTime);
        setDeltaMovement(vel);
        this.setSharedFlag(6,true);
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (!level.isClientSide) {
//            ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY(), this.getZ(), 8, 0, 0, 0, 0.12);
            if (level.getGameRules().getBoolean((GameRules.RULE_MOBGRIEFING)) && BSFConfig.destroyMode) {
                BlockPos.betweenClosedStream(getBoundingBox().inflate(5))
                        .filter(p -> {
                            float destroyTime = level.getBlockState(p).getBlock().defaultDestroyTime();
                            return p.getCenter().distanceToSqr(getPosition(0)) < 25 && destroyTime >= 0 && destroyTime < 50;
                        })
                        .forEach(p -> level.destroyBlock(p, true));
            }
            targetList.stream()
                    .filter(p -> p.distanceToSqr(getPosition(0)) < 9)
                    .forEach(p -> {
                        if (p instanceof BlackHoleExecutor) {
                            p.discard();
                            discard();
                        } else if (!(p instanceof ItemEntity)) {
                            p.hurt(level.damageSources().fellOutOfWorld(), 2);
                        }
                    });
        }else{
            Vec3 splashVec3 = new Vec3(-3,6,Math.sqrt(3));
            ParticleUtil.spawnForwardRaysParticles(level, ParticleRegister.SHORT_TIME_SNOWFLAKE.get(),this.getPosition(0).add(0.1,0.1,0.1),this.getPosition(0).add(-0.1,-0.1,-0.1),splashVec3,this.getDeltaMovement(),2,5,10);
            ParticleUtil.spawnForwardRaysParticles(level, ParticleRegister.SHORT_TIME_SNOWFLAKE.get(),this.getPosition(0).add(0.1,0.1,0.1),this.getPosition(0).add(-0.1,-0.1,-0.1),splashVec3.scale(-1),this.getDeltaMovement(),2,5,10);
//            ParticleUtil.spawnForwardConeParticles(level,this,splashVec3,ParticleTypes.END_ROD,2,90,0.5f,0);
//            ParticleUtil.spawnForwardConeParticles(level,this,splashVec3.scale(-1),ParticleTypes.END_ROD,2,90,0.5f,0);
        }
        Vec3 vec3 = this.getDeltaMovement();
        double d2 = this.getX() + vec3.x;
        double d0 = this.getY() + vec3.y;
        double d1 = this.getZ() + vec3.z;
        this.setPos(d2, d0, d1);
    }

    @Override
    public void remove(@NotNull RemovalReason pReason) {
        super.remove(pReason);
        Level level = level();
        if (pReason.equals(Entity.RemovalReason.DISCARDED) && !level.isClientSide) {
            if (level.getGameRules().getBoolean((GameRules.RULE_MOBGRIEFING)) && BSFConfig.destroyMode) {
                level.explode(null, getX(), getY(), getZ(), 6, Level.ExplosionInteraction.TNT);
            } else {
                level.explode(null, getX(), getY(), getZ(), 6, Level.ExplosionInteraction.NONE);
            }
        }
    }

    @Override
    public float getSubspacePower() {
        return 8;
    }

    @Override
    public ItemStack getSnowballItem() {
        return new ItemStack(ItemRegister.BLACK_HOLE_SNOWBALL.get());
    }

    @Override
    public List<? extends Entity> getTargetList() {
        return level().getEntities(this, getBoundingBox().inflate(range), EntitySelector.NO_CREATIVE_OR_SPECTATOR);
    }
}
