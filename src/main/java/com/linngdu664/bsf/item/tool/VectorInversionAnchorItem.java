package com.linngdu664.bsf.item.tool;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
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
        pLevel.getEntitiesOfClass(Entity.class, pPlayer.getBoundingBox().inflate(10), p->p.getPosition(1).distanceToSqr(pPlayer.getPosition(1)) < 10 * 10)
                .forEach(p -> p.setDeltaMovement(p.getDeltaMovement().reverse()));
        pPlayer.getCooldowns().addCooldown(this, 40);
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
        return InteractionResultHolder.success(itemStack);
    }
}
