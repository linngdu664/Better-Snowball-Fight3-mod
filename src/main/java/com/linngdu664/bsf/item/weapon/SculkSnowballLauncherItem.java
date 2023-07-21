package com.linngdu664.bsf.item.weapon;

import com.linngdu664.bsf.effect.EffectRegister;
import com.linngdu664.bsf.entity.snowball.nomal.SculkSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.util.SoundRegister;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SculkSnowballLauncherItem extends AbstractBSFWeaponItem {
    public static final int TYPE_FLAG = 8;

    public SculkSnowballLauncherItem() {
        super(8192, Rarity.RARE, TYPE_FLAG);
    }

    @Override
    public ILaunchAdjustment getLaunchAdjustment(double damageDropRate, Item snowball) {
        return null;
    }

    @Override
    public boolean isAllowBulkedSnowball() {
        return true;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer.hasEffect(EffectRegister.WEAPON_JAM.get())) {
            return InteractionResultHolder.fail(itemStack);
        }
        if (!pLevel.isClientSide) {
            ItemStack stack = getAmmo(pPlayer);
            if (stack != null) {
                pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundRegister.SNOWBALL_CANNON_SHOOT.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
                SculkSnowballEntity snowballEntity = new SculkSnowballEntity(pPlayer, pLevel);
                snowballEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 2.0F, 1.0F);
                pLevel.addFreshEntity(snowballEntity);
                itemStack.hurtAndBreak(1, pPlayer, p -> p.broadcastBreakEvent(pUsedHand));
                consumeAmmo(stack, pPlayer);
                pPlayer.awardStat(Stats.ITEM_USED.get(this));
            }
        }
        return InteractionResultHolder.pass(itemStack);
    }
}
