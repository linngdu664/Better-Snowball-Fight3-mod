package com.linngdu664.bsf.entity.executor;

import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.registry.ParticleRegister;
import com.linngdu664.bsf.particle.util.ParticleUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class MonsterGravityExecutor extends AbstractFixedForceExecutor {
    public MonsterGravityExecutor(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public MonsterGravityExecutor(EntityType<?> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel, 2, 2, 15);
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (level.isClientSide && getTimer() < getMaxTime() - 59) {
            ParticleUtil.spawnSphereGatherParticles(level, ParticleRegister.MONSTER_GRAVITY_EXECUTOR_ASH.get(), this.getPosition(0), 15, 40, 0.0197);
        }
    }

    @Override
    public List<? extends Entity> getTargetList() {
        double r2 = range * range;
        return level().getEntities(this, getBoundingBox().inflate(range), p -> p instanceof Enemy && distanceToSqr(p) < r2);
    }

    @Override
    public ItemStack getSnowballItem() {
        return new ItemStack(ItemRegister.MONSTER_GRAVITY_SNOWBALL.get());
    }
}
