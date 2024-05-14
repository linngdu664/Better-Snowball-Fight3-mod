package com.linngdu664.bsf.item.tool;

import com.linngdu664.bsf.network.VectorInversionParticleToClient;
import com.linngdu664.bsf.registry.NetworkRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class VectorInversionAnchorItem extends AbstractBSFEnhanceableToolItem {
    public VectorInversionAnchorItem() {
        super(Rarity.EPIC, 600);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        pLevel.getEntitiesOfClass(Entity.class, pPlayer.getBoundingBox().inflate(10), p->p.getPosition(1).distanceToSqr(pPlayer.getPosition(1)) < 10 * 10)
                .forEach(p -> {
                    p.setDeltaMovement(p.getDeltaMovement().reverse());
                    if (!pLevel.isClientSide) {
                        AABB aabb = p.getBoundingBox();
                        Vec3 center = aabb.getCenter();
                        double x = 0.5 * (aabb.maxX - aabb.minX);
                        double y = 0.5 * (aabb.maxY - aabb.minY);
                        double z = 0.5 * (aabb.maxZ - aabb.minZ);
                        ((ServerLevel) pLevel).sendParticles(ParticleTypes.ENCHANT, center.x, center.y, center.z, (int) (400 * z * x * y), x, y, z, 0.3);
                    }
                });
        if (!pLevel.isClientSide) {
            NetworkRegister.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> pPlayer), new VectorInversionParticleToClient(pPlayer.getX(),pPlayer.getEyeY(),pPlayer.getZ(),10,3,400));
        }
        pPlayer.getCooldowns().addCooldown(this, 40);
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
        return InteractionResultHolder.success(itemStack);
    }
}
