package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.block.entity.LooseSnowBlockEntity;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;

public abstract class AbstractSnowStorageSnowballEntity extends AbstractBSFSnowballEntity {
    protected int snowStock = 0;

    public AbstractSnowStorageSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractSnowStorageSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment, int snowStock) {
        super(pEntityType, pShooter, pLevel, launchAdjustment);
        this.snowStock = snowStock;
    }

    protected void placeLooseSnowBlock(Level level, BlockPos blockPos) {
        if (snowStock > 0) {
            if (level.getBlockState(blockPos).canBeReplaced()) {
                level.setBlock(blockPos, BlockRegister.LOOSE_SNOW_BLOCK.get().defaultBlockState(), 3);
                level.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.SNOW_PLACE, SoundSource.NEUTRAL, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                snowStock--;
            } else if (level.getBlockEntity(blockPos) instanceof LooseSnowBlockEntity blockEntity) {
                blockEntity.setAge(0);
                blockEntity.setChanged();
            }
        } else {
            this.discard();
        }
    }
}
