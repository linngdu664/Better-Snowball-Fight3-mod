package com.linngdu664.bsf.item.tool;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class VectorInversionAnchorItem extends AbstractBSFEnhanceableToolItem {
    public VectorInversionAnchorItem() {
        super(Rarity.EPIC, 600);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        pLevel.getEntitiesOfClass(Projectile.class, pPlayer.getBoundingBox().inflate(7)).forEach(p -> p.setDeltaMovement(p.getDeltaMovement().reverse()));
        pPlayer.getCooldowns().addCooldown(this, 80);
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.success(itemStack);
    }
}
