package com.linngdu664.bsf.entity.executor;

import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.registry.ParticleRegister;
import com.linngdu664.bsf.util.BSFConfig;
import com.linngdu664.bsf.util.ParticleUtil;
import net.minecraft.core.BlockPos;
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
    private static final Vec3 SPLASH_VEC3_1 = new Vec3(-3, 6, 1.732050807568877);
    private static final Vec3 SPLASH_VEC3_2 = new Vec3(3, -6, -1.732050807568877);

    public BlackHoleExecutor(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BlackHoleExecutor(EntityType<?> pEntityType, double pX, double pY, double pZ, Level pLevel, Vec3 vel, int maxTime) {
        super(pEntityType, pX, pY, pZ, pLevel, 8, 8, 30, maxTime);
        setDeltaMovement(vel);
        setGlowingTag(true);
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        Vec3 vec3 = getDeltaMovement();
        Vec3 pos = getPosition(0);
        if (!level.isClientSide) {
            if (level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && BSFConfig.destroyMode) {
                BlockPos.betweenClosedStream(getBoundingBox().inflate(5))
                        .filter(p -> {
                            float destroyTime = level.getBlockState(p).getBlock().defaultDestroyTime();
                            return p.getCenter().distanceToSqr(pos) < 25 && destroyTime >= 0 && destroyTime < 50;
                        })
                        .forEach(p -> level.destroyBlock(p, true));
            }
            targetList.stream()
                    .filter(p -> p.distanceToSqr(pos) < 9)
                    .forEach(p -> {
                        if (p instanceof BlackHoleExecutor) {
                            p.discard();
                            discard();
                        } else if (!(p instanceof ItemEntity)) {
                            p.hurt(level.damageSources().fellOutOfWorld(), 2);
                        }
                    });
        } else {
            Vec3 pos1 = pos.add(0.1, 0.1, 0.1);
            Vec3 pos2 = pos.subtract(0.1, 0.1, 0.1);
            ParticleUtil.spawnForwardRaysParticles(level, ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), pos1, pos2, SPLASH_VEC3_1, vec3, 2, 5, 10);
            ParticleUtil.spawnForwardRaysParticles(level, ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), pos1, pos2, SPLASH_VEC3_2, vec3, 2, 5, 10);
        }
        setPos(getX() + vec3.x, getY() + vec3.y, getZ() + vec3.z);
    }

    @Override
    public void remove(@NotNull RemovalReason pReason) {
        super.remove(pReason);
        Level level = level();
        if (pReason.equals(Entity.RemovalReason.DISCARDED) && !level.isClientSide) {
            if (level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && BSFConfig.destroyMode) {
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
