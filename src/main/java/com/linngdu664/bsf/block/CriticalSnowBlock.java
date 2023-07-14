package com.linngdu664.bsf.block;

import com.linngdu664.bsf.blockentity.BlockEntityRegister;
import com.linngdu664.bsf.blockentity.CriticalSnowBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CriticalSnowBlock extends Block implements EntityBlock {
    public CriticalSnowBlock() {
        super(Properties.copy(Blocks.SNOW_BLOCK).speedFactor(0.2F).jumpFactor(0.2F));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new CriticalSnowBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return pBlockEntityType == BlockEntityRegister.CRITICAL_SNOW_BLOCK_ENTITY.get() ? CriticalSnowBlockEntity::tick : null;
    }
}
