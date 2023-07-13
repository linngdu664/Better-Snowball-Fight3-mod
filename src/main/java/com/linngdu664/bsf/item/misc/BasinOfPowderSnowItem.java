package com.linngdu664.bsf.item.misc;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.network.ForwardConeParticlesSender;
import com.linngdu664.bsf.network.Network;
import com.linngdu664.bsf.util.BSFMthUtil;
import com.linngdu664.bsf.util.TargetGetter;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BasinOfPowderSnowItem extends BasinOfSnowItem {
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        List<LivingEntity> list = TargetGetter.getTargetList(pPlayer, LivingEntity.class, 8);
        Vec3 cameraVec = Vec3.directionFromRotation(pPlayer.getXRot(), pPlayer.getYRot());
        if (!pLevel.isClientSide) {
            Network.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> pPlayer), new ForwardConeParticlesSender(pPlayer.getEyePosition(), cameraVec, 4.5F, 30, 0.5F, 0.2));
            for (LivingEntity livingEntity : list) {
                if (!(livingEntity instanceof BSFSnowGolemEntity) && !(livingEntity instanceof SnowGolem) && !(livingEntity instanceof ArmorStand)) {
                    Vec3 rVec1 = new Vec3(livingEntity.getX() - pPlayer.getX(), livingEntity.getEyeY() - pPlayer.getEyeY() + 0.2, livingEntity.getZ() - pPlayer.getZ());
                    Vec3 rVec2 = new Vec3(rVec1.x, livingEntity.getY() - pPlayer.getEyeY(), rVec1.z);
                    if (BSFMthUtil.vec3AngleCos(rVec1, cameraVec) > 0.9363291776 && isNotBlocked(rVec1, rVec2, pPlayer, pLevel)) {
                        float r = (float) rVec1.length();
                        int t = 0;
                        if (r < 3.0F) {
                            t = 240;
                            if (livingEntity.getTicksFrozen() < t) {
                                livingEntity.setTicksFrozen(t);
                            }
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (livingEntity.getTicksFrozen() * 0.5), 3));
                        } else if (r < 6.0F) {
                            t = (int) (240.0F - (r - 3.0F) * (r - 3.0F) * (r - 3.0F));
                            if (livingEntity.getTicksFrozen() < t) {
                                livingEntity.setTicksFrozen(t);
                            }
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (livingEntity.getTicksFrozen() * 0.5), 2));
                        } else if (r < 8F) {
                            t = (int) (-15.375F * (r - 8.0F) * (r * (r - 9.4146341F) + 27.414634F));
                            if (livingEntity.getTicksFrozen() < t) {
                                livingEntity.setTicksFrozen(t);
                            }
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (livingEntity.getTicksFrozen() * 0.5), 1));
                        }
                        if (livingEntity.getTicksFrozen() < t) {
                            livingEntity.setTicksFrozen(t);
                        }
                        livingEntity.hurt(pLevel.damageSources().playerAttack(pPlayer), Float.MIN_VALUE);
                    }
                }
            }
        }
        if (!pPlayer.getAbilities().instabuild) {
            ItemStack newStack = new ItemStack(ItemRegister.EMPTY_BASIN.get(), itemStack.getCount());
            pPlayer.setItemInHand(pUsedHand, newStack);
        }
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.POWDER_SNOW_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("basin_of_snow.tooltip1", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("basin_of_powder_snow.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_AQUA));
    }
}
