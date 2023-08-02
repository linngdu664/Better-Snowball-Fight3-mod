package com.linngdu664.bsf.item.tool;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JediGloveItem extends GloveItem {
    public JediGloveItem() {
        super(Rarity.EPIC, 514, 12);
    }

    @Override
    public void onUseTick(@NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, @NotNull ItemStack pStack, int pRemainingUseDuration) {
        if (!pLevel.isClientSide && pLivingEntity instanceof Player player) {
            AABB aabb = player.getBoundingBox().inflate(3);
            List<AbstractBSFSnowballEntity> list = pLevel.getEntitiesOfClass(AbstractBSFSnowballEntity.class, aabb, p -> !player.equals(p.getOwner()) && p.canBeCaught());
            List<Snowball> list1 = pLevel.getEntitiesOfClass(Snowball.class, aabb, p -> !player.equals(p.getOwner()));
            for (AbstractBSFSnowballEntity snowball : list) {
                player.getInventory().placeItemBackInInventory(snowball.getItem(), true);
                ((ServerLevel) pLevel).sendParticles(ParticleTypes.DRAGON_BREATH, snowball.getX(), snowball.getY(), snowball.getZ(), 8, 0, 0, 0, 0.05);
                snowball.discard();
            }
            for (Snowball snowball : list1) {
                player.getInventory().placeItemBackInInventory(snowball.getItem(), true);
                ((ServerLevel) pLevel).sendParticles(ParticleTypes.DRAGON_BREATH, snowball.getX(), snowball.getY(), snowball.getZ(), 8, 0, 0, 0, 0.05);
                snowball.discard();
            }
            if (!list.isEmpty() || !list1.isEmpty()) {
                pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOW_BREAK, SoundSource.NEUTRAL, 3F, 0.4F / pLevel.getRandom().nextFloat() * 0.4F + 0.8F);
                releaseUsing(pStack, pLevel, pLivingEntity, 0);
            }
            pStack.hurtAndBreak(list.size() + list1.size(), player, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
        }
    }
    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("jedi_glove.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_AQUA));
    }
}
