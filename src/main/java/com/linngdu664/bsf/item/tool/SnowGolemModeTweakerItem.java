package com.linngdu664.bsf.item.tool;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SnowGolemModeTweakerItem extends Item {
    public SnowGolemModeTweakerItem() {
        super(new Properties().rarity(Rarity.UNCOMMON).stacksTo(1));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            CompoundTag tag = itemStack.getOrCreateTag();
            if (pPlayer.isShiftKeyDown()) {
                boolean useLocator = !tag.getBoolean("UseLocator");
                tag.putBoolean("UseLocator", useLocator);
                pPlayer.displayClientMessage(new TranslatableComponent(useLocator ? "snow_golem_locator_true.tip" : "snow_golem_locator_false.tip"), false);
            } else {
                byte status = (byte) ((tag.getByte("Status") + 1) % 5);
                tag.putByte("Status", status);
                pPlayer.displayClientMessage(new TranslatableComponent(switch (status) {
                    case 0 -> "snow_golem_standby.tip";
                    case 1 -> "snow_golem_follow.tip";
                    case 2 -> "snow_golem_follow_and_attack.tip";
                    case 3 -> "snow_golem_attack.tip";
                    default -> "snow_golem_turret.tip";
                }), false);
            }
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.DISPENSER_DISPENSE, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
        }
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void onCraftedBy(ItemStack pStack, @NotNull Level pLevel, @NotNull Player pPlayer) {
        pStack.getOrCreateTag().putByte("Status", (byte) 0);
        pStack.getOrCreateTag().putBoolean("UseLocator", false);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslatableComponent("snow_golem_mode_tweaker.tooltip").withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(new TranslatableComponent("snow_golem_mode_tweaker1.tooltip").withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(new TranslatableComponent("snow_golem_mode_tweaker2.tooltip").withStyle(ChatFormatting.BLUE));
    }
}
