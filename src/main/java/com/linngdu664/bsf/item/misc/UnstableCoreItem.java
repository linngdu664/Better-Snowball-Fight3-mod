package com.linngdu664.bsf.item.misc;

import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.util.ItemGroup;
import com.linngdu664.bsf.util.SoundRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UnstableCoreItem extends Item {
    public UnstableCoreItem() {
        super(new Properties().tab(ItemGroup.MAIN).rarity(Rarity.EPIC));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();
        Level level = context.getLevel();
        Block block = level.getBlockState(context.getClickedPos()).getBlock();
        if (block == Blocks.LODESTONE && player != null) {
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            player.getInventory().placeItemBackInInventory(new ItemStack(ItemRegister.GRAVITY_CORE.get(), 1), true);
            player.getInventory().placeItemBackInInventory(new ItemStack(ItemRegister.REPULSION_CORE.get(), 1), true);
            if (!level.isClientSide) {
                ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, context.getClickedPos().getX() + 0.5, context.getClickedPos().getY() + 0.5, context.getClickedPos().getZ() + 0.5, 64, 0, 0, 0, 0.12);
            }
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundRegister.UNSTABLE_CORE_BREAK.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("unstable_core.tooltip")).withStyle(ChatFormatting.BLUE));
    }
}
