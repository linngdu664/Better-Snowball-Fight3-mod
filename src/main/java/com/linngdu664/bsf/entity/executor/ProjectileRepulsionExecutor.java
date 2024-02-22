package com.linngdu664.bsf.entity.executor;

import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.registry.ParticleRegister;
import com.linngdu664.bsf.util.ParticleUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class ProjectileRepulsionExecutor extends AbstractFixedForceExecutor {
    public ProjectileRepulsionExecutor(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ProjectileRepulsionExecutor(EntityType<?> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel, -2, 2, 15);
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (level.isClientSide && timer<141) {
            ParticleUtil.spawnSphereDiffusionParticles(level, ParticleRegister.PROJECTILE_REPULSION_EXECUTOR_ASH.get(),this.getPosition(0),40,1.066864);
        }
    }
    @Override
    public List<? extends Entity> getTargetList() {
        return level().getEntitiesOfClass(Projectile.class, getBoundingBox().inflate(range), (p) -> true);
    }

    @Override
    public ItemStack getSnowballItem() {
        return new ItemStack(ItemRegister.PROJECTILE_REPULSION_SNOWBALL.get());
    }
}
