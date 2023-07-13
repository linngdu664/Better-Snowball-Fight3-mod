package com.linngdu664.bsf.entity.snowball.tracking;

import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.util.LaunchFunc;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class LightPlayerTrackingSnowballEntity extends AbstractTrackingSnowballEntity {
    public LightPlayerTrackingSnowballEntity(LivingEntity livingEntity, Level level, LaunchFunc launchFunc) {
        super(livingEntity, level);
        this.setRange(10).setTargetClass(Player.class).setLaunchFrom(launchFunc.getLaunchFrom());
        launchFunc.launchProperties(this);
        this.setItem(new ItemStack(ItemRegister.LIGHT_PLAYER_TRACKING_SNOWBALL.get()));
    }

    @Override
    public Item getCorrespondingItem() {
        return ItemRegister.LIGHT_PLAYER_TRACKING_SNOWBALL.get();
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!level().isClientSide) {
            this.discard();
        }
    }
}
