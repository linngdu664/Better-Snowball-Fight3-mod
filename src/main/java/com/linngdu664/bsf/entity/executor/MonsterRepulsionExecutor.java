package com.linngdu664.bsf.entity.executor;

import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.registry.ParticleRegister;
import com.linngdu664.bsf.util.ParticleUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class MonsterRepulsionExecutor extends AbstractFixedForceExecutor {
    public MonsterRepulsionExecutor(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public MonsterRepulsionExecutor(EntityType<?> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel, -2, 2, 15);
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (level.isClientSide && timer < 141) {
            ParticleUtil.spawnSphereDiffusionParticles(level, ParticleRegister.MONSTER_REPULSION_EXECUTOR_ASH.get(), this.getPosition(0), 40, 1.066864);
        }
    }

    @Override
    public List<? extends Entity> getTargetList() {
        double r2 = range * range;
        return level().getEntities(this, getBoundingBox().inflate(range), p -> p instanceof Enemy && distanceToSqr(p) < r2);
    }

    @Override
    public ItemStack getSnowballItem() {
        return new ItemStack(ItemRegister.MONSTER_REPULSION_SNOWBALL.get());
    }
}
