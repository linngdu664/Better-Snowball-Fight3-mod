package com.linngdu664.bsf.item.weapon;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import com.linngdu664.bsf.item.tank.AbstractSnowballTankItem;
import com.linngdu664.bsf.network.AmmoTypeSendToServer;
import com.linngdu664.bsf.network.Network;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class AbstractBSFWeaponItem extends Item {
    private ArrayList<Item> launchOrder;
    private Item ammoItem = Items.AIR;
    private final int typeFlag;

    public AbstractBSFWeaponItem(int durability, Rarity rarity, int flag) {
        super(new Properties().stacksTo(1).durability(durability).rarity(rarity));
        this.typeFlag = flag;
    }

//    public abstract ItemStack findAmmo(Player player);

//    public abstract LaunchFunc getLaunchFunc(double damageDropRate);
    public abstract ILaunchAdjustment getLaunchAdjustment(double damageDropRate, Item snowball);
    public abstract boolean isAllowBulkedSnowball();

    //Rewrite vanilla "shootFromRotation" method to remove the influence of player's velocity.
    protected void BSFShootFromRotation(Projectile projectile, float pX, float pY, float pVelocity, float pInaccuracy) {
        float f = -Mth.sin(pY * Mth.DEG_TO_RAD) * Mth.cos(pX * Mth.DEG_TO_RAD);
        float f1 = -Mth.sin(pX * Mth.DEG_TO_RAD);
        float f2 = Mth.cos(pY * Mth.DEG_TO_RAD) * Mth.cos(pX * Mth.DEG_TO_RAD);
        projectile.shoot(f, f1, f2, pVelocity, pInaccuracy);
    }

    protected void consumeAmmo(ItemStack itemStack, Player player) {
        if (itemStack.getItem() instanceof AbstractSnowballTankItem) {
            itemStack.hurtAndBreak(1, player, p -> p.getInventory().placeItemBackInInventory(new ItemStack(ItemRegister.EMPTY_SNOWBALL_STORAGE_TANK.get()), true));
        } else if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
            if (itemStack.isEmpty()) {
                player.getInventory().removeItem(itemStack);
            }
        }
    }

    protected AbstractBSFSnowballEntity ItemToEntity(Item item, Player player, Level level, ILaunchAdjustment launchAdjustment) {
        if (item instanceof AbstractSnowballTankItem tank) {
            item = tank.getSnowball();
        }
        if (item instanceof AbstractBSFSnowballItem snowball) {
            return snowball.getCorrespondingEntity(level, player, launchAdjustment);
        }
        return null;
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack pStack, ItemStack pRepairCandidate) {
        return pRepairCandidate.is(Items.IRON_INGOT);
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 25;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pIsSelected && pEntity instanceof Player player && pLevel.isClientSide) {
            ArrayList<Item> arrayList = new ArrayList<>();
            Inventory inventory = player.getInventory();
            int k = inventory.getContainerSize();
            for (int i = 0; i < k; i++) {
                Item item = inventory.getItem(i).getItem();
                if (item instanceof AbstractSnowballTankItem tank) {
                    AbstractBSFSnowballItem snowball = tank.getSnowball();
                    if (!arrayList.contains(snowball) && (typeFlag & snowball.getTypeFlag()) != 0) {
                        arrayList.add(snowball);
                    }
                } else if (isAllowBulkedSnowball() && item instanceof AbstractBSFSnowballItem snowball && !arrayList.contains(snowball) && (typeFlag & snowball.getTypeFlag()) != 0) {
                    arrayList.add(snowball);
                }
            }
            if (launchOrder == null) {
                launchOrder = arrayList;
                if (launchOrder.isEmpty()) {
                    Network.PACKET_HANDLER.sendToServer(new AmmoTypeSendToServer(Items.AIR));
                } else {
                    Network.PACKET_HANDLER.sendToServer(new AmmoTypeSendToServer(arrayList.get(0)));
                }
            } else {
                Item oldItem = null;
                if (!launchOrder.isEmpty()) {
                    oldItem = launchOrder.get(0);
                }
                int j = launchOrder.size();
                int i = 0;
                while (i < arrayList.size()) {
                    Item item = arrayList.get(i);
                    if (!launchOrder.contains(item)) {
                        launchOrder.add(item);
                        arrayList.remove(i);
                    } else {
                        i++;
                    }
                }
                i = 0;
                while (i < j) {
                    Item item = launchOrder.get(i);
                    if (!arrayList.contains(item)) {
                        launchOrder.remove(i);
                        j--;
                    } else {
                        i++;
                    }
                }
                if (launchOrder.isEmpty()) {
                    Network.PACKET_HANDLER.sendToServer(new AmmoTypeSendToServer(Items.AIR));
                } else {
                    Item newItem = launchOrder.get(0);
                    if (!newItem.equals(oldItem)) {
                        Network.PACKET_HANDLER.sendToServer(new AmmoTypeSendToServer(newItem));
                    }
                }
            }
        }
    }

    public ItemStack getAmmo(Player player) {
        if (ammoItem == Items.AIR) {
            return null;
        }
        Inventory inventory = player.getInventory();
        int k = inventory.getContainerSize();
        ItemStack ammoItemStack = null;
        for (int i = 0; i < k; i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.getItem() instanceof AbstractSnowballTankItem tank && tank.getSnowball().equals(ammoItem) && (ammoItemStack == null || ammoItemStack.getDamageValue() < itemStack.getDamageValue())) {
                ammoItemStack = itemStack;
            }
        }
        if (ammoItemStack != null) {
            return ammoItemStack;
        }
        if (isAllowBulkedSnowball()) {
            for (int i = 0; i < k; i++) {
                ItemStack itemStack = inventory.getItem(i);
                if (itemStack.getItem() instanceof AbstractBSFSnowballItem snowball && snowball.equals(ammoItem)) {
                    return itemStack;
                }
            }
        }
        return null;
    }

    public ArrayList<Item> getLaunchOrder() {
        return launchOrder;
    }

    public void setAmmoItem(Item item) {
        this.ammoItem = item;
    }
}
