package com.linngdu664.bsf.entity.ai.goal;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public class BSFGolemHurtByTargetGoal extends HurtByTargetGoal {
    private final BSFSnowGolemEntity snowGolem;

    public BSFGolemHurtByTargetGoal(BSFSnowGolemEntity snowGolem, Class<?>... pToIgnoreDamage) {
        super(snowGolem, pToIgnoreDamage);
        this.snowGolem = snowGolem;
    }

    @Override
    public boolean canUse() {
        LivingEntity livingEntity = snowGolem.getLastHurtByMob();
        if (snowGolem.isUseLocator() && snowGolem.getTarget() != null && !snowGolem.getTarget().isRemoved() || livingEntity instanceof BSFSnowGolemEntity snowGolem1 && snowGolem1.getOwner() == snowGolem.getOwner()) {
            return false;
        }
        return super.canUse();
    }
}
