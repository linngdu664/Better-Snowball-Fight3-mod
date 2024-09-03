package com.linngdu664.bsf.entity.ai.goal.target;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.util.BSFTeamSavedData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;

public class BSFGolemHurtByTargetGoal extends HurtByTargetGoal {
    private final BSFSnowGolemEntity snowGolem;

    public BSFGolemHurtByTargetGoal(BSFSnowGolemEntity snowGolem, Class<?>... pToIgnoreDamage) {
        super(snowGolem, pToIgnoreDamage);
        this.snowGolem = snowGolem;
    }

//     0: monster
//     1: target locator
//     2: enemy player
//     3: all
    @Override
    public boolean canUse() {
        if (snowGolem.getLocator() == 0) {
            if (snowGolem.getLastHurtByMob() instanceof Enemy) {
                return super.canUse();
            }
        } else if (snowGolem.getLocator() == 2) {
            BSFTeamSavedData savedData = snowGolem.getServer().overworld().getDataStorage().computeIfAbsent(BSFTeamSavedData::new, BSFTeamSavedData::new, "bsf_team");
            LivingEntity target = snowGolem.getLastHurtByMob();
            LivingEntity owner = snowGolem.getOwner();
            if (target instanceof Player && !savedData.isSameTeam(target, owner) || target instanceof OwnableEntity ownableEntity && !savedData.isSameTeam(target, ownableEntity.getOwner())) {
                return super.canUse();
            }
        } else if (snowGolem.getLocator() == 3) {
            return super.canUse();
        }
        return false;
//        if (snowGolem.getLocator()==1 && snowGolem.getTarget() != null && !snowGolem.getTarget().isRemoved()) {
//            return false;
//        }
//        LivingEntity owner = snowGolem.getOwner();
//        LivingEntity livingEntity = snowGolem.getLastHurtByMob();
//        if (livingEntity instanceof OwnableEntity ownableEntity && owner != null && owner.equals(ownableEntity.getOwner())) {
//            return false;
//        }
//        BSFTeamSavedData savedData = snowGolem.getServer().overworld().getDataStorage().computeIfAbsent(BSFTeamSavedData::new, BSFTeamSavedData::new, "bsf_team");
//        if (savedData.isSameTeam(owner, livingEntity)) {
//            return false;
//        }
//        return super.canUse();
    }
}
