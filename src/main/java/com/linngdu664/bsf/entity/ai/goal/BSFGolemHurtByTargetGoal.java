package com.linngdu664.bsf.entity.ai.goal;

import com.linngdu664.bsf.BSFTeamSavedData;
import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public class BSFGolemHurtByTargetGoal extends HurtByTargetGoal {
    private final BSFSnowGolemEntity snowGolem;

    public BSFGolemHurtByTargetGoal(BSFSnowGolemEntity snowGolem, Class<?>... pToIgnoreDamage) {
        super(snowGolem, pToIgnoreDamage);
        this.snowGolem = snowGolem;
    }

    @Override
    public boolean canUse() {
        if (snowGolem.isUseLocator() && snowGolem.getTarget() != null && !snowGolem.getTarget().isRemoved()) {
            return false;
        }
        LivingEntity owner = snowGolem.getOwner();
        LivingEntity livingEntity = snowGolem.getLastHurtByMob();
        if (livingEntity instanceof OwnableEntity ownableEntity && owner != null && owner.equals(ownableEntity.getOwner())) {
            return false;
        }
        BSFTeamSavedData savedData = snowGolem.getServer().overworld().getDataStorage().computeIfAbsent(BSFTeamSavedData::new, BSFTeamSavedData::new, "bsf_team");
        if (savedData.isSameTeam(owner, livingEntity)) {
            return false;
        }
        return super.canUse();
    }
}
