package com.linngdu664.bsf.entity.executor;

import com.linngdu664.bsf.registry.ItemRegister;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
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
    public List<? extends Entity> getTargetList() {
        return level().getEntitiesOfClass(Mob.class, getBoundingBox().inflate(range), (p) -> p instanceof Enemy);
    }

    @Override
    public ItemStack getSnowballItem() {
        return new ItemStack(ItemRegister.MONSTER_GRAVITY_SNOWBALL.get());
    }
}
