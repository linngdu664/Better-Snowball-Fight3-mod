package com.linngdu664.bsf.item.tank;

import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
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

public class SnowballTankItem extends Item {
    private final Item item;
    private final boolean isLarge;

    public SnowballTankItem(Item item, boolean isLarge) {
        super(new Properties().stacksTo(1).durability(isLarge ? 192 : 96).rarity(Rarity.UNCOMMON));
        this.isLarge = isLarge;
        this.item = item;
    }

    public AbstractBSFSnowballItem getSnowball() {
        return (AbstractBSFSnowballItem) item;
    }

    public boolean isLarge() {
        return isLarge;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer.getOffhandItem().isEmpty()) {
            int damageValue = itemStack.getDamageValue();
            int maxDamage = itemStack.getMaxDamage();
            if (pPlayer.isShiftKeyDown() || damageValue >= maxDamage- 16) {
                int k = maxDamage - damageValue;
                for (int i = 0; i < k / 16; i++) {
                    pPlayer.getInventory().placeItemBackInInventory(new ItemStack(item, 16), true);
                }
                pPlayer.getInventory().placeItemBackInInventory(new ItemStack(item, k % 16), true);
                if (!pPlayer.getAbilities().instabuild) {
                    pPlayer.setItemInHand(pUsedHand, new ItemStack(ItemRegister.EMPTY_SNOWBALL_STORAGE_TANK.get()));
                }
            } else {
                pPlayer.getInventory().placeItemBackInInventory(new ItemStack(item, 16), true);
                itemStack.hurt(16, pLevel.getRandom(), null);
            }
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
        }
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        AbstractBSFSnowballItem snowballItem = getSnowball();
        snowballItem.generateWeaponTips(pTooltipComponents);
        snowballItem.addLastTips(pTooltipComponents);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean isRepairable(@NotNull ItemStack stack) {
        return false;
    }
}
