package com.linngdu664.bsf.item.snowball.tracking;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.tracking.HeavyMonsterTrackingSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import com.linngdu664.bsf.item.weapon.SnowballCannonItem;
import com.linngdu664.bsf.item.weapon.SnowballMachineGunItem;
import com.linngdu664.bsf.item.weapon.SnowballShotgunItem;
import com.linngdu664.bsf.registry.ItemRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HeavyMonsterTrackingSnowballItem extends AbstractBSFSnowballItem {
    public HeavyMonsterTrackingSnowballItem() {
        super(Rarity.RARE, new SnowballProperties()
                .idForTank(26)
                .allowLaunchTypeFlag(SnowballCannonItem.TYPE_FLAG | SnowballShotgunItem.TYPE_FLAG | SnowballMachineGunItem.TYPE_FLAG)
                .machineGunRecoil(0.12)
                .shotgunPushRank(0.18)
        );
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer.isShiftKeyDown()) {
            ItemStack newStack = new ItemStack(ItemRegister.HEAVY_PLAYER_TRACKING_SNOWBALL.get(), itemStack.getCount());
            pPlayer.setItemInHand(pUsedHand, newStack);
        } else {
            storageInTank(pPlayer);
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }

    public AbstractBSFSnowballEntity getCorrespondingEntity(Level level, LivingEntity livingEntity, ILaunchAdjustment launchAdjustment) {
        return new HeavyMonsterTrackingSnowballEntity(livingEntity, level, launchAdjustment);
    }

//    @Override
//    public int getTypeFlag() {
//        return SnowballCannonItem.TYPE_FLAG | SnowballShotgunItem.TYPE_FLAG | SnowballMachineGunItem.TYPE_FLAG;
//    }
//
//    @Override
//    public double getMachineGunRecoil() {
//        return 0.12;
//    }
//
//    @Override
//    public double getShotgunPushRank() {
//        return 0.18;
//    }

    @Override
    public void addUsageTips(List<Component> pTooltipComponents) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("HMT_snowball.tooltip", null, new Object[0])).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void addLastTips(List<Component> pTooltipComponents) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("can_change.tooltip", null, new Object[]{Minecraft.getInstance().options.keyShift.getTranslatedKeyMessage()})).withStyle(ChatFormatting.DARK_GRAY));
    }
}
