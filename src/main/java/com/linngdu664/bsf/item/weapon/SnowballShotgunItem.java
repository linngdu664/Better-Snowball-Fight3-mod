package com.linngdu664.bsf.item.weapon;

import com.linngdu664.bsf.enchantment.EnchantmentRegister;
import com.linngdu664.bsf.entity.BSFSnowballEntity;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import com.linngdu664.bsf.item.snowball.special.ThrustSnowballItem;
import com.linngdu664.bsf.item.tank.AbstractSnowballTankItem;
import com.linngdu664.bsf.item.tank.special.ThrustSnowballTank;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SnowballShotgunItem extends AbstractBSFWeaponItem {
    public SnowballShotgunItem() {
        super(1145, Rarity.EPIC);
    }

    public LaunchFunc getLaunchFunc(double damageDropRate) {
        return new LaunchFunc() {
            @Override
            public LaunchFrom getLaunchFrom() {
                return LaunchFrom.SHOTGUN;
            }

            @Override
            public void launchProperties(BSFSnowballEntity bsfSnowballEntity) {
                bsfSnowballEntity.setPunch(1.51);
            }
        };
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (EnchantmentHelper.getTagEnchantmentLevel(EnchantmentRegister.SNOW_GOLEM_EXCLUSIVE.get(), stack) > 0) {
            return InteractionResultHolder.fail(stack);
        }
        double pushRank = 0.24;
        int i;
        for (i = 0; i < 4; i++) {
            ItemStack itemStack;
            if (player.isShiftKeyDown()) {
                itemStack = findPropulsionSnowballAmmo(player);
                if (itemStack == null) {
                    itemStack = findAmmo(player);
                }
            } else {
                itemStack = findAmmo(player);
            }
            if (itemStack != null) {
                BSFSnowballEntity snowballEntity = ItemToEntity(itemStack.getItem(), player, level, getLaunchFunc(1.0F));
                Item item = itemStack.getItem();
                if (item instanceof AbstractSnowballTankItem tank) {
                    item = tank.getSnowball();
                }
                if (item instanceof AbstractBSFSnowballItem snowball) {
                    pushRank += snowball.getPushRank();
                }
                if (!player.isShiftKeyDown()) {
                    BSFShootFromRotation(snowballEntity, player.getXRot(), player.getYRot(), 2.0F, 10.0F);
                    level.addFreshEntity(snowballEntity);
                }
                consumeAmmo(itemStack, player);
            } else {
                break;
            }
        }
        if (i > 0) {
            Vec3 cameraVec = Vec3.directionFromRotation(player.getXRot(), player.getYRot());
            if (!player.isShiftKeyDown()) {
                if (level.isClientSide()) {
                    player.push(-0.24 * cameraVec.x, -0.24 * cameraVec.y, -0.24 * cameraVec.z);
                } else {
                    Network.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new ForwardConeParticlesSender(player, cameraVec, 4.5F, 45, 1.5F, 0.1));
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundRegister.SHOTGUN_FIRE_2.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                }
            } else {
                if (level.isClientSide()) {
                    player.push(-pushRank * cameraVec.x, -pushRank * cameraVec.y, -pushRank * cameraVec.z);
                } else {
                    Network.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new ForwardConeParticlesSender(player, cameraVec, 4.5F, 45, 0.5F, 0.1));
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundRegister.SHOTGUN_FIRE_1.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                }
            }
            stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(p.getUsedItemHand()));
            player.getCooldowns().addCooldown(this, 20);
            player.awardStat(Stats.ITEM_USED.get(this));
        }
        return InteractionResultHolder.pass(stack);
    }

    private ItemStack findPropulsionSnowballAmmo(Player player) {
        int k = player.getInventory().getContainerSize();
        for (int j = 0; j < k; j++) {
            ItemStack itemStack = player.getInventory().getItem(j);
            if (itemStack.getItem() instanceof ThrustSnowballTank) {
                return itemStack;
            }
        }
        for (int j = 0; j < k; j++) {
            ItemStack itemStack = player.getInventory().getItem(j);
            if (itemStack.getItem() instanceof ThrustSnowballItem) {
                return itemStack;
            }
        }
        return null;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_shotgun1.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_PURPLE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_shotgun2.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_PURPLE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_shotgun3.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_shotgun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_AQUA));
    }

    @Override
    protected ItemStack findAmmo(Player player) {
        int k = player.getInventory().getContainerSize();
        for (int j = 0; j < k; j++) {
            ItemStack itemStack = player.getInventory().getItem(j);
            if (itemStack.getItem() instanceof AbstractSnowballTankItem tank && tank.getSnowball().canBeLaunchedByNormalWeapon()) {
                return itemStack;
            }
        }
        for (int j = 0; j < k; j++) {
            ItemStack itemStack = player.getInventory().getItem(j);
            if (itemStack.getItem() instanceof AbstractBSFSnowballItem snowball && snowball.canBeLaunchedByNormalWeapon()) {
                return itemStack;
            }
        }
        return null;
    }
}
