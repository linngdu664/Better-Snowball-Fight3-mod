package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.block.BlockRegister;
import com.linngdu664.bsf.block.entity.LooseSnowBlockEntity;
import com.linngdu664.bsf.entity.EntityRegister;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.ItemRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class ReconstructSnowballEntity extends AbstractBSFSnowballEntity {
    private static final int generatingDistance = 1;
    private static final int posNum = 10 + generatingDistance;
    private static final int growthConstraint = 3;

    private int stockSnow = 0;
    private int flag =0;


    private BlockPos[] passingPosArr = new BlockPos[posNum];


    public ReconstructSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ReconstructSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment, int stockSnow) {
        super(EntityRegister.IMPULSE_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
        this.stockSnow=stockSnow;

    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        Level level = level();
        if (!level.isClientSide) {
            if (!(level.getBlockEntity(result.getBlockPos()) instanceof LooseSnowBlockEntity)){
                System.out.println("剩："+stockSnow);
                this.discard();
            }
        }
        super.onHitBlock(result);
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (!level.isClientSide) {
            if (flag%growthConstraint==0){
                handleSetBlock(level);
            }
            posArrMove(new BlockPos(Mth.floor(this.getX()), Mth.floor(this.getY()), Mth.floor(this.getZ())));
            flag++;

        }
        this.push(0,0.01,0);

    }
    private void handleSetBlock(Level level){
        if (stockSnow<=0) return;
        for (int i = generatingDistance; i < posNum; i++) {
            if (passingPosArr[i]!=null){
                placeLooseSnowBlock(level,passingPosArr[i].offset(0,(i- generatingDistance)/growthConstraint,0));
                placeLooseSnowBlock(level,passingPosArr[i].offset(0,-(i- generatingDistance)/growthConstraint,0));
                if (i+1<posNum && passingPosArr[i+1]!=null){
                    int dx=passingPosArr[i].getX()-passingPosArr[i+1].getX();
                    int dz=passingPosArr[i].getZ()-passingPosArr[i+1].getZ();
                    int adx=Math.abs(dx);
                    int adz=Math.abs(dz);
                    if (adx>1||adz>1){
                        int x1=passingPosArr[i].getX();
                        int x2=passingPosArr[i+1].getX();
                        int z1=passingPosArr[i].getZ();
                        int z2=passingPosArr[i+1].getZ();

                        if (adx>adz){
                            float k=(float)((z2-z1)/(x2-x1));
                            float b= z1-k*x1;
                            int h=0;
                            if (dx<0){
                                for (int j = 1; j < adx; j++) {
                                    if (k>0){
                                        h=k*(x1+j)+b>z1+h?h+1:h;
                                    }else{
                                        h=k*(x1+j)+b<z1+h?h-1:h;
                                    }
                                    placeLooseSnowBlock(level,passingPosArr[i].offset(j,(i- generatingDistance)/growthConstraint,h));
                                    placeLooseSnowBlock(level,passingPosArr[i].offset(j,-(i- generatingDistance)/growthConstraint,h));
                                }
                            }else{
                                for (int j = 1; j < adx; j++) {
                                    if (k>0){
                                        h=k*(x2+j)+b>z2+h?h+1:h;
                                    }else{
                                        h=k*(x2+j)+b<z2+h?h-1:h;
                                    }
                                    placeLooseSnowBlock(level,passingPosArr[i+1].offset(j,(i- generatingDistance)/growthConstraint,h));
                                    placeLooseSnowBlock(level,passingPosArr[i+1].offset(j,-(i- generatingDistance)/growthConstraint,h));
                                }
                            }
                        }else{
                            float k=(float)((x2-x1)/(z2-z1));
                            float b= x1-k*z1;
                            int h=0;
                            if (dz<0){
                                for (int j = 1; j < adz; j++) {
                                    if (k>0){
                                        h=k*(z1+j)+b>x1+h?h+1:h;
                                    }else{
                                        h=k*(z1+j)+b<x1+h?h-1:h;
                                    }
                                    placeLooseSnowBlock(level,passingPosArr[i].offset(h,(i- generatingDistance)/growthConstraint,j));
                                    placeLooseSnowBlock(level,passingPosArr[i].offset(h,-(i- generatingDistance)/growthConstraint,j));
                                }
                            }else{
                                for (int j = 1; j < adz; j++) {
                                    if (k>0){
                                        h=k*(z2+j)+b>x2+h?h+1:h;
                                    }else{
                                        h=k*(z2+j)+b<x2+h?h-1:h;
                                    }
                                    placeLooseSnowBlock(level,passingPosArr[i+1].offset(h,(i- generatingDistance)/growthConstraint,j));
                                    placeLooseSnowBlock(level,passingPosArr[i+1].offset(h,-(i- generatingDistance)/growthConstraint,j));
                                }
                            }
                        }
                    }
                }
            }
        }

    }
    private void placeLooseSnowBlock(Level level, BlockPos blockPos){
        if (stockSnow>0){
            if (level.getBlockState(blockPos).canBeReplaced()) {
                level.setBlock(blockPos, BlockRegister.LOOSE_SNOW_BLOCK.get().defaultBlockState(), 3);
                level.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.SNOW_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                stockSnow--;
            } else if(level.getBlockEntity(blockPos) instanceof LooseSnowBlockEntity blockEntity){
                blockEntity.setAge(0);
                blockEntity.setChanged();
            }

        }

    }
    private void posArrMove(BlockPos newPos){
        System.arraycopy(passingPosArr, 0, passingPosArr, 1, posNum - 1);
        passingPosArr[0]=newPos;
    }


    @Override
    public boolean canBeCaught() {
        return true;
    }

    @Override
    public float getBasicDamage() {
        return Float.MIN_NORMAL;
    }

    @Override
    public float getBasicBlazeDamage() {
        return 3;
    }

    @Override
    public int getBasicWeaknessTicks() {
        return 0;
    }

    @Override
    public int getBasicFrozenTicks() {
        return 0;
    }

    @Override
    public double getBasicPunch() {
        return 0;
    }

    @Override
    public float getSubspacePower() {
        return 1.5F;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.RECONSTRUCT_SNOWBALL.get();
    }
}
