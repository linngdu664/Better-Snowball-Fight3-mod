package com.linngdu664.bsf.item.weapon;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.nomal.SmoothSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.entity.snowball.util.LaunchFrom;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.item.snowball.normal.SmoothSnowballItem;
import com.linngdu664.bsf.particle.ParticleRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SnowmanInHandItem extends AbstractBSFWeaponItem {
    public SnowmanInHandItem() {
        super(256, Rarity.EPIC);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if (pLivingEntity instanceof Player player) {

            HitResult hitResult = player.pick(3, 0, false);
            BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
            Block block = player.level().getBlockState(blockPos).getBlock();

            float pitch = player.getXRot();
            float yaw = player.getYRot();
            Vec3 cameraVec = Vec3.directionFromRotation(pitch, yaw);

            if ((block == Blocks.SNOW_BLOCK || block == Blocks.SNOW || block == Blocks.POWDER_SNOW) ){//charge
                pStack.setDamageValue(pStack.getDamageValue() - 1);
                if (!pLevel.isClientSide()){
                    pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOW_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                    ((ServerLevel) pLevel).sendParticles(ParticleRegister.SHORT_TIME_SNOWFLAKE.get(), player.getX()+cameraVec.x*0.5, player.getEyeY()+cameraVec.y*0.5, player.getZ()+cameraVec.z*0.5, 1, 0, 0, 0, 0.04);
                }
            }else if (pStack.getDamageValue() < pStack.getMaxDamage()-1){//attack
                if (pLevel.isClientSide()){
                    player.push(-cameraVec.x * 0.025, -cameraVec.y * 0.025, -cameraVec.z * 0.025);
                }else{
                    for (int i = 0; i < 3; i++) {
                        SmoothSnowballEntity snowballEntity = new SmoothSnowballEntity(player, pLevel, getLaunchAdjustment(1, ItemRegister.SMOOTH_SNOWBALL.get()));
                        if (player.isShiftKeyDown()){
                            BSFShootFromRotation(snowballEntity, pitch, yaw, 1, 10.0F);
                        }else{
                            BSFShootFromRotation(snowballEntity, pitch, yaw, 1, 15.0F);
                        }

                        pLevel.addFreshEntity(snowballEntity);
                    }
                    pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
                    pStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
                }
            }



        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.BOW;
    }
    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 1200;
    }
    @Override
    protected ItemStack findAmmo(Player player) {
        return null;
    }

    @Override
    public ILaunchAdjustment getLaunchAdjustment(double damageDropRate, Item snowball) {
        return new ILaunchAdjustment() {
            @Override
            public double adjustPunch(double punch) {
                return punch+1;
            }

            @Override
            public int adjustWeaknessTicks(int weaknessTicks) {
                return weaknessTicks;
            }

            @Override
            public int adjustFrozenTicks(int frozenTicks) {
                return frozenTicks;
            }

            @Override
            public float adjustDamage(float damage) {
                return damage;
            }

            @Override
            public float adjustBlazeDamage(float blazeDamage) {
                return blazeDamage;
            }

            @Override
            public LaunchFrom getLaunchFrom() {
                return LaunchFrom.SNOWMAN_IN_HAND;
            }
        };
    }
}
