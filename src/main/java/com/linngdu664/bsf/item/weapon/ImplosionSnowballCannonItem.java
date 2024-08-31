package com.linngdu664.bsf.item.weapon;

import com.linngdu664.bsf.block.LooseSnowBlock;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.network.ForwardRaysParticlesToClient;
import com.linngdu664.bsf.particle.util.BSFParticleType;
import com.linngdu664.bsf.registry.EffectRegister;
import com.linngdu664.bsf.registry.NetworkRegister;
import com.linngdu664.bsf.registry.SoundRegister;
import com.linngdu664.bsf.util.BSFCommonUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

public class ImplosionSnowballCannonItem extends Item {
    public static final int TYPE_FLAG = 64;
    public static final int DISTANCE = 16;
    public static final int RADIUM = 3;
    public static final double PUSH_POWER = 1;
    public static final double HURT_POWER = 1;

    public ImplosionSnowballCannonItem() {
        super(new Properties().stacksTo(1).durability(1000).rarity(Rarity.EPIC));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer.hasEffect(EffectRegister.WEAPON_JAM.get())) {
            return InteractionResultHolder.fail(itemStack);
        }
        if (!pLevel.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) pLevel;
            serverLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundRegister.SNOWBALL_CANNON_SHOOT.get(), SoundSource.NEUTRAL, 1.0F, 1.0F / (serverLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            Vec3 cameraVec = pPlayer.getViewVector(1);
            AABB aabb = pPlayer.getBoundingBox().inflate(RADIUM).expandTowards(cameraVec.scale(DISTANCE + RADIUM));

            BlockPos.betweenClosedStream(aabb)
                    .filter(p -> serverLevel.getBlockState(p).getBlock() instanceof LooseSnowBlock && BSFCommonUtil.pointOnTheFrontConeArea(pPlayer.getViewVector(1f), pPlayer.getEyePosition(1), p.getCenter(), RADIUM, DISTANCE))
                    .forEach(p -> {
                        serverLevel.setBlockAndUpdate(p, Blocks.AIR.defaultBlockState());
                        serverLevel.playSound(null, p.getX(), p.getY(), p.getZ(), SoundEvents.SNOW_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F / (serverLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                        NetworkRegister.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> pPlayer), new ForwardRaysParticlesToClient(new Vec3(p.getX(), p.getY(), p.getZ()), new Vec3(p.getX() + 1, p.getY() + 1, p.getZ() + 1), cameraVec, 0.2, 0.6, 10, BSFParticleType.SNOWFLAKE.ordinal()));
                    });

            serverLevel.getEntitiesOfClass(
                    Entity.class,
                    aabb,
                    p -> !pPlayer.equals(p) && BSFCommonUtil.pointOnTheFrontConeArea(pPlayer.getViewVector(1f), pPlayer.getEyePosition(1), p.getBoundingBox().getCenter(), RADIUM, DISTANCE)
            ).forEach(p -> {
                Vec3 pPos = p.getBoundingBox().getCenter();
                double d = BSFCommonUtil.vec3Projection(pPos.subtract(pPlayer.getEyePosition(1)), cameraVec);
                System.out.println(d);
                if (d<=0){
                    return;
                }
                Vec3 projPos = pPos.add(cameraVec.scale(-d));
                BlockHitResult blockHitResult = pLevel.clip(new ClipContext(pPos, projPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, p));
                if (blockHitResult.getType()!=BlockHitResult.Type.MISS){
                    return;
                }
                double basePower = Math.log(DISTANCE + 1 - d);
                Vec3 pushVec = cameraVec.scale(basePower * PUSH_POWER);
                if (p instanceof LivingEntity) {
                    p.hurt(serverLevel.damageSources().flyIntoWall(), (float) (basePower * HURT_POWER));
                }
                p.push(pushVec.x, pushVec.y, pushVec.z);
                if (p instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(p));
                }
            });
            itemStack.hurtAndBreak(1, pPlayer, p -> p.broadcastBreakEvent(pUsedHand));
            pPlayer.getCooldowns().addCooldown(this, 60);
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
        }
        ScreenshakeHandler.addScreenshake((new ScreenshakeInstance(8)).setIntensity(1.5f).setEasing(Easing.BOUNCE_IN));
        return InteractionResultHolder.pass(itemStack);
    }

}
