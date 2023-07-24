package com.linngdu664.bsf.item.tank;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LargeSnowballTankItem extends Item {
    public LargeSnowballTankItem() {
        super(new Properties().stacksTo(1).durability(192).rarity(Rarity.UNCOMMON));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer.getOffhandItem().isEmpty()) {
            CompoundTag compoundTag = itemStack.getOrCreateTag();
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Main.MODID, compoundTag.getString("snowball")));
            if (item != null && !Items.AIR.equals(item)) {
                int damageValue = itemStack.getDamageValue();
                int maxDamage = itemStack.getMaxDamage();
                Inventory inventory = pPlayer.getInventory();
                if (pPlayer.isShiftKeyDown() || damageValue >= maxDamage - 16) {
                    int k = maxDamage - damageValue;
                    itemStack.setDamageValue(maxDamage);
                    if (!pPlayer.getAbilities().instabuild) {
                        compoundTag.remove("snowball");
                    }
                    for (int i = 0; i < k / 16; i++) {
                        inventory.placeItemBackInInventory(new ItemStack(item, 16), true);
                    }
                    inventory.placeItemBackInInventory(new ItemStack(item, k % 16), true);
                } else {
                    itemStack.setDamageValue(damageValue + 16);
                    inventory.placeItemBackInInventory(new ItemStack(item, 16), true);
                }
                pPlayer.awardStat(Stats.ITEM_USED.get(this));
                return InteractionResultHolder.success(itemStack);
            }
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public @NotNull Component getName(ItemStack pStack) {
        CompoundTag compoundTag = pStack.getOrCreateTag();
        if (compoundTag.contains("snowball")) {
            return MutableComponent.create(new TranslatableContents("item.bsf." + compoundTag.getString("snowball") + "_tank", null, new Object[0]));
        }
        return super.getName(pStack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Main.MODID, pStack.getOrCreateTag().getString("snowball")));
        if (item instanceof AbstractBSFSnowballItem snowballItem) {
            snowballItem.generateWeaponTips(pTooltipComponents);
            snowballItem.addLastTips(pTooltipComponents);
        } else {
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_storage_tank1.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_storage_tank2.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_storage_tank3.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snowball_storage_tank.tooltip", null, new Object[0])).withStyle(ChatFormatting.GRAY));
        }
    }
}
