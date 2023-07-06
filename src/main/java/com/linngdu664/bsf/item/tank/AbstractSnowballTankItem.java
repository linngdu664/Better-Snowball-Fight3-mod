package com.linngdu664.bsf.item.tank;

import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import com.linngdu664.bsf.util.ItemGroup;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractSnowballTankItem extends Item {
    private final Item item;

    public AbstractSnowballTankItem(Item item) {
        super(new Properties().tab(ItemGroup.MAIN).stacksTo(1).durability(96).rarity(Rarity.UNCOMMON));
        this.item = item;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer.getOffhandItem().isEmpty()) {
            if (pPlayer.isShiftKeyDown() || itemStack.getDamageValue() >= 80) {
                int k = 96 - itemStack.getDamageValue();
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

    public AbstractBSFSnowballItem getSnowball() {
        return (AbstractBSFSnowballItem) item;
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
