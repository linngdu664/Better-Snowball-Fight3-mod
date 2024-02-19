package com.linngdu664.bsf.item.snowball.special;

import com.linngdu664.bsf.block.LooseSnowBlock;
import com.linngdu664.bsf.block.entity.CriticalSnowEntity;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import com.linngdu664.bsf.util.BSFCommonUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public abstract class AbstractSnowStorageSnowballItem extends AbstractBSFSnowballItem {
    protected static final float SUCK_RANGE = 7f;

    public AbstractSnowStorageSnowballItem(Rarity rarity) {
        super(rarity);
    }

    @Override
    public AbstractBSFSnowballEntity getCorrespondingEntity(Level level, LivingEntity livingEntity, ILaunchAdjustment launchAdjustment) {
        return null;
    }

    protected int absorbSnow(LivingEntity livingEntity, Level level) {
        int stockSnow = 0;
        BlockPos blockPos = new BlockPos(Mth.floor(livingEntity.getX()), Mth.floor(livingEntity.getY()), Mth.floor(livingEntity.getZ()));
        for (int i = (int) (blockPos.getX() - SUCK_RANGE); i <= (int) (blockPos.getX() + SUCK_RANGE); i++) {
            for (int j = (int) (blockPos.getY() - SUCK_RANGE); j <= (int) (blockPos.getY() + SUCK_RANGE); j++) {
                for (int k = (int) (blockPos.getZ() - SUCK_RANGE); k <= (int) (blockPos.getZ() + SUCK_RANGE); k++) {
                    if (BSFCommonUtil.lengthSqr(i - blockPos.getX(), j - blockPos.getY(), k - blockPos.getZ()) <= SUCK_RANGE * SUCK_RANGE) {
                        BlockPos blockPos1 = new BlockPos(i, j, k);
                        BlockState blockState = level.getBlockState(blockPos1);
                        if (blockState.getBlock() == Blocks.SNOW) {
                            stockSnow += blockState.getValue(SnowLayerBlock.LAYERS);
                            ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, blockPos1.getX(), blockPos1.getY(), blockPos1.getZ(), 5, 0, 0, 0, 0.12);
                        } else if (blockState.getBlock() == Blocks.SNOW_BLOCK) {
                            stockSnow += 8;
                            ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, blockPos1.getX(), blockPos1.getY(), blockPos1.getZ(), 5, 0, 0, 0, 0.12);
                        } else if (blockState.getBlock() == Blocks.POWDER_SNOW || blockState.getBlock() == Blocks.POWDER_SNOW_CAULDRON) {
                            stockSnow += 12;
                            ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, blockPos1.getX(), blockPos1.getY(), blockPos1.getZ(), 5, 0, 0, 0, 0.12);
                        } else if (level.getBlockEntity(blockPos1) instanceof CriticalSnowEntity criticalSnowEntity) {
                            criticalSnowEntity.suicide();
                            stockSnow += 2;
                        } else if (posIsLooseSnow(level, blockPos1)) {
                            destroyBlock(level, blockPos1);
                            stockSnow += 4;
                        }
                    }
                }
            }
        }
        return stockSnow;
    }

    private void destroyBlock(Level level, BlockPos pos) {
        if (posIsLooseSnow(level, pos)) {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            BlockState snow = Blocks.SNOW.defaultBlockState();
            if (level.getBlockState(pos).getMaterial().isReplaceable() && snow.canSurvive(level, pos) && !posIsLooseSnow(level, pos.below())) {
                level.setBlockAndUpdate(pos, snow);
            }
            level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.SNOW_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, pos.getX(), pos.getY(), pos.getZ(), 5, 0, 0, 0, 0.12);
        }
    }

    protected boolean posIsLooseSnow(Level level, BlockPos pos) {
        return level.getBlockState(pos).getBlock() instanceof LooseSnowBlock;
    }

    @Override
    public int getTypeFlag() {
        return AbstractBSFSnowballItem.HAND_TYPE_FLAG;
    }

    public abstract int getMaxCapacity();

    @Override
    public void addUsageTips(List<Component> pTooltipComponents) {
        pTooltipComponents.add(new TranslatableComponent("snow_storage_snowball.tooltip").withStyle(ChatFormatting.BLUE));
    }
}
