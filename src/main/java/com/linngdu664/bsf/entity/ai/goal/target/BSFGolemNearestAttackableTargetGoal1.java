package com.linngdu664.bsf.entity.ai.goal.target;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class BSFGolemNearestAttackableTargetGoal1 extends NearestAttackableTargetGoal<BSFSnowGolemEntity> {
    private final BSFSnowGolemEntity golem;

    public BSFGolemNearestAttackableTargetGoal1(BSFSnowGolemEntity golem, Class pTargetType, int pRandomInterval, boolean pMustSee, boolean pMustReach, @Nullable Predicate pTargetPredicate) {
        super(golem, pTargetType, pRandomInterval, pMustSee, pMustReach, pTargetPredicate);
        this.golem = golem;
    }

    @Override
    public boolean canUse() {
        if (golem.getLocator()==1) {
            return false;
        }
        return super.canUse();
    }

    @Override
    protected @NotNull AABB getTargetSearchArea(double pTargetDistance) {
        return mob.getBoundingBox().inflate(pTargetDistance, pTargetDistance, pTargetDistance);
    }
}
