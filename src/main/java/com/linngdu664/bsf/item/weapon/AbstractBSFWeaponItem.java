package com.linngdu664.bsf.item.weapon;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import com.linngdu664.bsf.item.tank.SnowballTankItem;
import com.linngdu664.bsf.network.AmmoTypeToServer;
import com.linngdu664.bsf.network.Network;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class AbstractBSFWeaponItem extends Item {
    private LinkedList<Item> launchOrder;                                      // client only
    private ItemStack prevAmmoItemStack = Items.AIR.getDefaultInstance();      // client only
    private ItemStack currentAmmoItemStack = Items.AIR.getDefaultInstance();   // client only
    private ItemStack nextAmmoItemStack = Items.AIR.getDefaultInstance();      // client only
    private final int typeFlag;

    public AbstractBSFWeaponItem(int durability, Rarity rarity, int flag) {
        super(new Properties().stacksTo(1).durability(durability).rarity(rarity));
        this.typeFlag = flag;
    }

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
        if (itemStack.getItem() instanceof SnowballTankItem tank) {
            itemStack.hurtAndBreak(1, player, p -> p.getInventory().placeItemBackInInventory(tank.isLarge() ? ItemStack.EMPTY : new ItemStack(ItemRegister.EMPTY_SNOWBALL_STORAGE_TANK.get()), true));
        } else if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
            if (itemStack.isEmpty()) {
                player.getInventory().removeItem(itemStack);
            }
        }
    }

    protected AbstractBSFSnowballEntity ItemToEntity(Item item, Player player, Level level, ILaunchAdjustment launchAdjustment) {
        if (item instanceof SnowballTankItem tank) {
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
        if (pLevel.isClientSide && pEntity instanceof Player player && (this.equals(player.getMainHandItem().getItem()) || this.equals(player.getOffhandItem().getItem()))) {
            ArrayList<Item> arrayList = new ArrayList<>();
            Inventory inventory = player.getInventory();
            int k = inventory.getContainerSize();
            for (int i = 0; i < k; i++) {
                Item item = inventory.getItem(i).getItem();
                if (item instanceof SnowballTankItem tank) {
                    AbstractBSFSnowballItem snowball = tank.getSnowball();
                    if (!arrayList.contains(snowball) && (typeFlag & snowball.getTypeFlag()) != 0) {
                        arrayList.add(snowball);
                    }
                } else if (isAllowBulkedSnowball() && item instanceof AbstractBSFSnowballItem snowball && !arrayList.contains(snowball) && (typeFlag & snowball.getTypeFlag()) != 0) {
                    arrayList.add(snowball);
                }
            }
            if (launchOrder == null) {
                launchOrder = new LinkedList<>(arrayList);
            } else {
                int j = launchOrder.size();
                int i = 0;
                while (i < arrayList.size()) {
                    Item item = arrayList.get(i);
                    if (!launchOrder.contains(item)) {
                        launchOrder.addLast(item);
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
            }
            Item oldItem = currentAmmoItemStack.getItem();
            if (launchOrder.isEmpty()) {
                prevAmmoItemStack = Items.AIR.getDefaultInstance();
                currentAmmoItemStack = prevAmmoItemStack;
                nextAmmoItemStack = prevAmmoItemStack;
            } else {
                Item item1, item3;
                if (launchOrder.size() == 1) {
                    item1 = Items.AIR;
                    item3 = Items.AIR;
                } else {
                    item1 = launchOrder.getLast();
                    item3 = launchOrder.get(1);
                }
                Item item2 = launchOrder.getFirst();
                int i1 = 0, i2 = 0, i3 = 0;
                for (int i = 0; i < k; i++) {
                    ItemStack itemStack = inventory.getItem(i);
                    Item item = itemStack.getItem();
                    if (item instanceof SnowballTankItem tank) {
                        AbstractBSFSnowballItem snowball = tank.getSnowball();
                        if (snowball.equals(item1)) {
                            i1 += itemStack.getMaxDamage() - itemStack.getDamageValue();
                        } else if (snowball.equals(item2)) {
                            i2 += itemStack.getMaxDamage() - itemStack.getDamageValue();
                        } else if (snowball.equals(item3)) {
                            i3 += itemStack.getMaxDamage() - itemStack.getDamageValue();
                        }
                    } else if (item instanceof AbstractBSFSnowballItem snowball) {
                        if (snowball.equals(item1)) {
                            i1 += itemStack.getCount();
                        } else if (snowball.equals(item2)) {
                            i2 += itemStack.getCount();
                        } else if (snowball.equals(item3)) {
                            i3 += itemStack.getCount();
                        }
                    }
                }
                if (item3.equals(item1)) {
                    i3 = i1;
                }
                prevAmmoItemStack = new ItemStack(item1, i1);
                currentAmmoItemStack = new ItemStack(item2, i2);
                nextAmmoItemStack = new ItemStack(item3, i3);
            }
            Item newItem = currentAmmoItemStack.getItem();
            if (!newItem.equals(oldItem)) {
                Network.PACKET_HANDLER.sendToServer(new AmmoTypeToServer(newItem, pSlotId));
            }
        }
    }

    public ItemStack getAmmo(Player player, ItemStack weaponItemStack) {
        CompoundTag compoundTag = weaponItemStack.getOrCreateTag();
        Item ammoItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Main.MODID, compoundTag.getString("ammo_item")));
        if (ammoItem == Items.AIR) {
            return null;
        }
        Inventory inventory = player.getInventory();
        int k = inventory.getContainerSize();
        ItemStack ammoItemStack = null;
        for (int i = 0; i < k; i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.getItem() instanceof SnowballTankItem tank && tank.getSnowball().equals(ammoItem) && (ammoItemStack == null || ammoItemStack.getDamageValue() < itemStack.getDamageValue())) {
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

    public LinkedList<Item> getLaunchOrder() {
        return launchOrder;
    }

    public ItemStack getPrevAmmoItemStack() {
        return prevAmmoItemStack;
    }

    public ItemStack getCurrentAmmoItemStack() {
        return currentAmmoItemStack;
    }

    public ItemStack getNextAmmoItemStack() {
        return nextAmmoItemStack;
    }

    public int getTypeFlag() {
        return typeFlag;
    }
}
