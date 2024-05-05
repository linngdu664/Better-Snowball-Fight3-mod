package com.linngdu664.bsf.item.tool;

import com.linngdu664.bsf.network.ForwardConeParticlesToClient;
import com.linngdu664.bsf.registry.NetworkRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class ColdCompressionJetEngineItem extends AbstractBSFEnhanceableToolItem {
    public ColdCompressionJetEngineItem() {
        super(Rarity.RARE, 400);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        BlockPos blockPos1 = new BlockPos((int) pEntity.getX(), (int) pEntity.getY(), (int) pEntity.getZ());
        if (pLevel.getBlockState(blockPos1).is(BlockTags.SNOW) || pLevel.getBlockState(blockPos1.below()).is(BlockTags.SNOW)) {
            if (pIsSelected) {
                pStack.setDamageValue(Math.max(pStack.getDamageValue() - 2, 0));
            } else {
                pStack.setDamageValue(Math.max(pStack.getDamageValue() - 1, 0));
            }
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (stack.getDamageValue() == stack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(stack);
        }
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onUseTick(@NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, @NotNull ItemStack pStack, int pRemainingUseDuration) {
        int i = this.getUseDuration(pStack)-pRemainingUseDuration;
        if (pStack.getDamageValue() == pStack.getMaxDamage() - 1) {
            releaseUsing(pStack, pLevel, pLivingEntity, pRemainingUseDuration);
            return;
        }
        Vec3 vec3 = Vec3.directionFromRotation(pLivingEntity.getXRot(), pLivingEntity.getYRot());
        Vec3 particlesPos = pLivingEntity.getEyePosition();
        if (i < ColdCompressionJetEngineItem.getStartupDuration()) {
            pStack.hurtAndBreak(1, pLivingEntity, p -> {});
            if (!pLevel.isClientSide) {
                NetworkRegister.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> pLivingEntity), new ForwardConeParticlesToClient(particlesPos, vec3.reverse().scale(2), 2F, 150, 2F, 0));
            }
            return;
        }
        if (i == ColdCompressionJetEngineItem.getStartupDuration()){
            Vec3 aVec = vec3.scale(2);
            pLivingEntity.push(aVec.x, aVec.y, aVec.z);
            if (!pLevel.isClientSide) {
                NetworkRegister.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> pLivingEntity), new ForwardConeParticlesToClient(particlesPos, vec3.reverse().scale(0.5), 5F, 10, 0.2F, 0));
            }
        }else{
            Vec3 aVec = vec3.scale(0.2);
            pLivingEntity.push(aVec.x, aVec.y, aVec.z);
        }
        if (vec3.y > 0) {
            pLivingEntity.resetFallDistance();
        }
        pStack.hurtAndBreak(1, pLivingEntity, p -> {});
        if (!pLevel.isClientSide) {
            NetworkRegister.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> pLivingEntity), new ForwardConeParticlesToClient(particlesPos, vec3.reverse(), 2F, 60, 0.5F, 0));
        }
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 72000;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.getItem().equals(newStack.getItem());
    }
    public static int getStartupDuration(){
        return 24;
    }
}
