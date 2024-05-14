package com.linngdu664.bsf.item.tool;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.network.ForwardConeParticlesToClient;
import com.linngdu664.bsf.particle.util.BSFParticleType;
import com.linngdu664.bsf.registry.EffectRegister;
import com.linngdu664.bsf.registry.NetworkRegister;
import com.linngdu664.bsf.util.BSFCommonUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class BasinItem extends Item {
    public BasinItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        ItemStack itemStack = pContext.getItemInHand();
        if (!itemStack.getOrCreateTag().contains("SnowType")) {
            Player player = pContext.getPlayer();
            Level level = pContext.getLevel();
            Block block = level.getBlockState(pContext.getClickedPos()).getBlock();
            if (player != null) {
                if (block.equals(Blocks.SNOW_BLOCK) || block.equals(Blocks.SNOW)) {
                    itemStack.getOrCreateTag().putByte("SnowType", (byte) 1);
                    player.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResult.SUCCESS;
                } else if (block.equals(Blocks.POWDER_SNOW)) {
                    itemStack.getOrCreateTag().putByte("SnowType", (byte) 2);
                    player.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!itemStack.getOrCreateTag().contains("SnowType")) {
            return InteractionResultHolder.pass(itemStack);
        }
        Vec3 cameraVec = Vec3.directionFromRotation(pPlayer.getXRot(), pPlayer.getYRot());
        if (!pLevel.isClientSide) {
            List<LivingEntity> list = pLevel.getEntitiesOfClass(LivingEntity.class, pPlayer.getBoundingBox().inflate(8), p -> {
                if (p instanceof ArmorStand || p.isSpectator() || p.distanceToSqr(pPlayer) >= 64) {
                    return false;
                }
                Vec3 vec31 = new Vec3(p.getX() - pPlayer.getX(), p.getEyeY() - pPlayer.getEyeY() + 0.2, p.getZ() - pPlayer.getZ());
                Vec3 vec32 = new Vec3(p.getX() - pPlayer.getX(), p.getY() - pPlayer.getEyeY(), p.getZ() - pPlayer.getZ());
                return BSFCommonUtil.vec3AngleCos(vec31, cameraVec) > 0.9363291776 && isNotBlocked(vec31, vec32, pPlayer, pLevel);
            });
            NetworkRegister.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> pPlayer), new ForwardConeParticlesToClient(pPlayer.getEyePosition(), cameraVec, 4.5F, 30, 0.5F, 0.2, BSFParticleType.SNOWFLAKE.ordinal()));
            if (itemStack.getOrCreateTag().getByte("SnowType") == 1) {
                addEffectsToLivingEntities(list, pPlayer, pLevel, p -> p < 5F ? 180 : (int) (180F - 6.6666667F * (p - 5F) * (p - 5F) * (p - 5F)), p -> p < 5F ? 2 : 1, 20);
            } else {
                addEffectsToLivingEntities(list, pPlayer, pLevel, p -> p < 4F ? 240 : (int) (-4F * p * p * p + 49F * p * p - 200F * p + 512F), p -> p < 3.0F ? 3 : (p < 6.0F ? 2 : 1), 30);
            }
            if (!pPlayer.getAbilities().instabuild) {
                itemStack.getOrCreateTag().remove("SnowType");
            }
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.POWDER_SNOW_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public @NotNull Component getName(ItemStack pStack) {
        CompoundTag compoundTag = pStack.getOrCreateTag();
        if (compoundTag.contains("SnowType")) {
            if (compoundTag.getByte("SnowType") == 1) {
                return MutableComponent.create(new TranslatableContents("item.bsf.basin_of_snow", null, new Object[0]));
            }
            return MutableComponent.create(new TranslatableContents("item.bsf.basin_of_powder_snow", null, new Object[0]));
        }
        return super.getName(pStack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getOrCreateTag();
        if (!tag.contains("SnowType")) {
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("empty_basin.tooltip1", null, new Object[0])).withStyle(ChatFormatting.BLUE));
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("empty_basin.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_AQUA));
        } else {
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("basin_of_snow.tooltip1", null, new Object[0])).withStyle(ChatFormatting.BLUE));
            if (tag.getByte("SnowType") == 1) {
                pTooltipComponents.add(MutableComponent.create(new TranslatableContents("basin_of_snow.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_AQUA));
            } else {
                pTooltipComponents.add(MutableComponent.create(new TranslatableContents("basin_of_powder_snow.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_AQUA));
            }
        }
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
        double offsetX = 0.25 * rVec.z * Mth.invSqrt(BSFCommonUtil.lengthSqr(rVec.x, rVec.z));
        double offsetZ = 0.25 * rVec.x * Mth.invSqrt(BSFCommonUtil.lengthSqr(rVec.x, rVec.z));
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

    private void addEffectsToLivingEntities(List<LivingEntity> list, Player pPlayer, Level pLevel, Function<Float, Integer> tFunc, Function<Float, Integer> ampFunc, int jamTime) {
        for (LivingEntity livingEntity : list) {
            if (!(livingEntity instanceof BSFSnowGolemEntity) && !(livingEntity instanceof SnowGolem)) {
                float r = pPlayer.distanceTo(livingEntity);
                int t = tFunc.apply(r);
                int amp = ampFunc.apply(r);
                if (livingEntity.getTicksFrozen() < t) {
                    livingEntity.setTicksFrozen(t);
                }
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (t * 0.5), amp));
                livingEntity.hurt(pLevel.damageSources().playerAttack(pPlayer), Float.MIN_VALUE);
            }
            livingEntity.addEffect(new MobEffectInstance(EffectRegister.WEAPON_JAM.get(), jamTime, 0));
        }
    }
}
