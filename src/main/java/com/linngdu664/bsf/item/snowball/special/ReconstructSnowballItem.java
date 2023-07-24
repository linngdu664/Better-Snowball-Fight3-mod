package com.linngdu664.bsf.item.snowball.special;

import com.linngdu664.bsf.block.entity.CriticalSnowEntity;
import com.linngdu664.bsf.block.entity.LooseSnowBlockEntity;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.special.ReconstructSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import com.linngdu664.bsf.util.BSFMthUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ReconstructSnowballItem extends AbstractBSFSnowballItem {
    private static final float suckRange = 7f;
    public ReconstructSnowballItem() {
        super(Rarity.RARE);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        return throwOrStorage(pPlayer, pLevel, pUsedHand, 1.7F, 15);
    }

    @Override
    public AbstractBSFSnowballEntity getCorrespondingEntity(Level level, LivingEntity livingEntity, ILaunchAdjustment launchAdjustment) {
        int stockSnow=0;
        BlockPos blockPos = new BlockPos(Mth.floor(livingEntity.getX()), Mth.floor(livingEntity.getY()), Mth.floor(livingEntity.getZ()));
        for (int i = (int) (blockPos.getX() - suckRange); i <= (int) (blockPos.getX() + suckRange); i++) {
            for (int j = (int) (blockPos.getY() - suckRange); j <= (int) (blockPos.getY() + suckRange); j++) {
                for (int k = (int) (blockPos.getZ() - suckRange); k <= (int) (blockPos.getZ() + suckRange); k++) {
                    if (BSFMthUtil.modSqr(i - blockPos.getX(), j - blockPos.getY(), k - blockPos.getZ()) <= suckRange * suckRange) {
                        BlockPos blockPos1 = new BlockPos(i, j, k);
                        BlockState blockState = level.getBlockState(blockPos1);
                        if (blockState.getBlock() == Blocks.SNOW) {
                            stockSnow+=blockState.getValue(SnowLayerBlock.LAYERS);
                            ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, blockPos1.getX(), blockPos1.getY(), blockPos1.getZ(), 5, 0, 0, 0, 0.12);
                        } else if (blockState.getBlock() == Blocks.SNOW_BLOCK) {
                            stockSnow+=8;
                            ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, blockPos1.getX(), blockPos1.getY(), blockPos1.getZ(), 5, 0, 0, 0, 0.12);
                        } else if (blockState.getBlock() == Blocks.POWDER_SNOW||blockState.getBlock() == Blocks.POWDER_SNOW_CAULDRON) {
                            stockSnow+=12;
                            ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, blockPos1.getX(), blockPos1.getY(), blockPos1.getZ(), 5, 0, 0, 0, 0.12);
                        } else if (level.getBlockEntity(blockPos1) instanceof CriticalSnowEntity criticalSnowEntity){
                            criticalSnowEntity.suicide();
                            stockSnow+=2;
                        }else if (level.getBlockEntity(blockPos1) instanceof LooseSnowBlockEntity looseSnowBlockEntity){
                            looseSnowBlockEntity.suicide();
                            stockSnow+=4;
                        }
                    }
                }
            }
        }
        return new ReconstructSnowballEntity(livingEntity, level, launchAdjustment,stockSnow,!livingEntity.isShiftKeyDown());
    }

    @Override
    public int getTypeFlag() {
        return AbstractBSFSnowballItem.HAND_TYPE_FLAG;
    }
}
