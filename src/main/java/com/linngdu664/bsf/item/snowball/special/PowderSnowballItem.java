package com.linngdu664.bsf.item.snowball.special;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.special.PowderSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import com.linngdu664.bsf.item.weapon.SnowballCannonItem;
import com.linngdu664.bsf.item.weapon.SnowballShotgunItem;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PowderSnowballItem extends AbstractBSFSnowballItem {
    public PowderSnowballItem() {
        super(Rarity.COMMON);
        DispenserBlock.registerBehavior(this, new AbstractProjectileDispenseBehavior() {
            protected @NotNull Projectile getProjectile(@NotNull Level p_123476_, @NotNull Position p_123477_, @NotNull ItemStack p_123478_) {
                return Util.make(new PowderSnowballEntity(p_123476_, p_123477_.x(), p_123477_.y(), p_123477_.z()), (p_123474_) -> {
                });
            }
        });
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        return throwOrStorage(pPlayer, pLevel, pUsedHand, 1.25F, 20);
    }

    @Override
    public AbstractBSFSnowballEntity getCorrespondingEntity(Level level, LivingEntity livingEntity, ILaunchAdjustment launchAdjustment) {
        return new PowderSnowballEntity(livingEntity, level, launchAdjustment);
    }

    @Override
    public void onCraftedBy(@NotNull ItemStack pStack, @NotNull Level pLevel, Player pPlayer) {
        pPlayer.getInventory().placeItemBackInInventory(new ItemStack(Items.BUCKET), true);
    }

    @Override
    public double getShotgunPushRank() {
        return 0.12;
    }

    @Override
    public int getTypeFlag() {
        return AbstractBSFSnowballItem.HAND_TYPE_FLAG | SnowballCannonItem.TYPE_FLAG | SnowballShotgunItem.TYPE_FLAG;
    }

    @Override
    public Item getTank() {
        return ItemRegister.POWDER_SNOWBALL_TANK.get();
    }

//    @Override
//    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
//        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_hand.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
//        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_cannon.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
//        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_no_machine_gun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_RED));
//        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_shotgun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
//        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("powder_snowball.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_AQUA));
//    }

    @Override
    public void addLastTips(List<Component> pTooltipComponents) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("powder_snowball.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_AQUA));
    }
}
