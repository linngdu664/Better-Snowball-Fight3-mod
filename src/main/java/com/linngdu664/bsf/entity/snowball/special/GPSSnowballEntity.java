package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.registry.EntityRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class GPSSnowballEntity extends AbstractBSFSnowballEntity {
    private ItemStack targetLocator;

    public GPSSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setItem(new ItemStack(ItemRegister.GPS_SNOWBALL.get()));
    }

    public GPSSnowballEntity(LivingEntity pShooter, Level pLevel, ItemStack targetLocator) {
        super(EntityRegister.GPS_SNOWBALL.get(), pShooter, pLevel);
        this.targetLocator = targetLocator;
        this.setItem(new ItemStack(ItemRegister.GPS_SNOWBALL.get()));
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!level().isClientSide) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!isCaught && pResult.getEntity() instanceof LivingEntity livingEntity && targetLocator != null) {
            targetLocator.getOrCreateTag().putUUID("UUID", livingEntity.getUUID());
            if (getOwner() instanceof Player player) {
//                player.displayClientMessage(MutableComponent.create(new TranslatableContents("target.tip", null, new Object[0])).append(livingEntity.getName().getString() + " UUID:" + livingEntity.getUUID()), false);
                if (pResult.getEntity() instanceof Player player1) {
                    player1.displayClientMessage(MutableComponent.create(new TranslatableContents("targeted.tip", null, new Object[]{})), false);
                }
                level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.7F, 1.0F / (level().getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            }
            targetLocator.setHoverName(MutableComponent.create(new TranslatableContents("item.bsf.target_locator", null, new Object[]{}))
                    .append(": ").append(MutableComponent.create(new TranslatableContents("target.tip", null, new Object[]{})))
                    .append(livingEntity.getName().getString() + "  UUID: " + livingEntity.getUUID()));
        }
    }

    @Override
    public double getBasicPunch() {
        return 1;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.IRON_SNOWBALL.get();
    }
}
