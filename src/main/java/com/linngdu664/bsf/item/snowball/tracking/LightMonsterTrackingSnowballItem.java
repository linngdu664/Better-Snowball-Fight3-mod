package com.linngdu664.bsf.item.snowball.tracking;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.tracking.LightMonsterTrackingSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import com.linngdu664.bsf.item.weapon.SnowballCannonItem;
import com.linngdu664.bsf.item.weapon.SnowballMachineGunItem;
import com.linngdu664.bsf.item.weapon.SnowballShotgunItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LightMonsterTrackingSnowballItem extends AbstractBSFSnowballItem {
    public LightMonsterTrackingSnowballItem() {
        super(Rarity.RARE);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer.isShiftKeyDown()) {
            ItemStack newStack = new ItemStack(ItemRegister.LIGHT_PLAYER_TRACKING_SNOWBALL.get(), itemStack.getCount());
            pPlayer.setItemInHand(pUsedHand, newStack);
        } else {
            storageInTank(pPlayer, itemStack);
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }

    public AbstractBSFSnowballEntity getCorrespondingEntity(Level level, LivingEntity livingEntity, ILaunchAdjustment launchAdjustment) {
        return new LightMonsterTrackingSnowballEntity(livingEntity, level, launchAdjustment);
    }

    @Override
    public int getTypeFlag() {
        return SnowballCannonItem.TYPE_FLAG | SnowballShotgunItem.TYPE_FLAG | SnowballMachineGunItem.TYPE_FLAG;
    }

    @Override
    public Item getTank() {
        return ItemRegister.LIGHT_MONSTER_TRACKING_SNOWBALL_TANK.get();
    }

//    @Override
//    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
//        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_no_hand.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_RED));
//        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_cannon.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
//        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_machine_gun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
//        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_shotgun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
//        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("can_change.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
//        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("LMT_snowball.tooltip", null, new Object[0])).withStyle(ChatFormatting.GOLD));
//    }

    @Override
    public void addUsageTips(List<Component> pTooltipComponents) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("can_change.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
    }

    @Override
    public void addLastTips(List<Component> pTooltipComponents) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("LMT_snowball.tooltip", null, new Object[0])).withStyle(ChatFormatting.GOLD));
    }
}
