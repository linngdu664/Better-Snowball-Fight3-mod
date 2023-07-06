package com.linngdu664.bsf.item.tool;

import com.linngdu664.bsf.item.ItemRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SnowballClampItem extends TieredItem {
    public SnowballClampItem(Tier pTier) {
        super(pTier, new Properties().stacksTo(1));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        ItemStack itemStack = pContext.getItemInHand();
        Level level = pContext.getLevel();
        Block block = level.getBlockState(pContext.getClickedPos()).getBlock();
        if ((block == Blocks.SNOW_BLOCK || block == Blocks.SNOW || block == Blocks.POWDER_SNOW) && player != null) {
            if (player.getMainHandItem().isEmpty() || player.getOffhandItem().isEmpty()) {
                player.getInventory().placeItemBackInInventory(new ItemStack(ItemRegister.SMOOTH_SNOWBALL.get(), 1), true);
                itemStack.hurtAndBreak(1, player, (e) -> e.broadcastBreakEvent(pContext.getHand()));
            }
            player.awardStat(Stats.ITEM_USED.get(this));
        }
        return InteractionResult.PASS;
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack pStack, @NotNull Player pPlayer, @NotNull LivingEntity pInteractionTarget, @NotNull InteractionHand pUsedHand) {
        if (pInteractionTarget instanceof SnowGolem && (pPlayer.getMainHandItem().isEmpty() || pPlayer.getOffhandItem().isEmpty())) {
            pPlayer.getInventory().placeItemBackInInventory(new ItemStack(ItemRegister.SMOOTH_SNOWBALL.get(), 1), true);
            pStack.hurtAndBreak(1, pPlayer, (e) -> e.broadcastBreakEvent(pUsedHand));
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_clamp.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
    }
}
