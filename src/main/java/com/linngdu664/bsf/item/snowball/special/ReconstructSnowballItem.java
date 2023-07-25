package com.linngdu664.bsf.item.snowball.special;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.special.ReconstructSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.util.BSFConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ReconstructSnowballItem extends AbstractSnowStorageSnowballItem {
    public ReconstructSnowballItem() {
        super(Rarity.RARE);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        return throwOrStorage(pPlayer, pLevel, pUsedHand, 2.0F, 15);
    }

    @Override
    public AbstractBSFSnowballEntity getCorrespondingEntity(Level level, LivingEntity livingEntity, ILaunchAdjustment launchAdjustment) {
        return new ReconstructSnowballEntity(livingEntity, level, launchAdjustment, absorbSnow(livingEntity, level));
    }

    @Override
    public int getMaxCapacity() {
        return BSFConfig.reconstructSnowballCapacity;
    }
}
