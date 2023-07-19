package com.linngdu664.bsf.block.entity;

import com.linngdu664.bsf.util.BSFMthUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class CriticalSnowEntity extends BlockEntity {
    private int targetAge = BSFMthUtil.randInt(100, 140);
    private int age;

    public CriticalSnowEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegister.CRITICAL_SNOW_ENTITY.get(), pPos, pBlockState);
    }

    public static <T> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        CriticalSnowEntity criticalSnowEntity = (CriticalSnowEntity) blockEntity;
        if (criticalSnowEntity.age < criticalSnowEntity.targetAge) {
            criticalSnowEntity.age++;
            criticalSnowEntity.setChanged();
        } else {
            criticalSnowEntity.setRemoved();
            level.setBlock(pos, Blocks.SNOW.defaultBlockState(), 3);
        }
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        age = pTag.getInt("age");
        targetAge = pTag.getInt("target_age");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("age", age);
        pTag.putInt("target_age", targetAge);
    }
}