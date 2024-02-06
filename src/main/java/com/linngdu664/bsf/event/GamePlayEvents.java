package com.linngdu664.bsf.event;

import com.linngdu664.bsf.util.BSFTeamSavedData;
import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.item.misc.IceSkatesItem;
import com.linngdu664.bsf.item.misc.SnowFallBootsItem;
import com.linngdu664.bsf.item.snowball.normal.SmoothSnowballItem;
import com.linngdu664.bsf.item.tank.SnowballTankItem;
import com.linngdu664.bsf.network.TeamMembersToClient;
import com.linngdu664.bsf.registry.EffectRegister;
import com.linngdu664.bsf.registry.EnchantmentRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.registry.NetworkRegister;
import com.linngdu664.bsf.util.BSFConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GamePlayEvents {
    public static final UUID SKATES_SPEED_ID = UUID.fromString("00a3641b-33e0-4022-8d92-1c7b74c380b0");

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player1 && event.getSource().getEntity() instanceof Player player2) {
            BSFTeamSavedData savedData = player1.getServer().overworld().getDataStorage().computeIfAbsent(BSFTeamSavedData::new, BSFTeamSavedData::new, "bsf_team");
            int id = savedData.getTeam(player1.getUUID());
            String msgId = event.getSource().getMsgId();
            if (id >= 0 && id == savedData.getTeam(player2.getUUID()) && msgId.equals("thrown") && !BSFConfig.enableFriendlyFire) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        BSFTeamSavedData savedData = player.getServer().overworld().getDataStorage().computeIfAbsent(BSFTeamSavedData::new, BSFTeamSavedData::new, "bsf_team");
        NetworkRegister.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> player), new TeamMembersToClient(savedData.getMembers(savedData.getTeam(player.getUUID()))));
    }

    @SubscribeEvent
    public static void onLivingUseItemTick(LivingEntityUseItemEvent.Tick event) {
        LivingEntity livingEntity = event.getEntity();
        ItemStack itemStack = event.getItem();
        if (EnchantmentHelper.getTagEnchantmentLevel(EnchantmentRegister.FLOATING_SHOOTING.get(), itemStack) > 0) {
            livingEntity.resetFallDistance();
            double vy = livingEntity.getDeltaMovement().y;
            livingEntity.push(0, -0.25 * vy, 0);
        }
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        Player player = event.getEntity();
        Entity entity = event.getTarget();
        Level level = player.level();
        Item item = player.getMainHandItem().getItem();
        if (!player.isSpectator() && entity instanceof LivingEntity target) {
            if (item instanceof SolidBucketItem) {
                if (!(target instanceof BSFSnowGolemEntity) && !(target instanceof SnowGolem)) {
                    if (target.getTicksFrozen() < 240) {
                        target.setTicksFrozen(240);
                    }
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 2));
                    target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 150, 1));
                }
                target.addEffect(new MobEffectInstance(EffectRegister.WEAPON_JAM.get(), 80, 0));
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.ITEM_SNOWBALL, target.getX(), target.getEyeY(), target.getZ(), 16, 0, 0, 0, 0);
                    serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, target.getX(), target.getEyeY(), target.getZ(), 16, 0, 0, 0, 0.04);
                }
                if (target instanceof Blaze) {
                    target.hurt(level.damageSources().playerAttack(player), 8);
                }
                if (!player.getAbilities().instabuild) {
                    player.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
                    player.getInventory().placeItemBackInInventory(new ItemStack(Items.BUCKET), true);
                }
            } else if (item instanceof SnowballItem || item instanceof SmoothSnowballItem) {
                if (!(target instanceof BSFSnowGolemEntity) && !(target instanceof SnowGolem)) {
                    if (target.getTicksFrozen() < 180) {
                        target.setTicksFrozen(180);
                    }
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));
                    target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 30, 1));
                }
                target.addEffect(new MobEffectInstance(EffectRegister.WEAPON_JAM.get(), 40, 0));
                if (!player.getAbilities().instabuild) {
                    player.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
                }
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.ITEM_SNOWBALL, target.getX(), target.getEyeY(), target.getZ(), 8, 0, 0, 0, 0);
                    serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, target.getX(), target.getEyeY(), target.getZ(), 8, 0, 0, 0, 0.04);
                }
                if (target instanceof Blaze) {
                    target.hurt(level.damageSources().playerAttack(player), 4);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        ItemStack itemStack = event.getCrafting();
        if (itemStack.getItem() instanceof SnowballTankItem) {
            itemStack.setDamageValue(itemStack.getMaxDamage());
        }
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player) {
            Level level = player.level();
            ItemStack shoes = player.getItemBySlot(EquipmentSlot.FEET);
            if (!level.isClientSide && shoes.getItem() instanceof SnowFallBootsItem) {
                int i = Mth.floor(player.getX());
                int j = Mth.floor(player.getY());
                int k = Mth.floor(player.getZ());
                Block block1 = level.getBlockState(new BlockPos(i, j, k)).getBlock();
                //Block block2 = level.getBlockState(new BlockPos(i, j - 1, k)).getBlock();
                if (level.getBlockState(new BlockPos(i, j, k)).is(BlockTags.SNOW) || level.getBlockState(new BlockPos(i, j - 1, k)).is(BlockTags.SNOW) || snowAroundPlayer(level, player, block1)) {
                    event.setDamageMultiplier(0);
                    float h = event.getDistance();
                    ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, player.getX(), player.getY(), player.getZ(), (int) h * 8, 0, 0, 0, h * 0.01);
                    shoes.hurtAndBreak((int) Math.ceil((h - 3) * 0.25), player, (p) -> p.broadcastBreakEvent(EquipmentSlot.FEET));
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOW_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                    if (EnchantmentHelper.getTagEnchantmentLevel(EnchantmentRegister.KINETIC_ENERGY_STORAGE.get(), shoes) > 0 && h > 5) {
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, (int) h * 6, EnchantmentHelper.getTagEnchantmentLevel(EnchantmentRegister.KINETIC_ENERGY_STORAGE.get(), shoes) - 1));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if (event.getName().equals(new ResourceLocation("minecraft:chests/shipwreck_treasure")) || event.getName().equals(new ResourceLocation("minecraft:chests/igloo_chest"))) {
            LootTable lootTable = event.getTable();
            lootTable.addPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1.0F))
                    .setBonusRolls(ConstantValue.exactly(0.0F))
                    .add(LootItem.lootTableItem(ItemRegister.SNOWBALL_CANNON_UPGRADE_SMITHING_TEMPLATE.get()))
                    .build());
            event.setTable(lootTable);
        } else if (event.getName().equals(new ResourceLocation("minecraft:chests/pillager_outpost"))) {
            LootTable lootTable = event.getTable();
            lootTable.addPool(LootPool.lootPool()
                    .setRolls(BinomialDistributionGenerator.binomial(2, 0.4F))
                    .setBonusRolls(ConstantValue.exactly(0.0F))
                    .add(LootItem.lootTableItem(ItemRegister.SNOWBALL_CANNON_UPGRADE_SMITHING_TEMPLATE.get()))
                    .build());
            event.setTable(lootTable);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            ItemStack shoes = player.getItemBySlot(EquipmentSlot.FEET);
            if (!shoes.isEmpty()) {
                if (shoes.getItem() instanceof IceSkatesItem && player.isSprinting() && player.onGround()) {
                    Level level = player.level();
                    BlockPos pos = player.blockPosition().below();
                    if (level.getBlockState(pos).is(BlockTags.ICE)) {
                        level.addParticle(ParticleTypes.SNOWFLAKE, player.getX(), player.getEyeY() - 1.4, player.getZ(), 0, 0, 0);
                        addSpeedGoodEffect(player);
                    } else {
                        addSpeedBadEffect(player);
                    }
                } else {
                    clearSpeedEffect(player);
                }
            }
        }
    }

    private static boolean snowAroundPlayer(Level level, Player player, Block block1) {
        int x = Mth.floor(player.getX());
        int y = Mth.floor(player.getY());
        int z = Mth.floor(player.getZ());
        if (block1.equals(Blocks.AIR)) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    BlockPos pos1 = new BlockPos(x + i, y, z + j);
                    BlockPos pos2 = new BlockPos(x + i, y - 1, z + j);
                    if (level.getBlockState(pos1).is(BlockTags.SNOW) || level.getBlockState(pos2).is(BlockTags.SNOW)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void clearSpeedEffect(Player player) {
        if (player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).getModifier(SKATES_SPEED_ID) != null) {
            player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).removeModifier(SKATES_SPEED_ID);
            player.setMaxUpStep(0.6f);
        }
    }

    private static void addSpeedGoodEffect(Player player) {
        AttributeModifier skatesSpeed = new AttributeModifier(SKATES_SPEED_ID, "skates_speed", 0.15, AttributeModifier.Operation.ADDITION);
        if (!player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).hasModifier(skatesSpeed)) {
            player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).addPermanentModifier(skatesSpeed);
            player.setMaxUpStep(2);
        }
    }

    private static void addSpeedBadEffect(Player player) {
        AttributeModifier skatesSpeed = new AttributeModifier(SKATES_SPEED_ID, "skates_speed", -0.25, AttributeModifier.Operation.MULTIPLY_BASE);
        if (!player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).hasModifier(skatesSpeed)) {
            player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).addPermanentModifier(skatesSpeed);
            player.setMaxUpStep(0.5f);
        }
    }
}
