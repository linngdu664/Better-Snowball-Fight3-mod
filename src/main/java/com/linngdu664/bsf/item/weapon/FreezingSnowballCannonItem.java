package com.linngdu664.bsf.item.weapon;

import com.linngdu664.bsf.entity.BSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.nomal.IceSnowballEntity;
import com.linngdu664.bsf.entity.snowball.special.FrozenSnowballEntity;
import com.linngdu664.bsf.network.ForwardConeParticlesSender;
import com.linngdu664.bsf.network.Network;
import com.linngdu664.bsf.util.LaunchFrom;
import com.linngdu664.bsf.util.LaunchFunc;
import com.linngdu664.bsf.util.SoundRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FreezingSnowballCannonItem extends SnowballCannonItem {
    public static LaunchFunc getLaunchFunc(double damageDropRate) {
        return new LaunchFunc() {
            @Override
            public LaunchFrom getLaunchFrom() {
                return LaunchFrom.FREEZING_CANNON;
            }

            @Override
            public void launchProperties(BSFSnowballEntity bsfSnowballEntity) {
                if (bsfSnowballEntity instanceof IceSnowballEntity || bsfSnowballEntity instanceof FrozenSnowballEntity) {
                    bsfSnowballEntity.setFrozenTicks(200).setBlazeDamage(bsfSnowballEntity.getBlazeDamage() + 4);
                } else {
                    bsfSnowballEntity.setFrozenTicks(140).setBlazeDamage(bsfSnowballEntity.getBlazeDamage() + 1);
                }
                bsfSnowballEntity.setBlazeDamage(bsfSnowballEntity.getBlazeDamage() * (float) damageDropRate).setPunch(damageDropRate * 1.51);
            }
        };
    }

    @Override
    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            int i = this.getUseDuration(pStack) - pTimeLeft;
            float f = getPowerForTime(i);
            if (f >= 0.1F) {
                ItemStack itemStack = findAmmo(player, false, true);
                if (itemStack != null) {
                    BSFSnowballEntity snowballEntity = ItemToEntity(itemStack.getItem(), player, pLevel, getLaunchFunc(f));
                    BSFShootFromRotation(snowballEntity, player.getXRot(), player.getYRot(), f * 3.0F, 0.5F);
                    pLevel.addFreshEntity(snowballEntity);
                    pStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(p.getUsedItemHand()));
                    Vec3 cameraVec = Vec3.directionFromRotation(player.getXRot(), player.getYRot());
                    //add push
                    if (pLevel.isClientSide()) {
                        player.push(-0.2 * cameraVec.x * f, -0.2 * cameraVec.y * f, -0.2 * cameraVec.z * f);
                        //add particles
                    } else {
                        Network.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new ForwardConeParticlesSender(player, cameraVec, 4.5F, 90, 1.5F, 0.1));
                        pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundRegister.SNOWBALL_CANNON_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    }
                    consumeAmmo(itemStack, player);
                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_cannon4.tooltip", null, new Object[]{})).withStyle(ChatFormatting.AQUA));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_cannon2.tooltip", null, new Object[]{})).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_cannon3.tooltip", null, new Object[]{})).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_cannon.tooltip", null, new Object[]{})).withStyle(ChatFormatting.DARK_AQUA));
    }
}
