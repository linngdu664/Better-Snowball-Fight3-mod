package com.linngdu664.bsf.block;

import com.linngdu664.bsf.blockentity.BlockEntityRegister;
import com.linngdu664.bsf.blockentity.LooseSnowBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.block.RedStoneOreBlock;

public class LooseSnowBlock extends Block implements EntityBlock {
    public LooseSnowBlock() {
        super(Properties.copy(Blocks.SNOW_BLOCK)
                .noLootTable()
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY)
                .isSuffocating((BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) -> false)
                .isRedstoneConductor((BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) -> false));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getBlockSupportShape(@NotNull BlockState pState, @NotNull BlockGetter pReader, @NotNull BlockPos pPos) {
        return Shapes.empty();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new LooseSnowBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return pBlockEntityType == BlockEntityRegister.LOOSE_SNOW_BLOCK_ENTITY.get() ? LooseSnowBlockEntity::tick : null;
    }
}
