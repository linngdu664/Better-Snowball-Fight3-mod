package com.linngdu664.bsf.entity.ai.goal.target;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.util.BSFTeamSavedData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class BSFGolemOwnerHurtByTargetGoal extends TargetGoal {
    private final BSFSnowGolemEntity snowGolem;
    private LivingEntity ownerLastHurtBy;
    private int timestamp;

    public BSFGolemOwnerHurtByTargetGoal(BSFSnowGolemEntity snowGolem) {
        super(snowGolem, false);
        this.snowGolem = snowGolem;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

//     0: monster
//     1: target locator
//     2: enemy player
//     3: all
    public boolean canUse() {
        if (snowGolem.isTame() && !snowGolem.isOrderedToSit()) {
            LivingEntity owner = snowGolem.getOwner();
            if (owner == null) {
                return false;
            } else {
                this.ownerLastHurtBy = owner.getLastHurtByMob();
                if (snowGolem.getLocator() == 0) {
                    if (!(ownerLastHurtBy instanceof Enemy)) {
                        return false;
                    }
                } else if (snowGolem.getLocator() == 1) {
                    return false;
                } else if (snowGolem.getLocator() == 2) {
                    if (!(ownerLastHurtBy instanceof Player)) {
                        return false;
                    }
                    BSFTeamSavedData savedData = snowGolem.getServer().overworld().getDataStorage().computeIfAbsent(BSFTeamSavedData::new, BSFTeamSavedData::new, "bsf_team");
                    if (savedData.isSameTeam(owner, ownerLastHurtBy)) {
                        return false;
                    }
                }
                int $$1 = owner.getLastHurtByMobTimestamp();
                return $$1 != timestamp && canAttack(ownerLastHurtBy, TargetingConditions.DEFAULT) && snowGolem.wantsToAttack(ownerLastHurtBy, owner);
            }
        } else {
            return false;
        }
    }

    public void start() {
        mob.setTarget(ownerLastHurtBy);
        LivingEntity owner = snowGolem.getOwner();
        if (owner != null) {
            timestamp = owner.getLastHurtByMobTimestamp();
        }
        super.start();
    }
}
