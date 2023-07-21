package com.linngdu664.bsf.block.entity;

import com.linngdu664.bsf.block.BlockRegister;
import com.linngdu664.bsf.util.BSFMthUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class LooseSnowBlockEntity extends BlockEntity {
    private int targetAge = BSFMthUtil.randInt(140, 180);
    private int age;

    public LooseSnowBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegister.LOOSE_SNOW_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public static <T> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (!level.isClientSide) {
            LooseSnowBlockEntity looseSnowBlockEntity = (LooseSnowBlockEntity) blockEntity;
            if (looseSnowBlockEntity.age < looseSnowBlockEntity.targetAge) {
                looseSnowBlockEntity.age++;
                looseSnowBlockEntity.setChanged();
            } else {
                looseSnowBlockEntity.setRemoved();
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                BlockState snow = Blocks.SNOW.defaultBlockState();
                if (level.getBlockState(pos).canBeReplaced() && snow.canSurvive(level, pos)&& !level.getBlockState(pos.below()).getBlock().getName().getString().equals(BlockRegister.LOOSE_SNOW_BLOCK.get().getName().getString())) {
                    level.setBlockAndUpdate(pos, snow);
                }
                ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, pos.getX(), pos.getY(), pos.getZ(), 10, 0, 0, 0, 0.12);

            }
        }

    }

    public void setAge(int age) {
        this.age = age;
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
