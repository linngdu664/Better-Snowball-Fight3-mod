package com.linngdu664.bsf.item.misc;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.network.ForwardConeParticlesSender;
import com.linngdu664.bsf.network.Network;
import com.linngdu664.bsf.util.BSFMthUtil;
import com.linngdu664.bsf.util.TargetGetter;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BasinOfSnowItem extends Item {
    public BasinOfSnowItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        List<LivingEntity> list = TargetGetter.getTargetList(pPlayer, LivingEntity.class, 8);
        Vec3 cameraVec = Vec3.directionFromRotation(pPlayer.getXRot(), pPlayer.getYRot());
        if (!pLevel.isClientSide) {
            Network.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> pPlayer), new ForwardConeParticlesSender(pPlayer, cameraVec, 4.5F, 30, 0.5F, 0.2));
            for (LivingEntity livingEntity : list) {
                if (!(livingEntity instanceof BSFSnowGolemEntity) && !(livingEntity instanceof SnowGolem) && !(livingEntity instanceof ArmorStand)) {
                    Vec3 rVec1 = new Vec3(livingEntity.getX() - pPlayer.getX(), livingEntity.getEyeY() - pPlayer.getEyeY() + 0.2, livingEntity.getZ() - pPlayer.getZ());
                    Vec3 rVec2 = new Vec3(rVec1.x, livingEntity.getY() - pPlayer.getEyeY(), rVec1.z);
                    if (BSFMthUtil.vec3AngleCos(rVec1, cameraVec) > 0.9363291776 && isNotBlocked(rVec1, rVec2, pPlayer, pLevel)) {
                        float r = (float) rVec1.length();
                        int t;
                        if (r < 5.0F) {
                            t = 180;
                            if (livingEntity.getTicksFrozen() < t) {
                                livingEntity.setTicksFrozen(t);
                            }
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (livingEntity.getTicksFrozen() * 0.5), 2));
                        } else if (r < 8.0F) {
                            t = (int) (180.0F - 6.6666667F * (r - 5.0F) * (r - 5.0F) * (r - 5.0F));
                            if (livingEntity.getTicksFrozen() < t) {
                                livingEntity.setTicksFrozen(t);
                            }
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (livingEntity.getTicksFrozen() * 0.5), 1));
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

    /**
     * Check whether there are solid blocks on head-head and head-feet line segments (See param). Specially designed for
     * basin of snow/powder snow.
     *
     * @param rVec   The vector from attacker's head to target's head.
     * @param rVec1  The vector from attacker's head to target's feet.
     * @param player The attacker.
     * @param level  The attacker's level.
     * @return Both rVec and rVec1 are blocked by solid block: false. Otherwise: true.
     */
    public boolean isNotBlocked(Vec3 rVec, Vec3 rVec1, Player player, Level level) {
        double offsetX = 0.25 * rVec.z * Mth.fastInvSqrt(BSFMthUtil.modSqr(rVec.x, rVec.z));
        double offsetZ = 0.25 * rVec.x * Mth.fastInvSqrt(BSFMthUtil.modSqr(rVec.x, rVec.z));
        double x = player.getX();
        double y = player.getEyeY();
        double z = player.getZ();
        Vec3 n = rVec.normalize().scale(0.25);
        int l = (int) (4 * rVec.length());
        boolean flag = true;
        int k;
        for (int i = 0; i < l; i++) {
            k = detectBlock(level, offsetX, offsetZ, x, y, z);
            if (k > 1) {
                flag = false;
                break;
            }
            x += n.x;
            y += n.y;
            z += n.z;
        }
        x = player.getX();
        y = player.getEyeY();
        z = player.getZ();
        n = rVec1.normalize().scale(0.25);
        l = (int) (4 * rVec1.length());
        for (int i = 0; i < l; i++) {
            k = detectBlock(level, offsetX, offsetZ, x, y, z);
            if (k > 1) {
                return flag;
            }
            x += n.x;
            y += n.y;
            z += n.z;
        }
        return true;
    }

    private int detectBlock(Level level, double offsetX, double offsetZ, double x, double y, double z) {
        int k = 0;
        for (int j = -1; j <= 1; j++) {
            BlockPos blockPos = new BlockPos(Mth.floor(x - offsetX * j), Mth.floor(y), Mth.floor(z + offsetZ * j));
            BlockState blockState = level.getBlockState(blockPos);
            if (blockState.blocksMotion()) {
                k++;
            }
        }
        return k;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("basin_of_snow.tooltip1", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("basin_of_snow.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_AQUA));
    }
}
