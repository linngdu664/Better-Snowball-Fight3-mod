package com.linngdu664.bsf.item.weapon;

import com.linngdu664.bsf.entity.BSFSnowballEntity;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import com.linngdu664.bsf.item.tank.AbstractSnowballTankItem;
import com.linngdu664.bsf.util.ItemGroup;
import com.linngdu664.bsf.util.LaunchFunc;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBSFWeaponItem extends Item {
    public AbstractBSFWeaponItem(int durability, Rarity rarity) {
        super(new Properties().tab(ItemGroup.MAIN).stacksTo(1).durability(durability).rarity(rarity));
    }

    /**
     * Find the ammo of the weapon in player's inventory. It will search tanks first, and then it will search bulk
     * snowballs if "onlyTank" is false.
     *
     * @param player         The user of the weapon.
     * @param onlyTank       Whether the weapon can only use the snowball in tanks.
     * @param isNormalWeapon Whether the weapon is a normal weapon (cannon/shotgun).
     * @return The first valid ammo itemstack. If the method can't find a proper itemstack, it will return null.
     */
    protected ItemStack findAmmo(Player player, boolean onlyTank, boolean isNormalWeapon) {
        int k = player.getInventory().getContainerSize();
        for (int j = 0; j < k; j++) {
            ItemStack itemStack = player.getInventory().getItem(j);
            if (itemStack.getItem() instanceof AbstractSnowballTankItem tank && (tank.getSnowball().canBeLaunchedByMachineGun() && !isNormalWeapon || tank.getSnowball().canBeLaunchedByNormalWeapon() && isNormalWeapon)) {
                return itemStack;
            }
        }
        if (!onlyTank) {
            for (int j = 0; j < k; j++) {
                ItemStack itemStack = player.getInventory().getItem(j);
                if (itemStack.getItem() instanceof AbstractBSFSnowballItem snowball && (snowball.canBeLaunchedByMachineGun() && !isNormalWeapon || snowball.canBeLaunchedByNormalWeapon() && isNormalWeapon)) {
                    return itemStack;
                }
            }
        }
        return null;
    }

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

    protected BSFSnowballEntity ItemToEntity(Item item, Player player, Level level, LaunchFunc launchFunc) {
        if (item instanceof AbstractSnowballTankItem tank) {
            item = tank.getSnowball();
        }
        if (item instanceof AbstractBSFSnowballItem snowball) {
            return snowball.getCorrespondingEntity(level, player, launchFunc);
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
}
