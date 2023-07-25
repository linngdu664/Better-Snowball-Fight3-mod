package com.linngdu664.bsf.item.misc;

import com.linngdu664.bsf.registry.ItemRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmptyBasinItem extends Item {
    public EmptyBasinItem() {
        super(new Properties().stacksTo(16));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        ItemStack itemStack = pContext.getItemInHand();
        Block block = level.getBlockState(pContext.getClickedPos()).getBlock();
        InteractionHand hand = pContext.getHand();
        if (player != null) {
            if (block == Blocks.SNOW_BLOCK || block == Blocks.SNOW) {
                if (itemStack.getCount() > 1) {
                    player.getInventory().placeItemBackInInventory(new ItemStack(ItemRegister.BASIN_OF_SNOW.get()), true);
                    itemStack.shrink(1);
                } else {
                    ItemStack newStack = new ItemStack(ItemRegister.BASIN_OF_SNOW.get(), itemStack.getCount());
                    itemStack.shrink(1);
                    player.setItemInHand(hand, newStack);
                }
            } else if (block == Blocks.POWDER_SNOW) {
                if (itemStack.getCount() > 1) {
                    player.getInventory().placeItemBackInInventory(new ItemStack(ItemRegister.BASIN_OF_POWDER_SNOW.get()), true);
                    itemStack.shrink(1);
                } else {
                    ItemStack newStack = new ItemStack(ItemRegister.BASIN_OF_POWDER_SNOW.get(), itemStack.getCount());
                    itemStack.shrink(1);
                    player.setItemInHand(hand, newStack);
                }
            }
        }
        return super.useOn(pContext);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("empty_basin.tooltip1", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("empty_basin.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_AQUA));
    }
}
