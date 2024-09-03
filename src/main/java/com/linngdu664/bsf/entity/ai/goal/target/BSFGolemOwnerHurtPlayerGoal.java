package com.linngdu664.bsf.entity.ai.goal.target;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.util.BSFTeamSavedData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class BSFGolemOwnerHurtPlayerGoal extends TargetGoal {
    private final BSFSnowGolemEntity snowGolem;
    private LivingEntity ownerLastHurt;
    private int timestamp;

    public BSFGolemOwnerHurtPlayerGoal(BSFSnowGolemEntity snowGolem) {
        super(snowGolem, false);
        this.snowGolem = snowGolem;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean canUse() {
        if (snowGolem.isTame() && !snowGolem.isOrderedToSit()) {
            LivingEntity owner = snowGolem.getOwner();
            if (owner == null) {
                return false;
            } else {
                if (snowGolem.getLocator() != 2) {
                    return false;
                }
                this.ownerLastHurt = owner.getLastHurtMob();
                if (!(ownerLastHurt instanceof Player)) {
                    return false;
                }
                BSFTeamSavedData savedData = snowGolem.getServer().overworld().getDataStorage().computeIfAbsent(BSFTeamSavedData::new, BSFTeamSavedData::new, "bsf_team");
                if (savedData.isSameTeam(owner, ownerLastHurt)) {
                    return false;
                }
                int $$1 = owner.getLastHurtMobTimestamp();
                return $$1 != timestamp && canAttack(ownerLastHurt, TargetingConditions.DEFAULT);
            }
        } else {
            return false;
        }
    }

    public void start() {
        mob.setTarget(ownerLastHurt);
        LivingEntity owner = snowGolem.getOwner();
        if (owner != null) {
            timestamp = owner.getLastHurtMobTimestamp();
        }
        super.start();
    }
}
