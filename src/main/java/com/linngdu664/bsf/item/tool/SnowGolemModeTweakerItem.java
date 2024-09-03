package com.linngdu664.bsf.item.tool;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
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
    public void onCraftedBy(ItemStack pStack, @NotNull Level pLevel, @NotNull Player pPlayer) {
        pStack.getOrCreateTag().putByte("Status", (byte) 0);
        pStack.getOrCreateTag().putByte("Locator", (byte) 0);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        Options options = Minecraft.getInstance().options;
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snow_golem_mode_tweaker.tooltip", null, new Object[]{options.keyShift.getTranslatedKeyMessage()})).withStyle(ChatFormatting.DARK_GRAY));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snow_golem_mode_tweaker1.tooltip", null, new Object[]{options.keySprint.getTranslatedKeyMessage()})).withStyle(ChatFormatting.DARK_GRAY));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snow_golem_mode_tweaker2.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        CompoundTag tag = pStack.getOrCreateTag();
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents(switch (tag.getByte("Locator")) {
            case 0 -> "snow_golem_locator_monster.tip";
            case 1 -> "snow_golem_locator_specify.tip";
            case 2 -> "snow_golem_locator_enemy_team.tip";
            default -> "snow_golem_locator_all_creatures.tip";
        }, null, new Object[0])).withStyle(ChatFormatting.DARK_GRAY));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents(switch (tag.getByte("Status")) {
            case 0 -> "snow_golem_standby.tip";
            case 1 -> "snow_golem_follow.tip";
            case 2 -> "snow_golem_follow_and_attack.tip";
            case 3 -> "snow_golem_attack.tip";
            default -> "snow_golem_turret.tip";
        }, null, new Object[0])).withStyle(ChatFormatting.DARK_GRAY));
    }
}
