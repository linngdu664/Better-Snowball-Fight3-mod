package com.linngdu664.bsf.item.tool;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JediGloveItem extends GloveItem {
    public JediGloveItem() {
        super(Rarity.RARE, 514, 12);
    }

    @Override
    public void onUseTick(@NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, @NotNull ItemStack pStack, int pRemainingUseDuration) {
        if (!pLevel.isClientSide && pLivingEntity instanceof Player player) {
            AABB aabb = player.getBoundingBox().inflate(3);
            List<AbstractBSFSnowballEntity> list = pLevel.getEntitiesOfClass(AbstractBSFSnowballEntity.class, aabb, p -> !player.equals(p.getOwner()) && p.canBeCaught());
            List<Snowball> list1 = pLevel.getEntitiesOfClass(Snowball.class, aabb, p -> !player.equals(p.getOwner()));
            for (AbstractBSFSnowballEntity snowball : list) {
                player.getInventory().placeItemBackInInventory(snowball.getItem(), true);
                snowball.discard();
            }
            for (Snowball snowball : list1) {
                player.getInventory().placeItemBackInInventory(snowball.getItem(), true);
                snowball.discard();
            }
            if (!list.isEmpty() || !list1.isEmpty()) {
                releaseUsing(pStack, pLevel, pLivingEntity, 0);
            }
            pStack.hurtAndBreak(list.size() + list1.size(), player, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
        }
    }
}
