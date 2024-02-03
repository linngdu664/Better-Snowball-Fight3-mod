package com.linngdu664.bsf.item.weapon;

import com.linngdu664.bsf.block.LooseSnowBlock;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.network.ForwardConeParticlesToClient;
import com.linngdu664.bsf.network.ForwardRaysParticlesToClient;
import com.linngdu664.bsf.registry.EffectRegister;
import com.linngdu664.bsf.registry.NetworkRegister;
import com.linngdu664.bsf.registry.ParticleRegister;
import com.linngdu664.bsf.registry.SoundRegister;
import com.linngdu664.bsf.util.BSFMthUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class ImplosionSnowballCannonItem extends AbstractBSFWeaponItem {
    public static final int TYPE_FLAG = 64;

    public ImplosionSnowballCannonItem() {
        super(1000, Rarity.RARE, TYPE_FLAG);    // 暂定耐久
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer.hasEffect(EffectRegister.WEAPON_JAM.get())) {
            return InteractionResultHolder.fail(itemStack);
        }
        if (!pLevel.isClientSide) {
            ItemStack stack = getAmmo(pPlayer, itemStack);
            if (stack != null || pPlayer.isCreative()) {
                ServerLevel serverLevel = (ServerLevel) pLevel;
                serverLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundRegister.SNOWBALL_CANNON_SHOOT.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (serverLevel.getRandom().nextFloat() * 0.4F + 0.8F));
                AABB aabb = pPlayer.getBoundingBox().inflate(8);
                Vec3 cameraVec = Vec3.directionFromRotation(pPlayer.getXRot(), pPlayer.getYRot());
                Vec3 eyePos = pPlayer.getEyePosition();
                serverLevel.getEntities(pPlayer, aabb, p -> !p.isSpectator() && p.distanceToSqr(pPlayer) < 64 && BSFMthUtil.vec3AngleCos(p.getEyePosition().subtract(eyePos), cameraVec) > 0.9363291776)
                        .forEach(p -> {
                            double r = p.distanceTo(pPlayer);
                            double d1 = r < 4 ? 0.2 : -3.3333333e-3 * r * r * r + 4.0833333e-2 * r * r - 0.16666666 * r + 0.42666667;
                            Vec3 vec3 = p.getEyePosition().subtract(eyePos).scale(d1);
                            p.push(vec3.x, vec3.y, vec3.z);
                            p.hurt(serverLevel.damageSources().playerAttack(pPlayer), 1);
                            if (p instanceof ServerPlayer serverPlayer) {
                                serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(p));
                            }
                        });
                BlockPos.betweenClosedStream(aabb)
                        .filter(p -> serverLevel.getBlockState(p).getBlock() instanceof LooseSnowBlock && BSFMthUtil.vec3AngleCos(p.getCenter().subtract(eyePos), cameraVec) > 0.9363291776)
                        .forEach(p -> {
                            serverLevel.setBlockAndUpdate(p, Blocks.AIR.defaultBlockState());
                            serverLevel.playSound(null, p.getX(), p.getY(), p.getZ(), SoundEvents.SNOW_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F / (serverLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                            NetworkRegister.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> pPlayer), new ForwardRaysParticlesToClient(new Vec3(p.getX(), p.getY(), p.getZ()), new Vec3(p.getX() + 1, p.getY() + 1, p.getZ() + 1), eyePos.subtract(p.getCenter()), 0.2, 0.6, 10));
                        });
                serverLevel.sendParticles(ParticleRegister.IMPULSE.get(), pPlayer.getX() + cameraVec.x, pPlayer.getEyeY() + cameraVec.y, pPlayer.getZ() + cameraVec.z, 1, 0, 0, 0, 0);
                NetworkRegister.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> pPlayer), new ForwardConeParticlesToClient(pPlayer.getEyePosition(), cameraVec, 4.5F, 30, 0.5F, 0.2));
                itemStack.hurtAndBreak(1, pPlayer, p -> p.broadcastBreakEvent(pUsedHand));
                pPlayer.getCooldowns().addCooldown(this, 60);
                if (stack != null) {
                    consumeAmmo(stack, pPlayer);
                }
                pPlayer.awardStat(Stats.ITEM_USED.get(this));
            }
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public ILaunchAdjustment getLaunchAdjustment(double damageDropRate, Item snowball) {
        return null;
    }

    @Override
    public boolean isAllowBulkedSnowball() {
        return true;
    }
}
